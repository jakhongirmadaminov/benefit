package com.example.benefit.ui.main.profile.settings_bsd

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.benefit.R
import com.example.benefit.ui.base.BaseFragment
import com.example.benefit.ui.main.profile.ProfileViewModel
import com.example.benefit.util.AppPrefs
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_settings_main.*

/**
 * Created by jahon on 03-Sep-20
 */
class SettingsMainFragment : BaseFragment(R.layout.fragment_settings_main) {


    private val viewModel: ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        attachListeners()
        subscribeObservers()
    }

    private fun setupViews() {
        edtSurname.setText(AppPrefs.lastName)
        edtName.setText(AppPrefs.firstName)
        lblPhoneNum.setText(AppPrefs.phoneNumber)
        cardPhoto.setBackgroundResource(R.drawable.shape_oval)
        cardPhotoIcon.setBackgroundResource(R.drawable.shape_oval)
    }

    private fun subscribeObservers() {
        viewModel.isLoading.observe(viewLifecycleOwner, {
            progress.isVisible = it
        })

        viewModel.uploadUserInfoResp.observe(viewLifecycleOwner, {
            findNavController().popBackStack()
        })
        viewModel.errorMessage.observe(viewLifecycleOwner, {
            Snackbar.make(clParent, it, Snackbar.LENGTH_SHORT).show()
        })
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

        tvReady.setOnClickListener {
            viewModel.updateUserInfo(edtSurname.text.toString(), edtName.text.toString())
        }

    }

}