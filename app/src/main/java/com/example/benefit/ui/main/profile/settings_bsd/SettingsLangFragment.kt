package com.example.benefit.ui.main.profile.settings_bsd

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.benefit.R
import com.example.benefit.ui.auth.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_settings_lang.*
import javax.inject.Inject

/**
 * Created by jahon on 03-Sep-20
 */
@AndroidEntryPoint
class SettingsLangFragment @Inject constructor() : Fragment(R.layout.fragment_settings_lang) {


    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        attachListeners()
        subscribeObservers()
    }

    private fun subscribeObservers() {
    }

    private fun attachListeners() {
        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        llRussian.setOnClickListener {
            rbRu.isChecked = true
            rbUz.isChecked = false
            rbEn.isChecked = false
        }
        llUzbek.setOnClickListener {
            rbUz.isChecked = true
            rbRu.isChecked = false
            rbEn.isChecked = false
        }
        llEnglish.setOnClickListener {
            rbEn.isChecked = true
            rbRu.isChecked = false
            rbUz.isChecked = false
        }

    }

}