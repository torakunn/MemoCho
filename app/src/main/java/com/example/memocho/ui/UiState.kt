package com.example.memocho.ui

import com.example.memocho.database.model.Note

data class UiState(
    val noteList: List<List<String>> = listOf(),
    val titles: List<String>  = listOf(),
    val note: List<Note> = listOf<Note>(),
    )
