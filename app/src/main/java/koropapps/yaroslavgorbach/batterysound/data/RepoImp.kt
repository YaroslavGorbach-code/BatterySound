package koropapps.yaroslavgorbach.batterysound.data

import android.util.Log
import androidx.lifecycle.MutableLiveData

object RepoImp : Repo {
    private val tasks: MutableLiveData<List<BatteryTask>?> = MutableLiveData(null)

    init {
        tasks.value = listOf(
            BatteryTask(1, 64, null, "TIME TO CHARGE", false),
            BatteryTask(2, 65, null, "TIME TO CHARGE", false),
            BatteryTask(3, 66, null, "TIME TO CHARGE", false),
            BatteryTask(4, 67, null, "TIME TO CHARGE", false),
            BatteryTask(5, 68, null, "TIME TO CHARGE", false),
            BatteryTask(6, 69, null, "TIME TO CHARGE", false),
            BatteryTask(7, 70, null, "TIME TO CHARGE", false),
            BatteryTask(8, 71, null, "TIME TO CHARGE", false),
            BatteryTask(9, 72, null, "TIME TO CHARGE", false),
            BatteryTask(10, 73, null, "TIME TO CHARGE", false),
            BatteryTask(11, 74, null, "TIME TO CHARGE", false),
            BatteryTask(12, 75, null, "TIME TO CHARGE", false),
            BatteryTask(13, 76, null, "TIME TO CHARGE", false),
            BatteryTask(14, 77, null, "TIME TO CHARGE", false),
            BatteryTask(15, 78, null, "TIME TO CHARGE", false),
            BatteryTask(16, 79, null, "TIME TO CHARGE", false),
            BatteryTask(17, 80, null, "Шо ти голова", false),
            BatteryTask(18, 81, null, "Вадим вставай блядь", false),
            BatteryTask(19, 82, null, "Да есть же", false),
            BatteryTask(20, 83, null, "TIME TO CHARGE", false),
            BatteryTask(21, 84, null, "TIME TO CHARGE", false),
            BatteryTask(22, 85, null, "TIME TO CHARGE", false),
            BatteryTask(23, 86, null, "TIME TO CHARGE", false),
            BatteryTask(24, 87, null, "TIME TO CHARGE", false)
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

    override fun getTextToSpeak(batteryLevel: Int): String {
        tasks.value?.forEach { task ->
            if (batteryLevel == task.level) {
                if (task.isActive && !task.isConsumed) {
                    task.text?.let {
                        task.isConsumed = true
                        Log.v("mes", "true consumed")
                        return it
                    }
                }
            } else {
                Log.v("mes", "false consumed")
                task.isConsumed = false
            }
        }
        return ""
    }
}
