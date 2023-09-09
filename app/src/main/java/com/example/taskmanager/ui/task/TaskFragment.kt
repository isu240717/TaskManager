package com.example.taskmanager.ui.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanager.App
import com.example.taskmanager.R
import com.example.taskmanager.databinding.FragmentTaskBinding
import com.example.taskmanager.model.Task
import com.example.taskmanager.ui.home.HomeFragment.Companion.TASK_KEY
import com.example.taskmanager.ui.task.adapter.TaskAdapter


class TaskFragment : Fragment() {

    private lateinit var binding: FragmentTaskBinding
    private var task: Task? = null
    private lateinit var adapter: TaskAdapter
    private val recyclerView: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        task = arguments?.getSerializable(TASK_KEY) as Task?
        adapter = TaskAdapter { task ->
            // Handle item click here
        }
        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        recyclerView?.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        DividerItemDecoration(
            requireContext(),
            DividerItemDecoration.VERTICAL
        )
        // Update UI based on the task data
        task?.let {
            etTitle.setText(it.title)
            etDesc.setText(it.desc)
            btnSave.text = getString(R.string.update)
        }

        if (task != null) {
            etTitle.setText(task?.title.toString())
            etDesc.setText(task?.desc.toString())
            btnSave.text = getString(R.string.update)
        }

        binding.btnSave.setOnClickListener {
            if (binding.etTitle.text.toString().isNotEmpty()) {
                if (task == null) {
                    save()
                } else {
                    update()
                }
            } else {
                binding.etTitle.error = "null title"
                return@setOnClickListener
            }
            findNavController().navigateUp()
        }
    }

    private fun save() {
        val data = Task(
            title = binding.etTitle.text.toString(),
            desc = binding.etDesc.text.toString(),
        )
        App.db.taskDao().insert(data)
    }

    private fun update() {
        val data = task?.copy(
            title = binding.etTitle.text.toString(),
            desc = binding.etDesc.text.toString()
        )
        if (data != null) {
            App.db.taskDao().update(data)
        }
    }

    companion object {
        const val RESULT_REQUEST_KEY = "request.key"
        const val RESULT_KEY = "result.key"
    }
}