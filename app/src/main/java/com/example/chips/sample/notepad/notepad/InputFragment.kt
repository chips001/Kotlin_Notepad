package com.example.chips.sample.notepad.notepad

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import java.io.File

class InputFragment: Fragment() {

    interface OnFileOutputListener {
        fun onFileOutput()
    }

    private var currentFile: File? = null

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        currentFile?.let {
            outState.putSerializable("file", it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null && savedInstanceState.containsKey("file")) {
            currentFile = savedInstanceState.getSerializable("file") as File
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_input, container, false)
        val contentEditText = view.findViewById<EditText>(R.id.content_edit_text)
        val saveButton = view.findViewById<Button>(R.id.save_button)
        saveButton.setOnClickListener {
            currentFile = outputFile(currentFile, contentEditText.text.toString())
            if (context is OnFileOutputListener) {
                (context as OnFileOutputListener).onFileOutput()
            }
        }
        return view
    }

    fun show(file: File) {
        val memo = inputFile(file)
        val contentEditText = view?.findViewById<EditText>(R.id.content_edit_text) ?: return
        contentEditText.setText(memo)
        currentFile = file
    }
}
