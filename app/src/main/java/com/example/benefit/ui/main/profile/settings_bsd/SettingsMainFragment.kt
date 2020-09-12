package com.example.benefit.ui.main.profile.settings_bsd

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.benefit.R
import com.example.benefit.ui.auth.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_settings_main.*
import javax.inject.Inject

/**
 * Created by jahon on 03-Sep-20
 */
@AndroidEntryPoint
class SettingsMainFragment @Inject constructor() : Fragment(R.layout.fragment_settings_main) {


    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        attachListeners()
        subscribeObservers()
    }

    private fun setupViews() {

        cardPhoto.setBackgroundResource(R.drawable.shape_oval)
        cardPhotoIcon.setBackgroundResource(R.drawable.shape_oval)
    }

    private fun subscribeObservers() {
    }

    private fun attachListeners() {

        llChangePhoneNum.setOnClickListener {
            findNavController().navigate(R.id.action_profileSettingsMainFragment_to_profileSettingsCodeFragment)
        }
        tvChangeCode.setOnClickListener {
            findNavController().navigate(R.id.action_profileSettingsMainFragment_to_profileSettingsChangeCodeFragment)
        }
        tvChangeLang.setOnClickListener {
            findNavController().navigate(R.id.action_profileSettingsMainFragment_to_profileSettingsLangFragment)
        }

    }

}