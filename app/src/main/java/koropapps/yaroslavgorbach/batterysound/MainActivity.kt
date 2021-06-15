package koropapps.yaroslavgorbach.batterysound

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import koropapps.yaroslavgorbach.batterysound.screen.tasks.TasksListFragment
import koropapps.yaroslavgorbach.batterysound.workers.RestartBatteryServiceWorker
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                val fragment = TasksListFragment()
                add(R.id.main_container, fragment)
                setPrimaryNavigationFragment(fragment)
            }
            RestartBatteryServiceWorker.requestRestart(this)
        }
    }
}