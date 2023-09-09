package com.example.taskmanager.ui.home

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.taskmanager.App
import com.example.taskmanager.R
import com.example.taskmanager.databinding.FragmentHomeBinding
import com.example.taskmanager.model.Task
import com.example.taskmanager.ui.task.adapter.TaskAdapter
import com.example.taskmanager.ui.utils.extensions.showToast

class HomeFragment : Fragment(), TaskAdapter.OnTaskLongClickListener {

    private var _binding: FragmentHomeBinding? = null

    private var adapter = TaskAdapter(this::onClick)
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = adapter

        adapter.setOnTaskLongClickListener(this)

        val data = App.db.taskDao().getAll()
        adapter.addTasks(data)


        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.taskFragment)
        }
    }

    override fun onTaskLongClick(task: Task) {
        showDeleteConfirmationDialog(task)
    }

    private fun showDeleteConfirmationDialog(task: Task) {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.delete_task)
            .setMessage(R.string.delete_task_confirmation)
            .setPositiveButton(R.string.yes) { _, _ ->
                deleteTask(task)
            }
            .setNegativeButton(R.string.no, null)
            .show()
    }

    private fun deleteTask(task: Task) {
        App.db.taskDao().delete(task)
        adapter.addTasks(App.db.taskDao().getAll())
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onClick(task: Task){
        findNavController().navigate(R.id.taskFragment, bundleOf(TASK_KEY to task))
    }

    companion object{
        const val TASK_KEY = "task.key"
    }
}