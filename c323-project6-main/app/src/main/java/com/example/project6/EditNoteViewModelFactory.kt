package com.example.project6

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class EditNoteViewModelFactory(private val noteId: Long, private val dao: NotesDao): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditNoteViewModel::class.java)) {
            return EditNoteViewModel(noteId, dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel")
    }
}