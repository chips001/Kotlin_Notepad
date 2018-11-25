package com.example.chips.sample.notepad.notepad

import android.content.pm.PackageManager
import android.content.res.Configuration
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import java.io.File

class MainActivity : AppCompatActivity(), FilesListFragment.OnFilesSelectListener, InputFragment.OnFileOutputListener {

    private var drawerToggle: ActionBarDrawerToggle? = null

    override fun onFileSelected(file: File) {
        val fragment = supportFragmentManager.findFragmentById(R.id.input) as InputFragment
        fragment.show(file)
    }

    override fun onFileOutput() {
        val fragment = supportFragmentManager.findFragmentById(R.id.list_item) as FilesListFragment
        fragment.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (hasPermission()) setViews()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        drawerToggle?.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        drawerToggle?.onConfigurationChanged(newConfig)
    }

    private fun hasPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (!grantResults.isEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            setViews()
            drawerToggle?.syncState()
        } else {
            finish()
        }
    }

    private fun setViews() {
        setContentView(R.layout.activity_main)
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        if (drawerLayout != null) {
            setupDrawer(drawerLayout)
        }
    }

    private fun setupDrawer(drawer: DrawerLayout) {
        val toggle = ActionBarDrawerToggle(this, drawer, R.string.app_name, R.string.app_name)
        toggle.isDrawerIndicatorEnabled = true
        drawer.addDrawerListener(toggle)
        drawerToggle = toggle
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (drawerToggle?.onOptionsItemSelected(item) == true) {
            return true
        } else {
            return super.onOptionsItemSelected(item)
        }
    }
}

