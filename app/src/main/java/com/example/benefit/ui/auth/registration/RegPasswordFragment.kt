package com.example.benefit.ui.auth.registration

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.benefit.R
import com.example.benefit.ui.auth.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_reg_password.*
import javax.inject.Inject

/**
 * Created by jahon on 03-Sep-20
 */
@AndroidEntryPoint
class RegPasswordFragment @Inject constructor() : Fragment(R.layout.fragment_reg_password) {


    private val loginViewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        attachListeners()
    }

    private fun attachListeners() {
        edtCode.doOnTextChanged { text, start, before, count ->
            if (!text.isNullOrBlank()) {
                btnConfirm.myEnabled(true)
            } else {
                btnConfirm.myEnabled(false)
            }

        }

        btnConfirm.setOnClickListener {
//            loginViewModel.login("998" + edtCode.text.toString())
            findNavController().navigate(R.id.action_regPasswordFragment_to_regCardActivationFragment)
        }
    }

}