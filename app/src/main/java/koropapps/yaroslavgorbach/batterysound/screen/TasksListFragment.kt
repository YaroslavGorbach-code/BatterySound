package koropapps.yaroslavgorbach.batterysound.screen

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import koropapps.yaroslavgorbach.batterysound.App
import koropapps.yaroslavgorbach.batterysound.R
import koropapps.yaroslavgorbach.batterysound.databinding.FragmentTasksBinding
import koropapps.yaroslavgorbach.batterysound.services.BatteryService


class TasksListFragment : Fragment(R.layout.fragment_tasks) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repo = (activity?.application as App).provideRepo()
        val serviceIntent = Intent(context, BatteryService::class.java)

        // init view
        val binding = FragmentTasksBinding.bind(view)
        val tasksAdapter = TasksListAdapter {
            ContextCompat.startForegroundService(requireContext(), serviceIntent)
        }.apply {
            setHasStableIds(true)
            setData(repo.getTasks())
        }

        binding.list.apply {
            adapter = tasksAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(LineDecorator(context, R.drawable.line_devider))
        }

    }
}