package com.example.project6

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.project6.databinding.ItemBinding

class DiffItemCallback : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note)
            = (oldItem.noteId == newItem.noteId)
    override fun areContentsTheSame(oldItem: Note, newItem: Note) = (oldItem == newItem)
}

class ItemAdapter(val onItemClicked: (noteId: Long) -> Unit, val onDeleteClicked: (noteId: Long) -> Unit)
    : ListAdapter<Note, ItemAdapter.TaskItemViewHolder>(DiffItemCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : TaskItemViewHolder = TaskItemViewHolder.inflateFrom(parent)
    override fun onBindViewHolder(holder: TaskItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onItemClicked, onDeleteClicked)
    }

    class TaskItemViewHolder(val binding: ItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun inflateFrom(parent: ViewGroup): TaskItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemBinding.inflate(layoutInflater, parent, false)
                return TaskItemViewHolder(binding)
            }
        }
        fun bind(item: Note, onItemClicked: (taskId: Long) -> Unit, onDeleteClicked: (noteId: Long) -> Unit) {
            binding.note = item
            binding.bDeleteItem.setOnClickListener { onDeleteClicked(item.noteId) }
            binding.itemRoot.setOnClickListener { onItemClicked(item.noteId) }
        }
    }
}



