package koropapps.yaroslavgorbach.batterysound.services

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import androidx.core.app.NotificationCompat
import koropapps.yaroslavgorbach.batterysound.data.RepoImp
import koropapps.yaroslavgorbach.batterysound.receivers.BatteryReceiver

class BatteryService: Service() {
    private val mBatInfoReceiver: BroadcastReceiver = BatteryReceiver()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        registerReceiver(mBatInfoReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        startForeground(1, NotificationCompat.Builder(this, "1").build())
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mBatInfoReceiver)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}