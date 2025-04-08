package com.example.memocho.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.memocho.data.model.Memo

//各テーブルに対するDAO（クエリと関数のペア）を定義
@Dao
interface MemoDao {
    @Query("SELECT * FROM memo")
    suspend fun getAll(): List<Memo>

    @Query("SELECT * FROM memo WHERE id IN (:noteIds)")
    suspend fun loadAllByIds(noteIds: IntArray): List<Memo>

    @Query("SELECT * FROM memo WHERE title LIKE :title")
    suspend fun findByName(title: String): Memo

    @Query("SELECT * FROM memo WHERE id = :id")
    suspend fun getNoteById(id: Long): Memo

    @Update
    suspend fun updateNote(memo: Memo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg users: Memo)

    @Delete
    suspend fun delete(memo: Memo)
}