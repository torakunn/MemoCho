package com.example.memocho.ui

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.memocho.R
import com.example.memocho.ui.theme.MemoChoTheme


// At the top level of your kotlin file:
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScreen(
    modifier: Modifier = Modifier,
    viewModel: ViewModel = ViewModel(context = LocalContext.current)
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
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
            ) {
                MyButtomAppBar(modifier,viewModel)
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.onAddButtonClicked() }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { innerPadding ->
        viewModel.loadNote()
        NoteList(modifier.padding(innerPadding),appUiState.titles)
    }
}



@Composable
fun MyButtomAppBar(
    modifier: Modifier = Modifier,
    viewModel: ViewModel
) {
    Row {
        Button(
            onClick = { viewModel.onMemoButtonClicked() },
            colors = ButtonDefaults.buttonColors(Color.Transparent),
            modifier = modifier
                .fillMaxSize()
                .weight(1.0f)
                .padding(8.dp)
        ) {
            Image(painterResource(R.drawable.icon_note),"open note list",modifier.fillMaxSize())
        }

        Button(
            onClick = { viewModel.onSettingButtonClicked() },
            colors = ButtonDefaults.buttonColors(Color.Transparent),
            modifier = modifier
                .fillMaxSize()
                .weight(1.0f)
                .padding(8.dp)
        ) {
            Image(painterResource(R.drawable.icon_setting),"open setting menu",modifier.fillMaxSize())
        }
    }
}

@Composable
fun NoteList(
    modifier: Modifier = Modifier,
    titles: List<String>,
) {

    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        ) {
        items(titles.size) { id ->
            TextButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.fillMaxSize(),
                shape = RectangleShape
                ) {
                Text(text="${titles[id]}",color = Color.Black)
            }
            HorizontalDivider(thickness = 2.dp)
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