package com.syed.myimportance

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class NoteDatabaseHelper(context: Context): SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE $TABLE_NAME($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_TITLE TEXT,$COLUMN_CONTENT TEXT)"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldversion: Int,
        newversion: Int
    ) {
        val dropTable = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTable)
        onCreate(db)
    }

    companion object{
        private const val DATABASE_NAME = "notesapp.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "allnotes"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_CONTENT = "content"
    }
    fun insertNote(notes: Notes){
        val db = writableDatabase
        val value = ContentValues().apply {
            put(COLUMN_TITLE,notes.title)
            put(COLUMN_CONTENT, notes.content)
        }
        db.insert(TABLE_NAME,null,value)
        db.close()
    }
    fun getAllNotes(): List<Notes>{
        val notelist = mutableListOf<Notes>()
        val db =readableDatabase
        val query ="SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query,null)
        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))

            val note = Notes(id,title,content)
            notelist.add(note)
        }
        cursor.close()
        db.close()
        return notelist
    }
    fun updateNote(notes: Notes){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE,notes.title)
            put(COLUMN_CONTENT,notes.content)
        }
        val whereClause = "$COLUMN_ID = ? "
        val whereArgs = arrayOf(notes.id.toString())
        db.update(TABLE_NAME,values,whereClause,whereArgs)
        db.close()
    }
    fun getNoteById(noteId: Int):Notes{
        val db =readableDatabase
        val query ="SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $noteId"
        val cursor = db.rawQuery(query,null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
        val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))

        cursor.close()
        db.close()
        return Notes(id, title,content)
    }
}