package com.example.benefit.ui.main.profile.settings_bsd

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.benefit.R
import com.example.benefit.ui.auth.login.LoginBSD
import com.example.benefit.ui.auth.login.LoginViewModel
import com.example.benefit.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_reg_code.*
import kotlinx.android.synthetic.main.fragment_settings_phone.*
import kotlinx.android.synthetic.main.fragment_settings_phone.ivBack
import splitties.fragments.start
import javax.inject.Inject

/**
 * Created by jahon on 03-Sep-20
 */
import com.example.benefit.ui.base.BaseFragment

class SettingsPhoneFragment : BaseFragment(R.layout.fragment_settings_phone) {


    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        attachListeners()
        subscribeObservers()
    }

    private fun subscribeObservers() {
    }

    private fun attachListeners() {
        edtPhone.doOnTextChanged { text, start, before, count ->
            if (!text.isNullOrBlank() && text.length == 9) {
                btnGetCode.myEnabled(true)
//                lblYoullReceiveCode.visibility = View.VISIBLE
//                lblYoullReceiveCode.text =
//                    getString(R.string.you_will_receive_code, tvPhoneStart.text.toString() + text)
            } else {
                btnGetCode.myEnabled(false)
//                lblYoullReceiveCode.visibility = View.GONE
            }

        }

        ivBack.setOnClickListener {
                findNavController().popBackStack()
        }

    }

}