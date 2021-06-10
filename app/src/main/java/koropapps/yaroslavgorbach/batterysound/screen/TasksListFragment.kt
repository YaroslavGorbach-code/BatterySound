package koropapps.yaroslavgorbach.batterysound.screen

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import koropapps.yaroslavgorbach.batterysound.App
import koropapps.yaroslavgorbach.batterysound.R
import koropapps.yaroslavgorbach.batterysound.data.BatteryTask
import koropapps.yaroslavgorbach.batterysound.databinding.FragmentTasksBinding
import koropapps.yaroslavgorbach.batterysound.services.BatteryService
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import kotlin.random.Random


@InternalCoroutinesApi
class TasksListFragment : Fragment(R.layout.fragment_tasks), AddUpdateTaskDialog.Host {
    val repo by lazy { (activity?.application as App).provideRepo() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val serviceIntent = Intent(context, BatteryService::class.java)

        // init view
        val v = TasksListView(FragmentTasksBinding.bind(view), object : TasksListView.Callback {
            override fun onStartTask(task: BatteryTask) {
                task.isActive = !task.isActive
                lifecycleScope.launch { repo.updateTask(task) }
                if (repo.getStartServiceIsAllow()) {
                    ContextCompat.startForegroundService(requireContext(), serviceIntent)
                } else {
                    context?.stopService(serviceIntent)
                }
            }

            override fun onAdd() {
                AddUpdateTaskDialog().show(childFragmentManager, null)
            }

            override fun onSwipe(batteryTask: BatteryTask) {
                repo.removeTask(batteryTask)

            }

            override fun onUndoRemove(batteryTask: BatteryTask) {
                lifecycleScope.launch { repo.addTask(batteryTask) }
            }

            override fun onEditTask(batteryTask: BatteryTask) {
                AddUpdateTaskDialog().apply {
                    arguments = AddUpdateTaskDialog.argsOf(batteryTask)
                }.show(childFragmentManager, null)
            }

        })

        repo.getTasks().observe(viewLifecycleOwner, {
            v.setTasks(it?.sortedBy(BatteryTask::created)?.reversed())
        })
    }

    override fun onAdded(level: Int, text: String) {
        lifecycleScope.launch {
            repo.addTask(BatteryTask(Random.nextInt(), level, null, text))
        }
    }

    override fun onUpdated(batteryTask: BatteryTask) {
        lifecycleScope.launch {
            repo.updateTask(batteryTask)
        }
    }
}