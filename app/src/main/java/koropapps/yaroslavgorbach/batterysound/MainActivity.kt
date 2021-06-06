package koropapps.yaroslavgorbach.batterysound

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
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