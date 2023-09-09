package com.example.taskmanager.ui.task.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.taskmanager.R
import com.example.taskmanager.databinding.ItemTaskBinding
import com.example.taskmanager.model.Task

class TaskAdapter(private val onClick: (Task) -> Unit) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private val list = arrayListOf<Task>()

    private var taskLongClickListener: OnTaskLongClickListener? = null

    fun addTasks(tasks: List<Task>) {
        list.clear()
        list.addAll(tasks)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            ItemTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(list[position], position)
    }

    inner class TaskViewHolder(private val binding: ItemTaskBinding) : ViewHolder(binding.root) {
        fun bind(task: Task, position: Int) {


            if (position % 2 == 0) {
                binding.root.setBackgroundResource(R.color.colorWhite)
                binding.tvDesc.setTextColor(Color.BLACK)
                binding.tvTitle.setTextColor(Color.BLACK)
            } else {
                binding.root.setBackgroundResource(R.color.colorBlack)
                binding.tvDesc.setTextColor(Color.WHITE)
                binding.tvTitle.setTextColor(Color.WHITE)
            }

            binding.tvTitle.text = task.title
            binding.tvDesc.text = task.desc
            binding.root.setOnClickListener {
                onClick(task)
            }
        }

        init {
            binding.root.setOnLongClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val task = list[position]
                    taskLongClickListener?.onTaskLongClick(task)
                }
                true
            }
        }
    }

    interface OnTaskLongClickListener {
        fun onTaskLongClick(task: Task)
    }

    fun setOnTaskLongClickListener(listener: OnTaskLongClickListener) {
        taskLongClickListener = listener
    }
}