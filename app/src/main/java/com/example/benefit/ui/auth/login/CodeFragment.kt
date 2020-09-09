package com.example.benefit.ui.auth.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.benefit.R
import com.example.benefit.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_code.*
import splitties.fragments.start
import javax.inject.Inject

/**
 * Created by jahon on 03-Sep-20
 */
@AndroidEntryPoint
class CodeFragment @Inject constructor() : Fragment(R.layout.fragment_code) {


    private val loginViewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        attachListeners()
    }

    private fun attachListeners() {
//        edtPhone.doOnTextChanged { text, start, before, count ->
//            if (!text.isNullOrBlank() && text.length == 9) {
//                btnGetCode.myEnabled(true)
//                lblYoullReceiveCode.visibility = View.VISIBLE
//                lblYoullReceiveCode.text =
//                    getString(R.string.you_will_receive_code, tvPhoneStart.text.toString() + text)
//            } else {
//                btnGetCode.myEnabled(false)
//                lblYoullReceiveCode.visibility = View.GONE
//            }
//
//        }
//
        btnConfirm.setOnClickListener {
//            loginViewModel.login("998" + edtPhone.text.toString())
            start<MainActivity> {}
            (parentFragment as LoginBSD).dismiss()
        }
    }

}