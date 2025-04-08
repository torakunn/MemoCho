package com.example.memocho.ui.component


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.memocho.R
import com.example.memocho.constants.Destinations

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseScaffold(
    navController: NavHostController,
    title: String,
    showSaveButton: Boolean = false,
    saveMemo: () -> Unit = {},
    showFab: Boolean = false,
    fabClicked: () -> Unit = {},
    content: @Composable () -> Unit,
){
    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(title)
                },
                actions = {
                    if(showSaveButton) {
                        IconButton(onClick = { saveMemo() }) {
                            Icon(
                                painter = painterResource(R.drawable.icon_save),
                                contentDescription = "save",
                                modifier = Modifier.padding(4.dp)
                            )
                        }
                    }
                }

            )
        },
        bottomBar = {
            MyBottomAppBar(
                navController = navController,
                saveMemo = saveMemo
            )
        },
        floatingActionButton = {
            if(showFab){
                FloatingActionButton(onClick = { fabClicked() }) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            }
        }
        ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            content()
        }
    }
}




@Composable
private fun MyBottomAppBar(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    saveMemo: () -> Unit = {},
) {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.primary,
    ) {
        Row {
//            メモ一覧ボタン(左下)
            Button(
                onClick = { navController.navigate(Destinations.MemoList.name); saveMemo() },
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
            // 設定ボタン(右下)
            Button(
                onClick = { navController.navigate(Destinations.Setting.name); saveMemo() },
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