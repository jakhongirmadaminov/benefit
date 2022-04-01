package uz.magnumactive.benefit.ui.main

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import splitties.activities.start
import splitties.experimental.ExperimentalSplittiesApi
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.ui.auth.AuthActivity
import uz.magnumactive.benefit.ui.base.BaseActivity
import uz.magnumactive.benefit.util.AppPrefs
import uz.magnumactive.benefit.util.ContextUtils.setLocale

@ExperimentalSplittiesApi
class MainActivity : BaseActivity() {

    companion object {
        const val IS_GOING_DEPOSIT = "IS_GOING_DEPOSIT"
        const val IS_JUST_LOGGED_IN = "IS_JUST_LOGGED_IN"
    }

    private var pinTimerJob: Job? = null
    private val viewModel: MainViewModel by viewModels()

    var isGoingDeposit = false
    var isJustLoggedIn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        setLocale(AppPrefs.language, this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        isGoingDeposit = intent.getBooleanExtra(IS_GOING_DEPOSIT, false)
        isJustLoggedIn = intent.getBooleanExtra(IS_JUST_LOGGED_IN, false)

        checkUserLogin()

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)
    }

    private fun checkUserLogin() {

        if (AppPrefs.token.isNullOrBlank()) {
            start<AuthActivity>()
            finish()
        }else{
            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("TAG", "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new FCM registration token
                val token = task.result
                viewModel.sendFCMToken(token)
            })
        }

    }

    override fun onRestart() {
        super.onRestart()
//        if (shouldEnterPin()) {
//            startActivity(Intent(this, PinActivity::class.java))
//            startTimerForPin()
//        }
    }

    private fun shouldEnterPin(): Boolean {
        return !isJustLoggedIn && AppPrefs.isLoggedIn() && pinTimerJob == null || pinTimerJob?.isCompleted == true
    }

    override fun onStop() {
        super.onStop()
        startTimerForPin()
    }

    private fun startTimerForPin() {
        pinTimerJob?.cancel()
        pinTimerJob = lifecycleScope.launch {
            delay(20_000)
        }
    }
}