package koropapps.yaroslavgorbach.batterysound.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import android.util.Log
import koropapps.yaroslavgorbach.batterysound.data.Repo
import koropapps.yaroslavgorbach.batterysound.data.RepoImp
import koropapps.yaroslavgorbach.batterysound.services.MediaPlayerService
import koropapps.yaroslavgorbach.batterysound.services.TextToSpeechService
import koropapps.yaroslavgorbach.batterysound.util.getName
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch

@InternalCoroutinesApi
class BatteryReceiver : BroadcastReceiver() {
    private lateinit var repo: Repo
    override fun onReceive(context: Context?, intent: Intent?) {
        repo = RepoImp.getInstance(context!!)
        val batteryLevel = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
        batteryLevel?.let { it ->
            GlobalScope.launch {
                if (repo.getSoundIsAllow()){
                    repo.getFileUri(it)?.let { uri->
                        val playerIntent = Intent(context, MediaPlayerService::class.java)
                        playerIntent.data = uri
                        context.startService(playerIntent)
                    }

                    repo.getTextToSpeak(it)?.let { text ->
                        val speechIntent = Intent(context, TextToSpeechService::class.java)
                        speechIntent.putExtra("MESSAGE", text)
                        context.startService(speechIntent)
                    }
                }
            }
        }
    }
}