package com.example.memocho.ui

import android.content.Context
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memocho.MainActivity
import com.example.memocho.database.model.Note
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.update


class ViewModel(val context: Context) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    val db = MainActivity.db
    val noteDAO = db.NoteDAO()


//    StartScreenで使う---------------------------------------------
//    関数オブジェクトを作って、ラムダ式で呼び出す。他に方法が分からない
    val OnTitleLongClicked = ::onTitleLongClicked
    val OnTitleClicked = ::onTitleClicked


    fun onMemoButtonClicked() {
        val note1 = Note(title = "this is title", content = "hi")
        viewModelScope.launch  {
            noteDAO.insertAll(note1)
        }

        Toast.makeText(context,"open note list", Toast.LENGTH_LONG).show()
    }

    fun onSettingButtonClicked() {
        val note1 = Note(id = 1)
        viewModelScope.launch  {
            noteDAO.delete(note1)
        }
        Toast.makeText(context,"open setting menu", Toast.LENGTH_LONG).show()
    }

    fun onAddButtonClicked() {
        val note1 = Note(title = "新規ノート", content = "")
        viewModelScope.launch  {
            noteDAO.insertAll(note1)
            val notes = noteDAO.getAll()
            _uiState.update { currentState ->
                currentState.copy(notes = notes)
            }
        }

        Toast.makeText(context,"add note", Toast.LENGTH_LONG).show()
    }


    fun loadNote (): () -> Unit = {
//        データベースからnoteListの内容を読み込む処理
        viewModelScope.launch  {
            val notes = noteDAO.getAll()
            Log.d("titles","coroutine start")
            _uiState.update { currentState ->
                currentState.copy(notes = notes)
            }
        }
    }

    fun onTitleClicked(id: Long) {
        viewModelScope.launch {
                val note = noteDAO.getNoteById(id)
                val contentString = note.content ?: ""
                _uiState.update { currentState ->
                    currentState.copy(id = id, content = contentString,title = note.title ?: "無題")
                }
        }
//        Toast.makeText(context,"open note id = $id", Toast.LENGTH_LONG).show()
    }
    fun onTitleLongClicked(id: Long){
        _uiState.update { currentState ->
            currentState.copy(openAlartDialog = true, id = id)
        }
    }

    fun onEditButtonClicked() {
        _uiState.update { currentState ->
            currentState.copy(openAlartDialog = false, openEditDialog = true)
        }
    }
    fun onDeleteButtonClicked() {
        _uiState.update { currentState ->
            currentState.copy(openAlartDialog = false)
        }
        //データベースから指定のnoteを削除する処理
        viewModelScope.launch  {
            noteDAO.delete(Note(id = uiState.value.id))
        }
    }
    fun onDismissRequest() {
        _uiState.update { currentState ->
            currentState.copy(openAlartDialog = false, openEditDialog = false)
        }
    }
    fun onTitleChange(title: String = uiState.value.title) {
        _uiState.update { currentState ->
            currentState.copy(title = title)
        }
        saveNote()
    }
//-----------------------------------------------------------------

//    MemoScreenで使う---------------------------------------------
    val OnContentChange = ::onContentChange
    val OnTitleChange = ::onTitleChange
    fun onContentChange(newContent: String = uiState.value.content) {
    //    キーボードを出していても一番下の行が見えるように末尾に大量の改行を挟む
        val newContent = newContent + "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"
        _uiState.update { currentState ->
            currentState.copy(content = newContent)
        }
    }

    fun saveNote(){
        val note = Note(id = uiState.value.id, title = uiState.value.title, content = uiState.value.content)
        viewModelScope.launch  {
            noteDAO.updateNote(note)
        }
        Toast.makeText(context,"保存しました", Toast.LENGTH_SHORT).show()
    }

    fun setShowingDisplay(showingScreen: MemoChoScreen) {
        _uiState.update { currentState ->
            currentState.copy(showingScreen = showingScreen)
        }
    }

//-----------------------------------------------------------------

}