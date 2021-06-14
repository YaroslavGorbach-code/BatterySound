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
import koropapps.yaroslavgorbach.batterysound.data.room.BatteryTask
import koropapps.yaroslavgorbach.batterysound.databinding.FragmentTasksBinding
import koropapps.yaroslavgorbach.batterysound.screen.createupdate.CreateUpdateTaskDialog
import koropapps.yaroslavgorbach.batterysound.screen.donotdisturb.DoNotDisturbDialog
import koropapps.yaroslavgorbach.batterysound.services.BatteryService
import koropapps.yaroslavgorbach.batterysound.services.MediaPlayerService
import koropapps.yaroslavgorbach.batterysound.services.TextToSpeechService
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import kotlin.random.Random


@InternalCoroutinesApi
class TasksListFragment : Fragment(R.layout.fragment_tasks), CreateUpdateTaskDialog.Host, DoNotDisturbDialog.Host {
    val repo by lazy { (activity?.application as App).provideRepo() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startService()
        // init view
        val v = TasksListView(FragmentTasksBinding.bind(view), object : TasksListView.Callback {
            override fun onSwitchChecked(task: BatteryTask, isChecked: Boolean) {
                lifecycleScope.launch {
                    task.isActive = isChecked
                    repo.updateTask(task)
                }
                startService()
            }

            override fun onAdd() {
                CreateUpdateTaskDialog().show(childFragmentManager, null)
            }

            override fun onSwipe(batteryTask: BatteryTask) {
                lifecycleScope.launch {
                    repo.removeTask(batteryTask)
                    if (!repo.getStartServiceIsAllow()) context?.stopServices()
                }
            }

            override fun onUndoRemove(batteryTask: BatteryTask) {
                lifecycleScope.launch {
                    batteryTask.isActive = false
                    batteryTask.isConsumed = false
                    repo.addTask(batteryTask)
                }
            }

            override fun onTask(batteryTask: BatteryTask) {
                CreateUpdateTaskDialog().apply {
                    arguments = CreateUpdateTaskDialog.argsOf(batteryTask)
                }.show(childFragmentManager, null)
            }

            override fun onDoNotDisturb() {
                DoNotDisturbDialog()
                    .apply {
                        arguments = DoNotDisturbDialog.argsOf(repo.getStartH(), repo.getStartM(), repo.getEndH(), repo.getEndM())
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

    override fun onStartTimeChanged(h: Int, m: Int) {
        repo.setDoNotDisturbStart(h, m)
    }

    override fun onEndTimeChanged(h: Int, m: Int) {
        repo.setDoNotDisturbEnd(h, m)
    }

    private fun startService() {
        lifecycleScope.launch {
            if (repo.getStartServiceIsAllow()) {
                ContextCompat.startForegroundService(
                    requireContext(),
                    Intent(context, BatteryService::class.java)
                )
            } else {
                context?.stopServices()
            }
        }
    }


    private fun Context.stopServices() {
        stopService(Intent(context, BatteryService::class.java))
        stopService(Intent(context, MediaPlayerService::class.java))
        stopService(Intent(context, TextToSpeechService::class.java))
    }
}