package com.example.project6

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.example.project6.databinding.FragmentNoteViewBinding
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [NoteViewFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NoteViewFragment : Fragment() {
    private lateinit var binding: FragmentNoteViewBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNoteViewBinding.inflate(inflater, container, false)
        val view = binding.root
        val application = requireNotNull(this.activity).application
        val dao = NotesDatabase.getInstance(application).notesDao
        val viewModelFactory = NotesViewModelFactory(dao)
        val viewModel = ViewModelProvider(this, viewModelFactory)[NotesViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val adapter = ItemAdapter(
            { noteId: Long -> run {
                viewModel.onNoteClicked(noteId)
            }},
            { noteId: Long -> run {
                val dialog = NoteDeleteDialogFragment(noteId)
                dialog.show(childFragmentManager, NoteDeleteDialogFragment.TAG)
            }}
        )
        binding.itemRecyclerView.adapter = adapter

        viewModel.notes.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        viewModel.navigateToNote.observe(viewLifecycleOwner, Observer { noteId ->
            noteId?.let {
                val action = NoteViewFragmentDirections.actionNoteViewFragmentToNoteFragment(noteId)
                this.findNavController().navigate(action)
                viewModel.onNoteNavigated()
            }
        })

        return view
    }
}