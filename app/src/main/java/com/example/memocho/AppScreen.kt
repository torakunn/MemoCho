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
import androidx.compose.material3.IconButton
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
                    when (appUiState.showingScreen) {
                        MemoChoScreen.Start -> Text("メモ一覧")
                        MemoChoScreen.Memo -> Text(appUiState.title)
                        MemoChoScreen.Setting -> Text("設定")
                        else -> {}
                    }
                },
                actions = {
                    when (appUiState.showingScreen) {
                        MemoChoScreen.Start -> {}
                        MemoChoScreen.Memo -> {
                            IconButton(onClick = { viewModel.saveNote() }) {
                                Icon(
                                    painter = painterResource(R.drawable.icon_save),
                                    contentDescription = "save",
                                    modifier = Modifier.padding(4.dp)
                                )
                            }

                        }

                        MemoChoScreen.Setting -> {}
                        else -> {}
                    }
                }

            )
        },
        bottomBar = {
            MyButtomAppBar(
                modifier,
                viewModel,
                onMemoButtonClicked = { navController.navigate(MemoChoScreen.Start.name)},
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
                viewModel.setShowingDisplay(MemoChoScreen.Start)
                StartScreen(
                    notes = appUiState.notes,
                    onButtonClicked = viewModel.OnTitleClicked,
                    navToMemo = { navController.navigate(MemoChoScreen.Memo.name) },
                    onLongButtonClicked = viewModel.OnTitleLongClicked,
                    loadNote = viewModel.loadNote(),
                    openAlertDialog = appUiState.openAlartDialog,
                    openEditDialog = appUiState.openEditDialog,
                    onEditButtonClicked = { viewModel.onEditButtonClicked() },
                    onDeleteButtonClicked = { viewModel.onDeleteButtonClicked() },
                    onDismissRequest = { viewModel.onDismissRequest() },
                    onTitleChange = viewModel.OnTitleChange,
                    title = appUiState.title,
                    id = appUiState.id
                )
            }
            composable(route = MemoChoScreen.Setting.name) {
                viewModel.setShowingDisplay(MemoChoScreen.Setting)
                SettingScreen(
                )
            }
            composable(route = MemoChoScreen.Memo.name) {
                viewModel.setShowingDisplay(MemoChoScreen.Memo)
                MemoScreen(
                    notes = appUiState.notes,
                    id = appUiState.id,
                    content = appUiState.content,
                    onValueChange = viewModel.OnContentChange,
                )
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