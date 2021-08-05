package tk.juniorcesarabreu.todolist.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import tk.juniorcesarabreu.todolist.databinding.ActivityMainBinding
import tk.juniorcesarabreu.todolist.datasource.TaskDataSource

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // lazy espera o atributo ser chamada para ser executado
    private val adapter by lazy { TaskListAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvTasks.adapter = adapter
        updateList()

        insertListeners()
    }

    private fun insertListeners() {

        // add fab
        binding.fab.setOnClickListener {

            openTaskActivity.launch(
                Intent(this, AddTaskActivity::class.java)
            )
        }

        // edit option
        adapter.listenerEdit = { task ->

            openTaskActivity.launch(
                Intent(this, AddTaskActivity::class.java)
                    .apply {
                        putExtra(AddTaskActivity.TASK_ID, task.id)
                    }
            )
        }

        // delete option
        adapter.listenerDelete = {
            TaskDataSource.deleteTask(it)
            updateList()
        }
    }

    private fun updateList() {
        val list = TaskDataSource.getList()

        binding.includeEmpty.emptyState.visibility =
            (if (list.isEmpty()) View.VISIBLE else View.GONE)
        binding.rvTasks.visibility =
            (if (list.isNotEmpty()) View.VISIBLE else View.GONE)

        adapter.submitList(list)
    }

    // activity result contract
    private val openTaskActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->

        if (result.resultCode == Activity.RESULT_OK) {
            updateList()
        }
    }
}