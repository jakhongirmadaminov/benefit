package com.example.benefit.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.benefit.R
import com.example.benefit.ui.auth.AuthActivity
import com.example.benefit.util.AppPrefs
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import splitties.activities.start
import splitties.experimental.ExperimentalSplittiesApi

@ExperimentalSplittiesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object{
        const val IS_GOING_DEPOSIT = "IS_GOING_DEPOSIT"
    }

    var isGoingDeposit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        isGoingDeposit = intent.getBooleanExtra(IS_GOING_DEPOSIT, false)

        checkUserLogin()

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)
    }

    private fun checkUserLogin() {

        if (AppPrefs.token.isNullOrBlank()) {
            start<AuthActivity>()
            finish()
        }

    }


}