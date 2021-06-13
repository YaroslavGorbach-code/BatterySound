package koropapps.yaroslavgorbach.batterysound.services

import android.app.Notification
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.core.app.JobIntentService
import androidx.core.app.NotificationCompat
import koropapps.yaroslavgorbach.batterysound.R
import koropapps.yaroslavgorbach.batterysound.util.getServiceNotification
import kotlinx.coroutines.InternalCoroutinesApi
import java.util.*
import kotlin.random.Random


@InternalCoroutinesApi
class TextToSpeechService : Service() {
    private var mySpeakTextToSpeech: TextToSpeech? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val message = intent?.getStringExtra("MESSAGE")
        mySpeakTextToSpeech = TextToSpeech(applicationContext) {
            mySpeakTextToSpeech?.language = Locale.getDefault()
            mySpeakTextToSpeech!!.speak(message, TextToSpeech.QUEUE_ADD, null, Random.toString())
        }

        startForeground(1, this.getServiceNotification())
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