package com.example.project6

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider


class NoteDeleteDialogFragment(noteId: Long) : DialogFragment() {
    val noteId = noteId
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setMessage("Are you sure you want to delete?")
            .setPositiveButton("Delete") { _,_ ->
                val application = requireNotNull(this.activity).application
                val dao = NotesDatabase.getInstance(application).notesDao
                val viewModelFactory = NotesViewModelFactory(dao)
                val viewModel = ViewModelProvider(this, viewModelFactory)[NotesViewModel::class.java]
                viewModel.deleteNote(noteId)
            }
            .setNegativeButton("Cancel"){ _,_ -> }
            .create()




    companion object {
        const val TAG = "NoteDeleteDialogFragment"
    }
}