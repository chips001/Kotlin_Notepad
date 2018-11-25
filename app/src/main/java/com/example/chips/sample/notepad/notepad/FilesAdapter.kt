package com.example.chips.sample.notepad.notepad

import android.content.Context
import android.view.LayoutInflater
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.io.File

class FilesAdapter(private val context: Context,
                   private val files: List<File>,
                   private val onFileClicked: (File) -> Unit)
    : RecyclerView.Adapter<FilesAdapter.FileViewHolder>() {

    private val inflater = LayoutInflater.from(context)

    class FileViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val titleTextView = view.findViewById<TextView>(R.id.title_text_view)
        val lastModifiedTextView = view.findViewById<TextView>(R.id.last_modified_text_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        val view = inflater.inflate(R.layout.list_item_row, parent, false)
        val viewHolder = FileViewHolder(view)
        view.setOnClickListener {
            val file = files[viewHolder.adapterPosition]
            onFileClicked(file)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        val file = files[position]
        holder.titleTextView.text = file.name
        holder.lastModifiedTextView.text = context.getString(R.string.last_modified, file.lastModified())
    }

    override fun getItemCount() = files.size
}
