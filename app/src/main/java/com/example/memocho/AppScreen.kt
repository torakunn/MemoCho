package com.example.memocho.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.memocho.R
import com.example.memocho.ui.theme.MemoChoTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


// At the top level of your kotlin file:
enum class MemoChoScreen() {
    Start,
    Setting,
    Memo
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScreen(
    modifier: Modifier = Modifier,
    viewModel: ViewModel = ViewModel(context = LocalContext.current),
    navController: NavHostController = rememberNavController()
) {
    val appUiState by viewModel.uiState.collectAsState()


    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("メモ帳！")
                }
            )
        },
        bottomBar = {
            MyButtomAppBar(
                modifier,
                viewModel,
                onMemoButtonClicked = { navController.navigate(MemoChoScreen.Start.name) },
                onSettingButtonClicked = { navController.navigate(MemoChoScreen.Setting.name) }
            )
                    },
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.onAddButtonClicked() }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = MemoChoScreen.Start.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = MemoChoScreen.Start.name) {
                StartScreen(
                    titles = appUiState.titles,
                    onButtonClicked = { navController.navigate(MemoChoScreen.Memo.name)},
                    onLongButtonClicked = viewModel.del,
                    loadNote = viewModel.loadNote()
                )
            }
            composable(route = MemoChoScreen.Setting.name) {
                SettingScreen()
            }
            composable(route = MemoChoScreen.Memo.name) {
                MemoScreen()
            }
        }
    }
}



@Composable
fun MyButtomAppBar(
    modifier: Modifier = Modifier,
    viewModel: ViewModel,
    onMemoButtonClicked: () -> Unit = {},
    onSettingButtonClicked: () -> Unit = {}
) {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.primary,
    ) {
        Row {
            Button(
                onClick = { onMemoButtonClicked() },
                colors = ButtonDefaults.buttonColors(Color.Transparent),
                modifier = modifier
                    .fillMaxSize()
                    .weight(1.0f)
                    .padding(8.dp)
            ) {
                Image(
                    painterResource(R.drawable.icon_note),
                    "open note list",
                    modifier.fillMaxSize()
                )
            }

            Button(
                onClick = { onSettingButtonClicked() },
                colors = ButtonDefaults.buttonColors(Color.Transparent),
                modifier = modifier
                    .fillMaxSize()
                    .weight(1.0f)
                    .padding(8.dp)
            ) {
                Image(
                    painterResource(R.drawable.icon_setting),
                    "open setting menu",
                    modifier.fillMaxSize()
                )
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun AppScreenPreview() {
    MemoChoTheme {
        AppScreen()
    }
}