package com.example.memocho.feature.memoeditor


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
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
    var isImeVisible by remember { mutableStateOf(false) }
    val ime = WindowInsets.ime
    isImeVisible = ime.getBottom(LocalDensity.current) > 0

    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        memoEditorViewModel.loadMemo(memoId)
    }

    if (memoEditorUiState.showToast) {
        Toast.makeText(LocalContext.current, "保存しました", Toast.LENGTH_SHORT).show()
        memoEditorViewModel.hideToast()
    }

    BaseScaffold(
        modifier = Modifier.imePadding(),
        showBottomBar = !isImeVisible,
        navController = navController,
        title = memoEditorUiState.title,
        showSaveButton = true,
        saveMemo = { memoEditorViewModel.saveMemo(); memoEditorViewModel.showToast() }
    )
    {
        TextField(
            value = memoEditorUiState.content,
            onValueChange = { memoEditorViewModel.changeMemoContent(it) },
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(Color.Transparent)
        )
    }

}