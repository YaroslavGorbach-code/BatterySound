package koropapps.yaroslavgorbach.batterysound.services

import android.app.Notification
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import koropapps.yaroslavgorbach.batterysound.R
import koropapps.yaroslavgorbach.batterysound.receivers.BatteryReceiver
import koropapps.yaroslavgorbach.batterysound.util.getServiceNotification
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class BatteryService : Service() {
    private val mBatInfoReceiver: BroadcastReceiver = BatteryReceiver()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        registerReceiver(mBatInfoReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        startForeground(1, this.getServiceNotification())
        return START_REDELIVER_INTENT
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mBatInfoReceiver)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}