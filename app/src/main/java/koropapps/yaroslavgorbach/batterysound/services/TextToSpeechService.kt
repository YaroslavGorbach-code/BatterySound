package koropapps.yaroslavgorbach.batterysound.services

import android.content.Context
import android.content.Intent
import android.speech.tts.TextToSpeech
import androidx.core.app.JobIntentService
import java.util.*


class TextToSpeechService : JobIntentService() {
    private var mySpeakTextToSpeech: TextToSpeech? = null

    override fun onHandleWork(intent: Intent) {
        val message = intent.getStringExtra("MESSAGE")

        mySpeakTextToSpeech = TextToSpeech(applicationContext) {
            mySpeakTextToSpeech?.language = Locale.getDefault()
            mySpeakTextToSpeech!!.speak(message, TextToSpeech.QUEUE_ADD, null, null)
        }
        //stopSelf()
    }

    override fun onDestroy() {
            if (mySpeakTextToSpeech != null) {
                mySpeakTextToSpeech!!.shutdown()
            super.onDestroy()
        }
    }

    companion object {
        fun enqueueWork(context: Context?, intent: Intent?) {
            enqueueWork(context!!, TextToSpeechService::class.java, 1, intent!!)
        }
    }
}