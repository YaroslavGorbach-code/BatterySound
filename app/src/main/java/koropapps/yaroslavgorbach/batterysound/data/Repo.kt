package koropapps.yaroslavgorbach.batterysound.data

import android.net.Uri
import androidx.lifecycle.LiveData

interface Repo {
    fun getTasks(): LiveData<List<BatteryTask>?>
    suspend fun updateTask(task: BatteryTask)
    suspend fun addTask(task: BatteryTask)
    fun getStartServiceIsAllow(): Boolean
    fun getTextToSpeak(batteryLevel: Int): String?
    fun removeTask(batteryTask: BatteryTask)
    fun getFileUri(batteryLevel: Int): Uri?
    fun setDoNotDisturbStart(h: Int, m: Int)
    fun setDoNotDisturbEnd(h: Int, m: Int)
    fun getStartH(): Int
    fun getStartM(): Int
    fun getEndH(): Int
    fun getEndM(): Int
    fun getSoundIsAllow(): Boolean
}