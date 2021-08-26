package com.example.benefit.ui.main.profile.settings_bsd

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.benefit.R
import com.example.benefit.ui.base.BaseFragment
import com.example.benefit.util.AppPrefs
import com.example.benefit.util.ResultError
import com.example.benefit.util.ResultSuccess
import kotlinx.android.synthetic.main.fragment_settings_new_code.*
import splitties.preferences.edit
import javax.inject.Inject

/**
 * Created by jahon on 03-Sep-20
 */

class SettingsNewCodeFragment @Inject constructor() :
    BaseFragment(R.layout.fragment_settings_new_code) {


    private val viewModel: SettingsBSDViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        attachListeners()
        subscribeObservers()
    }

    private fun subscribeObservers() {

        viewModel.newPasswordLoading.observe(viewLifecycleOwner) { isLoading ->
            progress.isVisible = isLoading
            edtCode.isVisible = !isLoading
            btnConfirm.isVisible = !isLoading
            lblEnterCode.isVisible = !isLoading

            if (isLoading) {
                lblTitle.text = getString(R.string.password_change)
            } else {
                lblTitle.text = getString(R.string.change_code)
            }

        }

        viewModel.newPasswordResp.observe(viewLifecycleOwner) {
            when (it) {
                is ResultError -> {
                    edtCode.isVisible = true
                    btnConfirm.isVisible = true
                    lblEnterCode.isVisible = true
                    lblTitle.text = getString(R.string.password_change)
                }
                is ResultSuccess -> {
                    successTick.isVisible = true
                    lblTitle.text = getString(R.string.password_successfully_changed)
                    btnConfirm.text = getString(R.string.close)
                    edtCode.isVisible = false
                    lblEnterCode.isVisible = false
                    btnConfirm.setOnClickListener {
                        ((parentFragment as NavHostFragment).parentFragment as SettingsBSD).dismiss()
                    }
                    ivBack.setOnClickListener {
                        ((parentFragment as NavHostFragment).parentFragment as SettingsBSD).dismiss()
                    }
                    AppPrefs.edit {
                        pin = edtCode.text.toString()
                    }
                }
            }
        }

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

        btnConfirm.setOnClickListener {
            viewModel.setNewCode(edtCode.text.toString())
        }
    }

}