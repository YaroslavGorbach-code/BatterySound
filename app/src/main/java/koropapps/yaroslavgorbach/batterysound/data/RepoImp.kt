package koropapps.yaroslavgorbach.batterysound.data

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData

object RepoImp : Repo {
    private val tasks: MutableLiveData<List<BatteryTask>?> = MutableLiveData(null)

    init {
        tasks.value = listOf(
            BatteryTask(1, 64, null, "Test task"),
        )
    }

    override fun getTask(id: Int): BatteryTask? {
        return tasks.value?.get(0)
    }


    override fun getTasks(): MutableLiveData<List<BatteryTask>?> {
       return tasks
    }

    override suspend fun updateTask(task: BatteryTask) {
        val currentTasks = tasks.value
        if (currentTasks != null) {
            val wordIndex = currentTasks.indexOfFirst { it.id == task.id }
            if (wordIndex != -1) {
                tasks.value = currentTasks.toMutableList().apply {
                    set(wordIndex, task)
                }
            }
        }
    }

    override suspend fun addTask(task: BatteryTask) {
        tasks.value = tasks.value?.let { listOf(task) + it }
    }


    override fun getStartServiceIsAllow(): Boolean {
        return tasks.value?.find { it.isActive } != null
    }

    override fun getTextToSpeak(batteryLevel: Int): String? {
        tasks.value?.forEach { task ->
            if (batteryLevel == task.batteryLevel) {
                if (task.isActive && !task.isConsumed) {
                    task.text?.let {
                        task.isConsumed = true
                        return it
                    }
                }
            } else {
                task.isConsumed = false
            }
        }
        return null
    }

    override fun removeTask(batteryTask: BatteryTask) {
        tasks.value = tasks.value?.filter { it != batteryTask }
    }

    override fun getFileUri(batteryLevel: Int): Uri? {
        tasks.value?.forEach { task ->
            if (batteryLevel == task.batteryLevel) {
                if (task.isActive && !task.isConsumed) {
                    task.fileUri?.let {
                        task.isConsumed = true
                        return it
                    }
                }
            } else {
                task.isConsumed = false
            }
        }
        return null
    }
}
