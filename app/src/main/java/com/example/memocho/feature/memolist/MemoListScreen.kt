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
import androidx.compose.foundation.lazy.itemsIndexed
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

    val dialogState by memoListviewModel.dialogState

    when (dialogState){
        is DialogState.Operation -> {
            OperationAlertDialog(
                onDismissRequest = { memoListviewModel.onDismissRequest() },
                onEditButtonClicked = { memoListviewModel.onEditButtonClicked() },
                onDeletionButtonClicked = { memoListviewModel.onDeleteButtonClicked() },
                dialogTitle = "操作を選択してください",
                icon = Icons.Default.Info
            )
        }

        is DialogState.Edit -> {
            EditAlertDialog(
                onDismissRequest = { memoListviewModel.onDismissRequest() },
                memoTitle = uiState.title,
                onTitleChange = { memoListviewModel.onTitleChange(it) })
        }
        else -> {}
    }

    BaseScaffold(
        navController = navController,
        title = "メモ一覧",
        showFab = true,
        fabClicked = { memoListviewModel.onFabClicked() }
    ) {
        MemoList(
            modifier,
            uiState.memos,
            { navController.navigate(Destinations.MemoEditor.name + "/${it}") },
            { memoListviewModel.onTitleLongClicked(it) }
        )
    }
}


@Composable
fun MemoList(
    modifier: Modifier = Modifier,
    memos: List<Memo>,
    onClick: (Long) -> Unit = {},
    onLongClick: (Long) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
    ) {
        itemsIndexed(memos) { _: Int, memo: Memo ->
            MemoItem(memo, onClick, onLongClick)
        }
    }
}


@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun MemoItem(
    memo: Memo,
    onClick: (Long) -> Unit = {},
    onLongClick: (Long) -> Unit = {}
) {
    Column {
        Text(
            text = "${memo.title}",
            color = Color.Black,
            modifier = Modifier
                .fillMaxSize()
                .combinedClickable(
                    onClick = { onClick(memo.id) },
                    onLongClick = { onLongClick(memo.id) }
                )
                .padding(8.dp)
        )
        HorizontalDivider(thickness = 2.dp)
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
                .fillMaxWidth(0.8f)
                .wrapContentHeight(),
            shape = RoundedCornerShape(16.dp),
        ) {
            Box(modifier = Modifier.padding(16.dp)){
                    TextField(
                        value = memoTitle,
                        onValueChange = { onTitleChange(it) },
                        singleLine = true,
                        label = { Text("タイトルを入力してください") }
                    )
            }
        }
    }
}