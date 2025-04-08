package com.example.memocho.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.memocho.data.model.Memo


//テーブル作成
@Database(entities = [Memo::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun MemoDao(): MemoDao
}