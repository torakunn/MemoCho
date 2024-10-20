package com.example.memocho.ui

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.memocho.database.model.Note
import com.example.memocho.ui.theme.MemoChoTheme



// At the top level of your kotlin file:
@Composable
fun StartScreen(
    modifier: Modifier = Modifier,
    notes: List<Note> = listOf(),
    onButtonClicked: (Long) -> Unit = {},
    onLongButtonClicked: (Long) -> Unit = {},
    loadNote: () -> Unit = {},
    openAlertDialog: Boolean,
    openEditDialog: Boolean,
    onEditButtonClicked: () -> Unit = {},
    onDeleteButtonClicked: () -> Unit = {},
    onDismissRequest: () -> Unit = {},
    onTitleChange: (String) -> Unit = {},
    title: String,
    id: Long,
    navToMemo: () -> Unit = {}
) {
    when {
        openAlertDialog -> {
            OperationAlertDialog(
                onDismissRequest = { onDismissRequest() },
                onEditButtonClicked = { onEditButtonClicked() },
                onDeletionButtonClicked = { onDeleteButtonClicked() },
                dialogTitle = "操作を選択してください",
                icon = Icons.Default.Info
            )
            }
        openEditDialog -> {
            EditAlertDialog(
                onDismissRequest = { onDismissRequest() },
                dialogTitle = title,
                onTitleChange = onTitleChange)
        }
    }

    NoteList(
        modifier,
        notes,
        onButtonClicked,
        onLongButtonClicked,
        navToMemo
    )
    loadNote()
}



@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteList(
    modifier: Modifier = Modifier,
    notes: List<Note>,
    onButtonClicked: (Long) -> Unit = {},
    onLongButtonClicked: (Long) -> Unit = {},
    navToMemo: () -> Unit = {}
) {
    val context = LocalContext.current
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
    ) {
        items(notes.size) { idx ->
            Text(
                text="${notes[idx].title}",
                color = Color.Black,
                modifier = Modifier.fillMaxSize()
                    .combinedClickable(
                        onClick = { onButtonClicked(notes[idx].id); navToMemo() },
                        onLongClick = {onLongButtonClicked(notes[idx].id)}
                    ).padding(8.dp)
            )
            HorizontalDivider(thickness = 2.dp)
        }
    }
}



@Composable
fun OperationAlertDialog(
    onDismissRequest: () -> Unit,
    onEditButtonClicked: () -> Unit,
    onDeletionButtonClicked: () -> Unit,
    dialogTitle: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = { Icon(icon, contentDescription = "Example Icon") },
        title = { Text(text = dialogTitle) },
        onDismissRequest = { onDismissRequest() },
        confirmButton = { TextButton(onClick = { onEditButtonClicked() }) { Text("編集") } },
        dismissButton = { TextButton(onClick = { onDeletionButtonClicked() }) { Text("削除") } },

    )
}

@Composable
fun EditAlertDialog(
    onDismissRequest: () -> Unit,
    dialogTitle: String,
    onTitleChange: (String) -> Unit = {},
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Text("タイトルを入力してください")
            TextField(
                value = dialogTitle,
                onValueChange = { onTitleChange(it) },
            )
        }
    }
}