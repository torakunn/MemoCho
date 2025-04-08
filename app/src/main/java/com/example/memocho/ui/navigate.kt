package com.example.memocho.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.memocho.constants.Destinations
import com.example.memocho.feature.memoeditor.MemoEditorScreen
import com.example.memocho.feature.memolist.MemoListScreen
import com.example.memocho.feature.setting.SettingScreen

@Composable
fun MemoAppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Destinations.MemoList.name
        )
    {
        composable(route = Destinations.MemoEditor.name+"/{memoId}") { backStackEntry ->
            val memoId = backStackEntry.arguments?.getString("memoId")?.toLong() ?: 0
            MemoEditorScreen(navController = navController,memoId = memoId)
        }
        composable(route = Destinations.MemoList.name) { MemoListScreen(navController = navController); }
        composable(route = Destinations.Setting.name) { SettingScreen(navController = navController); }



    }
}