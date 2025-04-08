package com.example.memocho.feature.memolist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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


sealed class DialogState{
    object None: DialogState()
    object Operation: DialogState()
    object Edit: DialogState()
}


class MemoListViewModel(private val memoRepository: MemoRepository): ViewModel() {
    private val _uiState = MutableStateFlow(MemoListState())
    val uiState: StateFlow<MemoListState> = _uiState.asStateFlow()

    private val _dialogState = mutableStateOf<DialogState>(DialogState.None)
    val dialogState: State<DialogState> = _dialogState


    init {
        loadMemoList()
        _uiState.update { currentState ->
            currentState.copy(completeInitialLoading = true)
        }
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
        _dialogState.value = DialogState.None
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
                currentState.copy(title = memo.title ?: "")
            }
            _dialogState .value = DialogState.Edit
        }
    }

    fun onDismissRequest() {
        _dialogState.value = DialogState.None
    }

    fun onTitleLongClicked(id: Long){
            _dialogState.value = DialogState.Operation
            _uiState.update { currentState ->
                currentState.copy(id = id)
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