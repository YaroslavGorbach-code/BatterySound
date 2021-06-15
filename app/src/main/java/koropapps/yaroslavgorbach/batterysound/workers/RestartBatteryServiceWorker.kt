package koropapps.yaroslavgorbach.batterysound.workers

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import koropapps.yaroslavgorbach.batterysound.data.Repo
import koropapps.yaroslavgorbach.batterysound.data.RepoImp
import koropapps.yaroslavgorbach.batterysound.services.BatteryService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@InternalCoroutinesApi
class RestartBatteryServiceWorker(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {
    override fun doWork(): Result {
        val repo: Repo = RepoImp.getInstance(applicationContext)
        GlobalScope.launch {
            if (repo.getStartServiceIsAllow())
                ContextCompat.startForegroundService(
                    applicationContext,
                    Intent(applicationContext, BatteryService::class.java)
                )
        }
        return Result.success()
    }

    companion object {
        fun requestRestart(context: Context) {
            val startServiceRequest =
                PeriodicWorkRequestBuilder<RestartBatteryServiceWorker>(20, TimeUnit.MINUTES)
                    .build()
            WorkManager
                .getInstance(context)
                .enqueue(startServiceRequest)
        }
    }
}

