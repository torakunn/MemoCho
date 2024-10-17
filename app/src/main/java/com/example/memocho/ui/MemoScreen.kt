package com.example.memocho.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.memocho.database.model.Note

@Composable
fun MemoScreen(
    modifier: Modifier = Modifier,
    notes: List<Note>,
    id: Long,
    content: String
) {
    Text(text = content)
}