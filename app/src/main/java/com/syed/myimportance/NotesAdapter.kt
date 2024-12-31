package com.syed.myimportance

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter(private var notes: List<Notes>, context: Context):
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    class NoteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val titlet: TextView =itemView.findViewById(R.id.title_tv)
        val content: TextView =itemView.findViewById(R.id.content_tv)
        val update: ImageView = itemView.findViewById(R.id.edit_iv)
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
        holder.update.setOnClickListener{
            val intent = Intent(holder.itemView.context, UpdateNoteActivity::class.java).apply{
                putExtra("note_id",note.id)
            }
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = notes.size
    @SuppressLint("NotifyDataSetChanged")
    fun refrashData(newNotes: List<Notes>){
        notes = newNotes
        notifyDataSetChanged()
    }
}