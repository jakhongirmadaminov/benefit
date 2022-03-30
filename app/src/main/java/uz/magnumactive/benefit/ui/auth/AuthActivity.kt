package uz.magnumactive.benefit.ui.auth

import android.os.Bundle
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.ui.auth.login.LoginBSD
import uz.magnumactive.benefit.ui.auth.registration.RegistrationBSD
import uz.magnumactive.benefit.ui.base.BaseActivity
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