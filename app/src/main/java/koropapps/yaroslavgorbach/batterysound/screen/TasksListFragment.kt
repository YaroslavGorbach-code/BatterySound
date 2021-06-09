package koropapps.yaroslavgorbach.batterysound.screen

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
class TasksListFragment : Fragment(R.layout.fragment_tasks), AddTaskDialog.Host {
    val repo by lazy { (activity?.application as App).provideRepo() }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val serviceIntent = Intent(context, BatteryService::class.java)

        // init view
        val v = TasksListView(FragmentTasksBinding.bind(view), object : TasksListView.Callback {
            override fun onTask(task: BatteryTask) {
                task.isActive = !task.isActive
                lifecycleScope.launch { repo.updateTask(task) }
                if (repo.getStartServiceIsAllow()) {
                    ContextCompat.startForegroundService(requireContext(), serviceIntent)
                } else {
                    context?.stopService(serviceIntent)
                }
            }

            override fun onAdd() {
                AddTaskDialog().show(childFragmentManager, null)
            }
        })

        repo.getTasks().observe(viewLifecycleOwner, v::setTasks)
    }

    override fun onAdd(level: Int, text: String) {
        lifecycleScope.launch {
            repo.addTask(BatteryTask(Random.nextInt(), level, null, text))
        }
    }
}