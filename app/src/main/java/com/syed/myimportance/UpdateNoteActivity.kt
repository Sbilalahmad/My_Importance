package com.syed.myimportance

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.syed.myimportance.databinding.ActivityUpdateNoteBinding

class UpdateNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateNoteBinding
    private lateinit var db: NoteDatabaseHelper
    private var noteId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityUpdateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        db = NoteDatabaseHelper(this)
        noteId = intent.getIntExtra("note_id",-1)
        if (noteId == -1){
            finish()
            return
        }
        val note = db.getNoteById(noteId)
        binding.updateTitleEdt.setText(note.title)
        binding.updateNoteEdt.setText(note.content)
        binding.updateDoneIv.setOnClickListener{
            val newTitle = binding.updateTitleEdt.text.toString()
            val newContent = binding.updateNoteEdt.text.toString()
            val updateNote = Notes(noteId,newTitle,newContent)
            db.updateNote(updateNote)
            finish()
            Toast.makeText(this,"Update Successful",Toast.LENGTH_SHORT).show()
        }
    }
}