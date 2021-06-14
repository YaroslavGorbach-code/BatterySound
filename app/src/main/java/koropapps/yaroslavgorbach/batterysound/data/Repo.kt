package koropapps.yaroslavgorbach.batterysound.data

import android.net.Uri
import androidx.lifecycle.LiveData
import koropapps.yaroslavgorbach.batterysound.data.room.BatteryTask

interface Repo {
    fun getTasks(): LiveData<List<BatteryTask>>
    suspend fun updateTask(task: BatteryTask)
    suspend fun addTask(task: BatteryTask)
    suspend fun getStartServiceIsAllow(): Boolean
    suspend fun getTextToSpeak(batteryLevel: Int): String?
    suspend fun removeTask(batteryTask: BatteryTask)
    suspend fun getFileUri(batteryLevel: Int): Uri?
    fun setDoNotDisturbStart(h: Int, m: Int)
    fun setDoNotDisturbEnd(h: Int, m: Int)
    fun getStartH(): Int
    fun getStartM(): Int
    fun getEndH(): Int
    fun getEndM(): Int
    fun getSoundIsAllow(): Boolean
}