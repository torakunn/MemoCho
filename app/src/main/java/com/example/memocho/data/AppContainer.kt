package com.example.memocho.data

import android.content.Context
import androidx.room.Room
import com.example.memocho.data.local.AppDatabase


interface AppContainer {
    val memoRepository: MemoRepository
}


class DefaultAppContainer(context: Context) : AppContainer {
    private val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java, "memo"
    ).build()

    private val memoDao = db.MemoDao()


    override val memoRepository: MemoRepository by lazy {
        LocalMemoRepository(memoDao)
    }
}