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
import com.example.benefit.util.ResultError
import com.example.benefit.util.ResultSuccess
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_settings_main.*
import splitties.experimental.ExperimentalSplittiesApi
import splitties.preferences.edit

/**
 * Created by jahon on 03-Sep-20
 */
class SettingsMainFragment : BaseFragment(R.layout.fragment_settings_main) {


    private val viewModel: SetingsMainViewModel by viewModels()

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

    @ExperimentalSplittiesApi
    private fun subscribeObservers() {
        viewModel.isLoading.observe(viewLifecycleOwner, {
            progress.isVisible = it
        })

        viewModel.uploadUserInfoResp.observe(viewLifecycleOwner, {
            findNavController().popBackStack()
        })
        viewModel.userInfoResp.observe(viewLifecycleOwner, {
            val result = it ?: return@observe
            when (result) {
                is ResultError -> {
                    Snackbar.make(
                        clParent,
                        result.message ?: "Something went wrong!",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                is ResultSuccess -> {
                    AppPrefs.edit {
                        firstName = result.value.first_name
                        this.lastName = result.value.last_name
                        result.value.gender?.let {
                            this.gender = it
                        }
                        this.dobMillis = result.value.birth_day!!.toLong()
                    }
                    edtName.setText(result.value.first_name)
                    edtSurname.setText(result.value.last_name)
                }
            }
        })
        viewModel.errorMessage.observe(viewLifecycleOwner, {
            Snackbar.make(clParent, it, Snackbar.LENGTH_SHORT).show()
        })
    }

    private fun attachListeners() {

        llChangePhoneNum.setOnClickListener {
            findNavController().navigate(SettingsMainFragmentDirections.actionSettingsMainFragmentToSettingsCodeFragment())
        }
        tvChangeCode.setOnClickListener {
            findNavController().navigate(R.id.action_settingsMainFragment_to_settingsChangeCodeFragment)
        }
        tvChangeLang.setOnClickListener {
            findNavController().navigate(R.id.action_settingsMainFragment_to_settingsLangFragment)
        }

        tvReady.setOnClickListener {
            viewModel.updateUserInfo(edtSurname.text.toString(), edtName.text.toString())
        }

    }

}