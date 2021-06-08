package koropapps.yaroslavgorbach.batterysound.screen

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import koropapps.yaroslavgorbach.batterysound.App
import koropapps.yaroslavgorbach.batterysound.R
import koropapps.yaroslavgorbach.batterysound.databinding.FragmentTasksBinding
import koropapps.yaroslavgorbach.batterysound.services.BatteryService
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch


@InternalCoroutinesApi
class TasksListFragment : Fragment(R.layout.fragment_tasks) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repo = (activity?.application as App).provideRepo()
        val serviceIntent = Intent(context, BatteryService::class.java)

        // init view
        val binding = FragmentTasksBinding.bind(view)
        val tasksAdapter = TasksListAdapter { task ->
            task.isActive = !task.isActive
            lifecycleScope.launch { repo.updateTask(task) }
            if (repo.getStartServiceIsAllow()) {
                ContextCompat.startForegroundService(requireContext(), serviceIntent)
            }else{
                context?.stopService(serviceIntent)
            }

        }.apply {
            setHasStableIds(true)
        }


        repo.getTasks().observe(viewLifecycleOwner) {
            it?.let { it1 -> tasksAdapter.setData(it1) }
        }


        binding.list.apply {
            adapter = tasksAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(LineDecorator(context, R.drawable.line_devider))
        }

    }
}