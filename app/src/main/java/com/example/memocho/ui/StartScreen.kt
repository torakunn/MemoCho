package com.example.memocho.ui

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.memocho.ui.theme.MemoChoTheme



// At the top level of your kotlin file:
@Composable
fun StartScreen(
    modifier: Modifier = Modifier,
    titles: List<String> = listOf(),
    onButtonClicked: () -> Unit = {},
    onLongButtonClicked: (Long) -> Unit = {},
    loadNote: () -> Unit = {},
) {
    NoteList(
        modifier,
        titles,
        onButtonClicked,
        onLongButtonClicked
    )
    loadNote()
}



@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteList(
    modifier: Modifier = Modifier,
    titles: List<String>,
    onButtonClicked: () -> Unit = {},
    onLongButtonClicked: (Long) -> Unit = {},
) {
    val context = LocalContext.current
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
    ) {
        items(titles.size) { id ->
            Text(
                text="${titles[id]}",
                color = Color.Black,
                modifier = Modifier.fillMaxSize()
                    .combinedClickable(
                        onClick = { onButtonClicked() },
                        onLongClick = {onLongButtonClicked(id.toLong())}
                    ).padding(8.dp)
            )
            HorizontalDivider(thickness = 2.dp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StartScreenPreview() {
    MemoChoTheme {
        StartScreen()
    }
}