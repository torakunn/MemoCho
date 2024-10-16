package com.example.memocho.ui

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.memocho.AppDatabase
import com.example.memocho.MainActivity
import com.example.memocho.database.model.Note
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import kotlinx.coroutines.flow.update


class ViewModel(val context: Context) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()
    var titles by mutableStateOf(listOf<String>())
        private set
    var title by mutableStateOf("")
        private set
    var content by mutableStateOf("")
        private set

    val db = MainActivity.db
    val noteDAO = db.NoteDAO()





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
        val note1 = Note(title = "this is title", content = "hi")
        viewModelScope.launch  {
            noteDAO.insertAll(note1)
        }
        Toast.makeText(context,"add note", Toast.LENGTH_LONG).show()
    }


    fun loadNote() {
//        データベースからnoteListの内容を読み込む処理
        viewModelScope.launch  {
            var noteList = listOf<Note>()
            noteList = noteDAO.getAll()
            Log.d("titles","coroutine $noteList")
            val titles = mutableListOf<String>()

            for (note in noteList){
                titles.add(note.title.toString())
            }

            Log.d("titles","titles in coroutine $titles")
            _uiState.update { currentState ->
                currentState.copy(titles = titles)
            }
        }


        Log.d("titles","before return ${titles.toString()}")
    }

    private fun deleteNote(id: Int){
        //データベースから指定のnoteを削除する処理
        viewModelScope.launch  {
            noteDAO.delete(Note(id = id.toLong()))
        }
    }

}