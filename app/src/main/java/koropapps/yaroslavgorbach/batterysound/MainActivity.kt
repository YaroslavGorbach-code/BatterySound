package koropapps.yaroslavgorbach.batterysound

import android.content.BroadcastReceiver
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import koropapps.yaroslavgorbach.batterysound.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bind = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(bind.root)
        val serviceIntent = Intent(this, BatteryService::class.java)
        serviceIntent.putExtra("test", "service is running...")
        bind.start.setOnClickListener {
            ContextCompat.startForegroundService(this, serviceIntent)
        }
        bind.stop.setOnClickListener {
            stopService(serviceIntent)
        }
    }
}