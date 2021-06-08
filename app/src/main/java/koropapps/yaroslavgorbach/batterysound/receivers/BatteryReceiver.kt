package koropapps.yaroslavgorbach.batterysound.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import android.util.Log
import koropapps.yaroslavgorbach.batterysound.data.Repo
import koropapps.yaroslavgorbach.batterysound.data.RepoImp
import koropapps.yaroslavgorbach.batterysound.services.TextToSpeechService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class BatteryReceiver : BroadcastReceiver() {
    private val repo = RepoImp
    override fun onReceive(context: Context?, intent: Intent?) {
        val batteryLevel = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
        batteryLevel?.let {
            val speechIntent = Intent()
            speechIntent.putExtra("MESSAGE", repo.getTextToSpeak(it))
            TextToSpeechService.enqueueWork(context, speechIntent)
        }

    }
}