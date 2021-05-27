package com.example.benefit.ui.main.profile.settings_bsd

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.benefit.R
import com.example.benefit.ui.auth.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_settings_new_code.*
import kotlinx.android.synthetic.main.fragment_settings_new_code.ivBack
import kotlinx.android.synthetic.main.fragment_settings_phone.*
import javax.inject.Inject

/**
 * Created by jahon on 03-Sep-20
 */

class SettingsNewCodeFragment @Inject constructor() :
    Fragment(R.layout.fragment_settings_new_code) {


    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        attachListeners()
        subscribeObservers()
    }

    private fun subscribeObservers() {
    }

    private fun attachListeners() {
        edtCode.doOnTextChanged { text, start, before, count ->
            if (!text.isNullOrBlank() && text.length == 4) {
                btnConfirm.myEnabled(true)
            } else {
                btnConfirm.myEnabled(false)
            }
        }
        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}