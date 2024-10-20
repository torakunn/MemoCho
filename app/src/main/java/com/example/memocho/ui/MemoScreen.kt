package com.example.memocho.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.memocho.database.model.Note

@Composable
fun MemoScreen(
    modifier: Modifier = Modifier,
    notes: List<Note>,
    id: Long,
    content: String,
    onValueChange: (String) -> Unit,
) {
    TextField(
        value = content,
        onValueChange = { onValueChange(it) },
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color.Transparent)



    )
}