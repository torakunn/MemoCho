package com.example.memocho.feature.memolist

import com.example.memocho.data.model.Memo

data class MemoListState(
    val memos: List<Memo> = emptyList(),
    val id: Long = 0,
    val title: String = "",
    val completeInitialLoading: Boolean = false
)
