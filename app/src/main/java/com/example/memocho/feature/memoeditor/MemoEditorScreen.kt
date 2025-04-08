package com.example.memocho.feature.memoeditor


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.memocho.ui.component.BaseScaffold

@Composable
fun MemoEditorScreen(
    navController: NavHostController,
    memoId: Long,
    memoEditorViewModel: MemoEditorViewModel = viewModel(factory = MemoEditorViewModel.Factory)
) {
    val memoEditorUiState by memoEditorViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        memoEditorViewModel.loadMemo(memoId)
    }

    BaseScaffold(
        navController = navController,
        title = memoEditorUiState.title,
        showSaveButton = true,
        saveMemo = { memoEditorViewModel.saveMemo() }
    )
    {
        TextField(
            value = memoEditorUiState.content,
            onValueChange = { memoEditorViewModel.changeMemoContent(it) },
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(Color.Transparent)
        )
    }

}