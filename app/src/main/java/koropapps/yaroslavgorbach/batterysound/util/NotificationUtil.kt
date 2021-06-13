package koropapps.yaroslavgorbach.batterysound.util

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import koropapps.yaroslavgorbach.batterysound.MainActivity
import koropapps.yaroslavgorbach.batterysound.R
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
fun Context.getServiceNotification(): Notification {
    val pendingIntent: PendingIntent =
        Intent(this, MainActivity::class.java).let { notificationIntent ->
            PendingIntent.getActivity(this, 0, notificationIntent, 0)
        }

    return NotificationCompat.Builder(this, "1")
        .setContentTitle(getText(R.string.notification_title))
        .setContentText(getText(R.string.notification_message))
        .setSmallIcon(R.drawable.ic_battery_full)
        .setTicker(getText(R.string.ticker_text))
        .setContentIntent(pendingIntent)
        .build()
}