package koropapps.yaroslavgorbach.batterysound.screen.tasks

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import koropapps.yaroslavgorbach.batterysound.App
import koropapps.yaroslavgorbach.batterysound.R
import koropapps.yaroslavgorbach.batterysound.data.BatteryTask
import koropapps.yaroslavgorbach.batterysound.databinding.FragmentTasksBinding
import koropapps.yaroslavgorbach.batterysound.screen.addupdate.AddUpdateTaskDialog
import koropapps.yaroslavgorbach.batterysound.services.BatteryService
import koropapps.yaroslavgorbach.batterysound.services.MediaPlayerService
import koropapps.yaroslavgorbach.batterysound.services.TextToSpeechService
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import kotlin.random.Random


@InternalCoroutinesApi
class TasksListFragment : Fragment(R.layout.fragment_tasks), AddUpdateTaskDialog.Host {
    val repo by lazy { (activity?.application as App).provideRepo() }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val batteryServiceIntent = Intent(context, BatteryService::class.java)
        val mediaServiceIntent = Intent(context, MediaPlayerService::class.java)
        val ttsServiceIntent = Intent(context, TextToSpeechService::class.java)


        // init view
        val v = TasksListView(FragmentTasksBinding.bind(view), object : TasksListView.Callback {
            override fun onStartTask(task: BatteryTask) {
                lifecycleScope.launch {
                    task.isActive = !task.isActive
                    repo.updateTask(task)
                }
                if (repo.getStartServiceIsAllow()) {
                    ContextCompat.startForegroundService(requireContext(), batteryServiceIntent)
                } else {
                    context?.stopServices(batteryServiceIntent, ttsServiceIntent, mediaServiceIntent)
                }
            }

            override fun onAdd() {
                AddUpdateTaskDialog().show(childFragmentManager, null)
            }

            override fun onSwipe(batteryTask: BatteryTask) {
                repo.removeTask(batteryTask)
                if(!repo.getStartServiceIsAllow())
                    context?.stopServices(batteryServiceIntent, ttsServiceIntent, mediaServiceIntent)
            }

            override fun onUndoRemove(batteryTask: BatteryTask) {
                lifecycleScope.launch {
                    batteryTask.isActive = false
                    batteryTask.isConsumed = false
                    repo.addTask(batteryTask)
                }
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

    override fun onAdded(level: Int, text: String?, fileUri: Uri?) {
        lifecycleScope.launch {
            repo.addTask(BatteryTask(Random.nextInt(), level, fileUri, text))
        }
    }

    override fun onUpdated(batteryTask: BatteryTask) {
        lifecycleScope.launch {
            repo.updateTask(batteryTask)
        }
    }

    private fun Context.stopServices(
        batteryServiceIntent: Intent,
        ttsServiceIntent: Intent,
        mediaServiceIntent: Intent
    ) {
        stopService(batteryServiceIntent)
        stopService(ttsServiceIntent)
        stopService(mediaServiceIntent)
    }
}