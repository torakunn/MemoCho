package com.example.memocho

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.room.Room
import com.example.memocho.database.AppDatabase
import com.example.memocho.ui.theme.MemoChoTheme
import com.example.memocho.ui.AppScreen

class MainActivity : ComponentActivity() {
    companion object {
        lateinit var db: AppDatabase
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MemoChoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppScreen()
                }
            }
        }
        db = Room.databaseBuilder(
            this,
            AppDatabase::class.java, "MemoAppDatabase"
        ).build()
    }

}