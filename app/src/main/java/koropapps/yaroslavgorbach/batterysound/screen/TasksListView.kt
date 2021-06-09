package koropapps.yaroslavgorbach.batterysound.screen

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import koropapps.yaroslavgorbach.batterysound.R
import koropapps.yaroslavgorbach.batterysound.data.BatteryTask
import koropapps.yaroslavgorbach.batterysound.databinding.FragmentTasksBinding

class TasksListView(binding: FragmentTasksBinding, callback: Callback) {
    interface Callback {
        fun onTask(task: BatteryTask)
    }

    var tasksAdapter: TasksListAdapter = TasksListAdapter(callback::onTask)

    init {
        binding.list.apply {
            adapter = tasksAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(LineDecorator(context, R.drawable.line_devider))
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy >0) {
                        // Scroll Down
                        if (binding.button.isShown) {
                            binding.button.hide()
                        }
                    }
                    else if (dy <0) {
                        // Scroll Up
                        if (!binding.button.isShown) {
                            binding.button.show()
                        }
                    }
                }
            })
        }
    }

    fun setTasks(it: List<BatteryTask>?) {
        it?.let { it1 -> tasksAdapter.setData(it1) }
    }

}