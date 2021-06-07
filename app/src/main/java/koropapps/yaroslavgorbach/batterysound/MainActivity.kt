package koropapps.yaroslavgorbach.batterysound

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import koropapps.yaroslavgorbach.batterysound.databinding.ActivityMainBinding
import koropapps.yaroslavgorbach.batterysound.services.BatteryService


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bind = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(bind.root)
        val serviceIntent = Intent(this, BatteryService::class.java)

        bind.start.setOnClickListener {
            ContextCompat.startForegroundService(this, serviceIntent)
        }
        bind.stop.setOnClickListener {
            stopService(serviceIntent)
        }
    }
}