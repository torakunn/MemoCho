package com.example.memocho

import android.app.Application
import com.example.memocho.data.AppContainer
import com.example.memocho.data.DefaultAppContainer

class MemoApplication: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}