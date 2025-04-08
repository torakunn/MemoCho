package com.example.memocho.feature.setting

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.memocho.ui.component.BaseScaffold

@Composable
fun SettingScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {

    BaseScaffold(navController = navController, title = "設定") {
        Text(text = "SettingScreen")
    }

}