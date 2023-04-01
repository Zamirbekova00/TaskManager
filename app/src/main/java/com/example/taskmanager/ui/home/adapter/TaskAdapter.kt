package com.example.taskmanager.ui.home.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.taskmanager.R
import com.example.taskmanager.databinding.ItemTaskBinding
import com.example.taskmanager.model.Task
import kotlin.reflect.KFunction0

class TaskAdapter(private val onLongClick: (Task) -> Unit) : Adapter<TaskAdapter.TaskViewHolder>() {
    private val data: ArrayList<Task> = arrayListOf()

    fun addTasks(task: List<Task>) {
        data.clear()
        data.addAll(task)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            ItemTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class TaskViewHolder(private val binding: ItemTaskBinding) : ViewHolder(binding.root) {
        fun bind(task: Task) {
            with(binding) {
                tvTitle.text = task.title
                tvDesc.text = task.desc
                stripedBackground()
                itemView.setOnLongClickListener {
                    onLongClick(task)
                    false
                }
            }
        }

        private fun stripedBackground() {
            binding.apply {
                if (adapterPosition % 2 == 0) {
                    itemView.setBackgroundColor(Color.parseColor("#000000"))
                    tvTitle.setTextColor(Color.parseColor("#FFFFFFFF"))
                    tvDesc.setTextColor(Color.parseColor("#FFFFFFFF"))
                } else {
                    itemView.setBackgroundColor(Color.parseColor("#FFFFFFFF"))
                    tvTitle.setTextColor(Color.parseColor("#000000"))
                    tvDesc.setTextColor(Color.parseColor("#000000"))
                }
            }
        }
    }
}