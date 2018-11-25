package com.example.chips.sample.notepad.notepad

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.io.File
import java.lang.RuntimeException

class FilesListFragment: Fragment() {

    interface OnFilesSelectListener {
        fun onFileSelected(file: File)
    }

    private lateinit var recyclerView: RecyclerView

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context !is OnFilesSelectListener) throw RuntimeException("$context must implement OnFileSelectListener")
    }

    fun show() {
        val context = context ?: return
        val adapter = FilesAdapter(context, getFiles()) { file ->
            (context as OnFilesSelectListener).onFileSelected(file)
        }
        recyclerView.adapter = adapter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        recyclerView = view.findViewById(R.id.fileslist)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
        show()
        return view
    }
}
