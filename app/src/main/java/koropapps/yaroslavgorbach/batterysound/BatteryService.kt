package koropapps.yaroslavgorbach.batterysound

import android.app.Notification
import android.app.PendingIntent
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import androidx.core.app.NotificationCompat

class BatteryService: Service() {
    private val mBatInfoReceiver: BroadcastReceiver = BatteryReceiver()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        this.registerReceiver(mBatInfoReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))

        val notification: Notification = NotificationCompat
            .Builder(this, "1")
            .build()
        startForeground(1, notification)
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        this.unregisterReceiver(mBatInfoReceiver)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}