package com.example.memocho.feature.memolist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.memocho.constants.Destinations
import com.example.memocho.data.model.Memo
import com.example.memocho.ui.component.BaseScaffold


// At the top level of your kotlin file:
@Composable
fun MemoListScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    memoListviewModel: MemoListViewModel = viewModel(factory = MemoListViewModel.Factory),
) {
    val uiState by memoListviewModel.uiState.collectAsState()

    BaseScaffold(
        navController = navController,
        title = "メモ一覧",
        showFab = true,
        fabClicked = { memoListviewModel.onFabClicked() }
    ) {
        when {
            uiState.openAlertDialog -> {
                OperationAlertDialog(
                    onDismissRequest = { memoListviewModel.onDismissRequest() },
                    onEditButtonClicked = { memoListviewModel.onEditButtonClicked() },
                    onDeletionButtonClicked = { memoListviewModel.onDeleteButtonClicked() },
                    dialogTitle = "操作を選択してください",
                    icon = Icons.Default.Info
                )
            }

            uiState.openEditDialog -> {
                EditAlertDialog(
                    onDismissRequest = { memoListviewModel.onDismissRequest() },
                    memoTitle = uiState.title,
                    onTitleChange = { memoListviewModel.onTitleChange(it) })
            }
        }

        NoteList(
            modifier,
            uiState.memos,
            { memoListviewModel.onTitleLongClicked(it) },
            { navController.navigate(Destinations.MemoEditor.name + "/${it}") }
        )
    }

}



@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteList(
    modifier: Modifier = Modifier,
    memos: List<Memo>,
    onLongButtonClicked: (Long) -> Unit = {},
    navToMemo: (Long) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
    ) {
        items(memos.size) { idx ->
            Text(
                text="${memos[idx].title}",
                color = Color.Black,
                modifier = Modifier
                    .fillMaxSize()
                    .combinedClickable(
                        onClick = { navToMemo(memos[idx].id) },
                        onLongClick = { onLongButtonClicked(memos[idx].id) }
                    )
                    .padding(8.dp)
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
    memoTitle: String,
    onTitleChange: (String) -> Unit = {},
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(16.dp),
        ) {
            Box(modifier = Modifier.padding(16.dp)){
                Column {
                    Text("タイトルを入力してください")
                    TextField(
                        value = memoTitle,
                        onValueChange = { onTitleChange(it) },
                    )
                }

            }
        }
    }
}