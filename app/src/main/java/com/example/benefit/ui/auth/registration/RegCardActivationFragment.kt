package com.example.benefit.ui.auth.registration

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.benefit.R
import com.example.benefit.ui.auth.login.LoginViewModel
import com.example.benefit.ui.auth.order_card.OrderCardActivity
import com.example.benefit.ui.main.MainActivity
import com.redmadrobot.inputmask.MaskedTextChangedListener
import com.redmadrobot.inputmask.MaskedTextChangedListener.Companion.installOn
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_reg_card_activation.*
import splitties.fragments.start
import javax.inject.Inject

/**
 * Created by jahon on 03-Sep-20
 */
@AndroidEntryPoint
class RegCardActivationFragment @Inject constructor() :
    Fragment(R.layout.fragment_reg_card_activation) {


    private val loginViewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupViews()
        attachListeners()
    }

    private fun setupViews() {

        installOn(edtCardNumber, "[0000] [0000] [0000] [0000]")
        val listener = MaskedTextChangedListener(
            "[0000] [0000] [0000] [0000]",
            edtCardNumber,
            object : MaskedTextChangedListener.ValueListener {
                override fun onTextChanged(
                    maskFilled: Boolean,
                    extractedValue: String,
                    formattedValue: String
                ) {
                }
            }
        )
        edtCardNumber.hint = "0000 0000 0000 0000"
        edtCardNumber.onFocusChangeListener = listener
        edtCardNumber.addTextChangedListener(listener)
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
        tvNext.setOnClickListener {
//            loginViewModel.login("998" + edtPhone.text.toString())
            start<MainActivity> {}
            (parentFragment as RegistrationBSD).dismiss()
        }
        btnOrderCard.setOnClickListener {
            start<OrderCardActivity> {}
        }
        btnActivate.setOnClickListener {
            findNavController().navigate(R.id.action_regCardActivationFragment_to_regEndFragment)
        }
    }

}