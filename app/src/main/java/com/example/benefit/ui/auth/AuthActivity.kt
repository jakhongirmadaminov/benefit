package com.example.benefit.ui.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentFactory
import com.example.benefit.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_auth.*
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

//    @Inject
//    lateinit var viewModelFactory: AuthFragmentFactory

//    @Inject
//    @Named("AuthFragmentFactory")
//    lateinit var fragmentFactory: FragmentFactory

//    private val viewModel: AuthViewModel by viewModels {
//        viewModelFactory
//    }


//    @Inject
//    @Named("LoginFragmentFactory")
//    lateinit var fragmentFactory: FragmentFactory



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
    }
}