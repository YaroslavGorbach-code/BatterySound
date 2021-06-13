package koropapps.yaroslavgorbach.batterysound.data

import android.content.Context
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import java.util.*

class RepoImp private constructor(context: Context) : Repo {
    private val commonPref = CommonPrefImp(context)

    companion object {
        private lateinit var instance: Repo
        fun getInstance(context: Context): Repo {
            if (!::instance.isInitialized) {
                instance = RepoImp(context)
            }
            return instance
        }
    }


    private val tasks: MutableLiveData<List<BatteryTask>?> = MutableLiveData(null)

    init {
        tasks.value = listOf(
            BatteryTask(1, 64, null, "Test task"),
        )
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

    override fun setDoNotDisturbStart(h: Int, m: Int) {
        commonPref.setDoNotDisturbStart(h, m)
    }

    override fun setDoNotDisturbEnd(h: Int, m: Int) {
        commonPref.setDoNotDisturbEnd(h, m)
    }

    override fun getStartH(): Int {
        return commonPref.getDoNotDisturbStartH()
    }

    override fun getStartM(): Int {
        return commonPref.getDoNotDisturbStartM()
    }

    override fun getEndH(): Int {
        return commonPref.getDoNotDisturbEndH()
    }

    override fun getEndM(): Int {
        return commonPref.getDoNotDisturbEndM()
    }

    override fun getSoundIsAllow(): Boolean {
        val calendarStart = Calendar.getInstance(Locale("ru"))
        calendarStart[Calendar.HOUR_OF_DAY] = commonPref.getDoNotDisturbStartH()
        calendarStart[Calendar.MINUTE] = commonPref.getDoNotDisturbStartM()

        val calendarEnd = Calendar.getInstance(Locale("ru"))
        calendarEnd[Calendar.HOUR_OF_DAY] = commonPref.getDoNotDisturbEndH()
        calendarEnd[Calendar.MINUTE] = commonPref.getDoNotDisturbEndM()

        val calendarCurrent = Calendar.getInstance(Locale("ru"))

        return !calendarCurrent.time.after(calendarStart.time) && !calendarCurrent.time.before(calendarEnd.time)
    }
}
