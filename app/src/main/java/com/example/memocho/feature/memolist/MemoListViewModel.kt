package com.example.memocho.feature.memolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.memocho.MemoApplication
import com.example.memocho.data.MemoRepository
import com.example.memocho.data.model.Memo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class MemoListViewModel(private val memoRepository: MemoRepository): ViewModel() {
    private val _uiState = MutableStateFlow(MemoListState())
    val uiState: StateFlow<MemoListState> = _uiState.asStateFlow()


    init {
        loadMemoList()
    }


    fun onFabClicked() {
        val memo = Memo(title = "新規メモ", content = "")
        viewModelScope.launch {
            memoRepository.insertAll(memo)
            loadMemoList()
        }
    }


    fun onTitleChange(title: String = uiState.value.title) {
        _uiState.update { currentState ->
            currentState.copy(title = title)
        }
        viewModelScope.launch {
            val memo = memoRepository.getNoteById(uiState.value.id)
            memoRepository.updateNote(memo.copy(title = title))
            loadMemoList()
        }
    }


    fun onDeleteButtonClicked() {
        _uiState.update { currentState ->
            currentState.copy(openAlertDialog = false)
        }
        //データベースから指定のnoteを削除する処理
        viewModelScope.launch  {
            memoRepository.delete(Memo(id = uiState.value.id))
            loadMemoList()
        }
    }

    fun onEditButtonClicked() {
        viewModelScope.launch {
            val memo = memoRepository.getNoteById(uiState.value.id)
            _uiState.update { currentState ->
                currentState.copy(openAlertDialog = false, openEditDialog = true,title = memo.title ?: "")
            }
        }
    }

    fun onDismissRequest() {
        _uiState.update { currentState ->
            currentState.copy(openAlertDialog = false, openEditDialog = false)
        }
    }

    fun onTitleLongClicked(id: Long){
        _uiState.update { currentState ->
            currentState.copy(openAlertDialog = true, id = id)
        }
    }

    private fun loadMemoList() {
        viewModelScope.launch {
            val memoList = memoRepository.getAll()
            _uiState.update { currentState ->
                currentState.copy(memos = memoList)
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MemoApplication)
                MemoListViewModel(application.container.memoRepository)
            }
        }
    }
}