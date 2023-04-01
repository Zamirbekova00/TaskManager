package com.example.taskmanager.ui.onBoard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.taskmanager.databinding.ItemOnboardingBinding
import com.example.taskmanager.databinding.ItemTaskBinding
import com.example.taskmanager.model.OnBoard
import com.example.taskmanager.utils.loadImage

class OnBoardingAdapter(
    private val onClick: () -> Unit,
    private val onNextClick: () -> Unit
) :
    Adapter<OnBoardingAdapter.OnBoardingViewHolder>() {
    private val data = arrayListOf<OnBoard>(
        OnBoard(
            "https://d57439wlqx3vo.cloudfront.net/iblock/03c/03c3f81942edbe28e073436c1e47b227/64c181f8554dc9a0e27e654be545e45d.png",
            "Task Manager 1",
            " A component of the Windows operating system (OS) that helps administrators and end users to monitor, manage and troubleshoot tasks."
        ),

        OnBoard(
            "https://toggl.com/blog/wp-content/uploads/2018/09/project-task-list.jpg",
            "Task Manager 2",
            "One of the most common examples of a task manager is the task manager utility in Microsoft Windows."
        ),

        OnBoard(
            "https://www.diffzy.com/articles/images/108/1.jpg",
            "Task Manager 3",
            "Opening your task manager in Windows is super easy. Just press the CTRL + Alt + Delete buttons simultaneously."
        )
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingViewHolder {
        return OnBoardingViewHolder(
            ItemOnboardingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: OnBoardingViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class OnBoardingViewHolder(private val binding: ItemOnboardingBinding) :
        ViewHolder(binding.root) {
        fun bind(onBoard: OnBoard) {
            binding.apply {
                tvTitle.text = onBoard.tittle
                tvDesc.text = onBoard.description
                imgBoard.loadImage(onBoard.image)
                skip.isVisible = adapterPosition != 2
                start.isVisible = adapterPosition == 2
                next.isVisible = adapterPosition != 2

                skip.setOnClickListener {
                    onClick()
                }

                start.setOnClickListener {
                    onClick()
                }

                next.setOnClickListener {
                    onNextClick()
                }
            }
        }
    }
}