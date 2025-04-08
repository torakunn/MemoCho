package com.example.memocho.feature.memoeditor

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

class MemoEditorViewModel(private val memoRepository: MemoRepository): ViewModel() {
    private val _uiState = MutableStateFlow(MemoEditorState())
    val uiState: StateFlow<MemoEditorState> = _uiState.asStateFlow()


    fun loadMemo(id:Long) {
        viewModelScope.launch {
            val memo = memoRepository.getNoteById(id)
            _uiState.update { currentState ->
                currentState.copy(
                    id = memo.id,
                    title = memo.title ?: "",
                    content = memo.content ?: ""
                )
            }
        }
    }

    fun showToast() {
        _uiState.update { currentState ->
            currentState.copy(showToast = true)
        }
    }

    fun hideToast() {
        _uiState.update { currentState ->
            currentState.copy(showToast = false)
        }
    }

    fun saveMemo(){
        viewModelScope.launch {
            memoRepository.updateNote(Memo(id = uiState.value.id, title = uiState.value.title, content = uiState.value.content))
        }
    }

    fun changeMemoContent(content: String = uiState.value.content) {
        _uiState.update { currentState ->
            currentState.copy(content = content)
        }
    }


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as MemoApplication)
                MemoEditorViewModel(application.container.memoRepository)
            }
        }
    }
}