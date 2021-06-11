package koropapps.yaroslavgorbach.batterysound.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.BatteryManager
import android.util.Log
import koropapps.yaroslavgorbach.batterysound.data.RepoImp
import koropapps.yaroslavgorbach.batterysound.services.MediaPlayerService
import koropapps.yaroslavgorbach.batterysound.services.TextToSpeechService

class BatteryReceiver : BroadcastReceiver() {
    private val repo = RepoImp
    override fun onReceive(context: Context?, intent: Intent?) {
        val batteryLevel = intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
        batteryLevel?.let { it ->
            repo.getFileUri(it)?.let { uri->
                val playerIntent = Intent(context, MediaPlayerService::class.java)
                playerIntent.data = uri
                context?.startService(playerIntent)
            }

            repo.getTextToSpeak(it)?.let { text ->
                val speechIntent = Intent(context, TextToSpeechService::class.java)
                speechIntent.putExtra("MESSAGE", text)
                context?.startService(speechIntent)
            }

        }
    }
}