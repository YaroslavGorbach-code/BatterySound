package koropapps.yaroslavgorbach.batterysound.services

import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import androidx.core.app.JobIntentService

class MediaPlayerService: JobIntentService() {

    override fun onHandleWork(intent: Intent) {
        val uri: Uri = intent.data!!
        Log.v("uri", uri.path.toString())
        MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            setDataSource(applicationContext, uri)
            prepare()
            setOnPreparedListener {
                start()
            }
        }
    }


    companion object {
        fun enqueueWork(context: Context?, intent: Intent?) {
            enqueueWork(context!!, MediaPlayerService::class.java, 1, intent!!)
        }
    }
}