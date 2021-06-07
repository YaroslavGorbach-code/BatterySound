package koropapps.yaroslavgorbach.batterysound

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.os.Build
import koropapps.yaroslavgorbach.batterysound.data.RepoImp
import koropapps.yaroslavgorbach.batterysound.data.RepoProvider

class App : Application(), RepoProvider {
    private val repo by lazy { RepoImp() }
    override fun onCreate() {
        super.onCreate()
        createChannel()
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                "1",
                "Service notification",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManager = this.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    override fun provideRepo(): RepoImp {
        return repo
    }
}