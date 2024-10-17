package com.example.memocho.ui

import com.example.memocho.database.model.Note

data class UiState(
    val id: Long = 0,
    val content: String = "",
    val title: String = "",
    val titles: List<String>  = listOf(),
    val notes: List<Note> = listOf(),
    val openAlartDialog: Boolean = false,
    )
