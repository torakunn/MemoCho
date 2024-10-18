package com.example.memocho.database.dao

import androidx.annotation.Nullable
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.memocho.database.model.Note

@Dao
interface NoteDAO {
    @Query("SELECT * FROM note")
    suspend fun getAll(): List<Note>

    @Query("SELECT * FROM note WHERE id IN (:noteIds)")
    suspend fun loadAllByIds(noteIds: IntArray): List<Note>

    @Query("SELECT * FROM note WHERE title LIKE :title")
    suspend fun findByName(title: String): Note

    @Query("SELECT * FROM note WHERE id = :id")
    suspend fun getNoteById(id: Long): Note

    @Update
    suspend fun updateNote(note: Note)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg users: Note)

    @Delete
    suspend fun delete(note: Note)
}