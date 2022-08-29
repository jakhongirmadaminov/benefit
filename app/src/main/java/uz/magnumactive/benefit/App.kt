package uz.magnumactive.benefit

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import uz.magnumactive.benefit.ui.main.pin.PinActivity
import uz.magnumactive.benefit.util.AppPrefs
import com.google.android.exoplayer2.database.DatabaseProvider
import com.google.android.exoplayer2.database.ExoDatabaseProvider
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor
import com.google.android.exoplayer2.upstream.cache.SimpleCache
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.*
import timber.log.Timber


@HiltAndroidApp
class App : Application(), Application.ActivityLifecycleCallbacks {


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
        registerActivityLifecycleCallbacks(this)

        if (AppPrefs.isLoggedIn()) {
            startActivity(Intent(this, PinActivity::class.java).apply {
                flags = FLAG_ACTIVITY_NEW_TASK
            })
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
        if (shouldEnterPin() && resumedActivity !is PinActivity) {
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

    var resumedActivity: Activity? = null

    private fun startTimerForPin() {
        pinTimerJob?.cancel()
        pinTimerJob = applicationScope.launch {
            delay(20_000)
        }
    }

    override fun onActivityCreated(p0: Activity, p1: Bundle?) {
    }

    override fun onActivityStarted(p0: Activity) {
    }

    override fun onActivityResumed(p0: Activity) {
        resumedActivity = p0
    }

    override fun onActivityPaused(p0: Activity) {
    }

    override fun onActivityStopped(p0: Activity) {
    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
    }

    override fun onActivityDestroyed(p0: Activity) {
    }
}