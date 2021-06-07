package koropapps.yaroslavgorbach.batterysound.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import android.widget.Toast
import koropapps.yaroslavgorbach.batterysound.data.Repo
import koropapps.yaroslavgorbach.batterysound.data.RepoImp

class BatteryReceiver : BroadcastReceiver() {
    private val repo: Repo = RepoImp()

    override fun onReceive(context: Context?, intent: Intent?) {
        val batteryLevel = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
        val task = repo.getTask(0)
        if (task.isActive){
            if (batteryLevel == task.level){
                Toast.makeText(context, "time to charge", Toast.LENGTH_LONG).show()
            }

        }

    }
}