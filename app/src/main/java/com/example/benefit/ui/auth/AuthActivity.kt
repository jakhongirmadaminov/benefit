package com.example.benefit.ui.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.benefit.R
import com.example.benefit.ui.auth.login.LoginBSD
import com.example.benefit.ui.auth.registration.RegistrationBSD
import com.example.benefit.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_auth.*


class AuthActivity : BaseActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        attachListeners()
    }

    private fun attachListeners() {

        btnLogin.setOnClickListener {
            val bsdLogin = LoginBSD()
            bsdLogin.show(supportFragmentManager, "")
        }
        btnRegister.setOnClickListener {
            val bsdRegister = RegistrationBSD()
            bsdRegister.show(supportFragmentManager, "")
        }
    }
}