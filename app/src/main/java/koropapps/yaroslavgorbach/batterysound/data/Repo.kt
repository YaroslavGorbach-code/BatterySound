package koropapps.yaroslavgorbach.batterysound.data

import androidx.lifecycle.LiveData

interface Repo {
    fun getTask(batteryLevel: Int): BatteryTask?
    fun getTasks(): LiveData<List<BatteryTask>?>
    suspend fun updateTask(task: BatteryTask)
    suspend fun addTask(task: BatteryTask)
    fun getStartServiceIsAllow(): Boolean
    fun getTextToSpeak(int: Int): String
}