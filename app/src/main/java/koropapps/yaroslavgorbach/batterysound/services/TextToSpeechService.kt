package koropapps.yaroslavgorbach.batterysound.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.core.app.JobIntentService
import androidx.core.app.NotificationCompat
import java.util.*
import kotlin.random.Random


class TextToSpeechService : Service() {
    private var mySpeakTextToSpeech: TextToSpeech? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val message = intent?.getStringExtra("MESSAGE")
        mySpeakTextToSpeech = TextToSpeech(applicationContext) {
            mySpeakTextToSpeech?.language = Locale.getDefault()
            mySpeakTextToSpeech!!.speak(message, TextToSpeech.QUEUE_ADD, null, Random.toString())
        }
        startForeground(1, NotificationCompat.Builder(this, "1").build())
        return START_NOT_STICKY
    }


    override fun onDestroy() {
        mySpeakTextToSpeech?.shutdown()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

}