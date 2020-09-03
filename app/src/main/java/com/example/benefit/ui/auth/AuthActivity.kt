package com.example.benefit.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.FragmentFactory
import com.example.benefit.R
import javax.inject.Inject
import javax.inject.Named

class AuthActivity : AppCompatActivity() {

//    @Inject
//    lateinit var viewModelFactory: AuthFragmentFactory

//    @Inject
//    @Named("AuthFragmentFactory")
//    lateinit var fragmentFactory: FragmentFactory

//    private val viewModel: AuthViewModel by viewModels {
//        viewModelFactory
//    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
    }
}