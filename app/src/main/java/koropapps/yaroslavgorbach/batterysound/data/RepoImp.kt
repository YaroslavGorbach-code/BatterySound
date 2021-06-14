package koropapps.yaroslavgorbach.batterysound.data

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import koropapps.yaroslavgorbach.batterysound.data.prefs.CommonPrefImp
import koropapps.yaroslavgorbach.batterysound.data.room.BatteryTask
import koropapps.yaroslavgorbach.batterysound.data.room.Db
import java.util.*

class RepoImp private constructor(context: Context) : Repo {
    private val commonPref = CommonPrefImp(context)
    private val db = Db.getInstance(context)

    companion object {
        private lateinit var instance: Repo
        fun getInstance(context: Context): Repo {
            if (!::instance.isInitialized) {
                instance = RepoImp(context)
            }
            return instance
        }
    }


    override fun getTasks(): LiveData<List<BatteryTask>> {
        return db.batteryDao().getAllLive()
    }

    override suspend fun updateTask(task: BatteryTask) {
        db.batteryDao().update(task)
    }

    override suspend fun addTask(task: BatteryTask) {
        db.batteryDao().insert(task)
    }

    override suspend fun getStartServiceIsAllow(): Boolean {
        Log.v("active" , db.batteryDao().getActive().toString())
        return db.batteryDao().getActive().isNotEmpty()
    }

    override suspend fun getTextToSpeak(batteryLevel: Int): String? {
        db.batteryDao().getAll().forEach { task ->
            if (batteryLevel == task.batteryLevel) {
                if (task.isActive && !task.isConsumed) {
                    task.text?.let {
                        task.isConsumed = true
                        updateTask(task)
                        return it
                    }
                }
            } else {
                task.isConsumed = false
                updateTask(task)
            }
        }
        return null
    }

    override suspend fun removeTask(batteryTask: BatteryTask) {
        db.batteryDao().delete(batteryTask)
    }

    override suspend fun getFileUri(batteryLevel: Int): Uri? {
        db.batteryDao().getAll().forEach { task ->
            if (batteryLevel == task.batteryLevel) {
                if (task.isActive && !task.isConsumed) {
                    task.fileUri?.let {
                        task.isConsumed = true
                        updateTask(task)
                        return it
                    }
                }
            } else {
                task.isConsumed = false
                updateTask(task)
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
