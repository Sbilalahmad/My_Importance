package com.syed.myimportance

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter(private var notes: List<Notes>, context: Context):
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    class NoteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val titlet: TextView =itemView.findViewById(R.id.title_tv)
        val content: TextView =itemView.findViewById(R.id.content_tv)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item,parent,false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: NoteViewHolder,
        position: Int
    ) {
        val note = notes[position]
        holder.titlet.text = note.title
        holder.content.text = note.content
    }

    override fun getItemCount(): Int = notes.size
    fun refrashData(newNotes: List<Notes>){
        notes = newNotes
        notifyDataSetChanged()
    }
}