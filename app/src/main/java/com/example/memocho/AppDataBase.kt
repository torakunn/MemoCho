package com.example.memocho

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.memocho.database.dao.NoteDAO
import com.example.memocho.database.model.Note

@Database(entities = [Note::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun NoteDAO(): NoteDAO
}