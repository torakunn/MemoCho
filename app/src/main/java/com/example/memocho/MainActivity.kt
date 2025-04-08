package com.example.memocho

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.memocho.ui.MemoAppNavHost
import com.example.memocho.ui.theme.MemoChoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()
            MemoChoTheme {
                MemoAppNavHost(navController = navController)
            }
        }
    }

}