package koropapps.yaroslavgorbach.batterysound.screen.tasks

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import koropapps.yaroslavgorbach.batterysound.R
import koropapps.yaroslavgorbach.batterysound.data.BatteryTask
import koropapps.yaroslavgorbach.batterysound.databinding.FragmentTasksBinding
import koropapps.yaroslavgorbach.batterysound.feature.LineDecorator
import koropapps.yaroslavgorbach.batterysound.feature.SwipeDismissDecor

class TasksListView(binding: FragmentTasksBinding, callback: Callback) {
    interface Callback {
        fun onStartTask(task: BatteryTask)
        fun onAdd()
        fun onSwipe(batteryTask: BatteryTask)
        fun onUndoRemove(batteryTask: BatteryTask)
        fun onEditTask(batteryTask: BatteryTask)
    }

    private var tasksAdapter: TasksListAdapter = TasksListAdapter(object :
        TasksListAdapter.Callback {
        override fun onStartTask(batteryTask: BatteryTask) {
            callback.onStartTask(batteryTask)
        }

        override fun onEditTask(batteryTask: BatteryTask) {
            callback.onEditTask(batteryTask)
        }

    })

    init {
        binding.list.apply {
            adapter = tasksAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(LineDecorator(context, R.drawable.line_devider))
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy > 0) {
                        // Scroll Down
                        if (binding.fab.isShown) {
                            binding.fab.hide()
                        }
                    } else if (dy < 0) {
                        // Scroll Up
                        if (!binding.fab.isShown) {
                            binding.fab.show()
                        }
                    }
                }
            })
            val swipeDecor =
                SwipeDismissDecor(ContextCompat.getDrawable(context, R.drawable.delete_item_hint_bg)!!) {
                    val task = tasksAdapter.getList()[it.adapterPosition]
                    callback.onSwipe(task)
                    Snackbar.make(binding.root, R.string.taskRemoved, Snackbar.LENGTH_SHORT).setAction(R.string.undo) {
                        callback.onUndoRemove(task)
                    }.show()
                }
            addItemDecoration(swipeDecor.also { it.attachToRecyclerView(this) })
        }
        binding.fab.setOnClickListener {
            callback.onAdd()
        }
    }

    fun setTasks(it: List<BatteryTask>?) {
        it?.let { it1 -> tasksAdapter.setData(it1) }
    }

}