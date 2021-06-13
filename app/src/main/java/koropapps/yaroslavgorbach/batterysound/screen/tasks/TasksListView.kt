package koropapps.yaroslavgorbach.batterysound.screen.tasks

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import koropapps.yaroslavgorbach.batterysound.R
import koropapps.yaroslavgorbach.batterysound.data.BatteryTask
import koropapps.yaroslavgorbach.batterysound.databinding.FragmentTasksBinding
import koropapps.yaroslavgorbach.batterysound.feature.LineDecorator
import koropapps.yaroslavgorbach.batterysound.feature.SwipeDismissDecor

class TasksListView(private val binding: FragmentTasksBinding, callback: Callback) {
    interface Callback {
        fun onSwitchChecked(task: BatteryTask, isChecked: Boolean)
        fun onAdd()
        fun onSwipe(batteryTask: BatteryTask)
        fun onUndoRemove(batteryTask: BatteryTask)
        fun onTask(batteryTask: BatteryTask)
        fun onDoNotDisturb()
    }

    private var tasksAdapter: TasksListAdapter = TasksListAdapter(object :
        TasksListAdapter.Callback {
        override fun onSwitchChecked(batteryTask: BatteryTask, isChecked: Boolean) {
            callback.onSwitchChecked(batteryTask, isChecked)
        }

        override fun onTask(batteryTask: BatteryTask) {
            callback.onTask(batteryTask)
        }

    })

    init {
        binding.toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.do_not_disturb -> callback.onDoNotDisturb()
            }
            true
        }
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
        binding.createLarge.battery.setOnClickListener {
            callback.onAdd()
        }
    }

    fun setTasks(list: List<BatteryTask>?) {
        list?.let {
            tasksAdapter.setData(list)
            if (list.isEmpty()){
                binding.createLarge.root.visibility = View.VISIBLE
            }else{
                binding.createLarge.root.visibility = View.GONE
            }
        }
    }

}