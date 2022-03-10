package com.example.benefit

import android.app.Application
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import com.example.benefit.ui.main.pin.PinActivity
import com.example.benefit.util.AppPrefs
import com.google.android.exoplayer2.database.DatabaseProvider
import com.google.android.exoplayer2.database.ExoDatabaseProvider
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.*
import timber.log.Timber


@HiltAndroidApp
class App : Application() {


    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        val leastRecentlyUsedCacheEvictor = LeastRecentlyUsedCacheEvictor(90 * 1024 * 1024)
        val databaseProvider: DatabaseProvider = ExoDatabaseProvider(this)

        if (simpleCache == null) {
            simpleCache = SimpleCache(cacheDir, leastRecentlyUsedCacheEvictor, databaseProvider)
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        if (AppPrefs.isLoggedIn()) {
            startActivity(Intent(this, PinActivity::class.java).apply {
                flags = FLAG_ACTIVITY_NEW_TASK
            })
            startTimerForPin()
        }
    }

    companion object {
        var simpleCache: SimpleCache? = null
        var INSTANCE: App? = null
        private var pinTimerJob: Job? = null
        val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    }

    private fun shouldEnterPin(): Boolean {
        return AppPrefs.isLoggedIn() && pinTimerJob == null || pinTimerJob?.isCompleted == true
    }

    fun isActivityVisible(): Boolean {
        return activityVisible
    }

    fun activityResumed() {
        if (shouldEnterPin()) {
            startActivity(Intent(this, PinActivity::class.java).apply {
                flags = FLAG_ACTIVITY_NEW_TASK
            })
            startTimerForPin()
        }
        activityVisible = true
        pinTimerJob?.cancel()

    }

    fun activityPaused() {
        activityVisible = false
        startTimerForPin()
    }

    private var activityVisible = false


    private fun startTimerForPin() {
        pinTimerJob?.cancel()
        pinTimerJob = applicationScope.launch {
            delay(20_000)
        }
    }
}