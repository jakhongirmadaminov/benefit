package com.example.benefit.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.benefit.R
import com.example.benefit.ui.auth.AuthActivity
import com.example.benefit.ui.base.BaseActivity
import com.example.benefit.ui.main.pin.PinActivity
import com.example.benefit.util.AppPrefs
import com.example.benefit.util.ContextUtils.setLocale
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import splitties.activities.start
import splitties.experimental.ExperimentalSplittiesApi

@ExperimentalSplittiesApi
class MainActivity : BaseActivity() {

    companion object {
        const val IS_GOING_DEPOSIT = "IS_GOING_DEPOSIT"
        const val IS_JUST_LOGGED_IN = "IS_JUST_LOGGED_IN"
    }

    private var pinTimerJob: Job? = null

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
        }/* else if (shouldEnterPin()) {
            startActivity(Intent(this, PinActivity::class.java))
        }*/

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