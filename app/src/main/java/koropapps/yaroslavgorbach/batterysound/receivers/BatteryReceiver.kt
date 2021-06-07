package koropapps.yaroslavgorbach.batterysound.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import koropapps.yaroslavgorbach.batterysound.data.Repo
import koropapps.yaroslavgorbach.batterysound.data.RepoImp
import koropapps.yaroslavgorbach.batterysound.services.TextToSpeechService


class BatteryReceiver : BroadcastReceiver() {
    private val repo: Repo = RepoImp()
    override fun onReceive(context: Context?, intent: Intent?) {
        val batteryLevel = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)

        repo.getTasks().forEach { task ->
            if (task.isActive && !task.isConsumed) {
                if (batteryLevel == task.level) {
                    task.text?.let {
                        val speechIntent = Intent()
                        speechIntent.putExtra("MESSAGE", it)
                        TextToSpeechService.enqueueWork(context, speechIntent)
                        task.isConsumed = true
                    }
                }else{
                    task.isConsumed = false
                }
            }
        }
    }
}