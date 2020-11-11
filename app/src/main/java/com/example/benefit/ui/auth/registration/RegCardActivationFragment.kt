package com.example.benefit.ui.auth.registration

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.benefit.R
import com.example.benefit.ui.auth.login.LoginViewModel
import com.example.benefit.ui.main.MainActivity
import com.example.benefit.ui.main.home.bsd_add_card.AddCardBSD
import com.example.benefit.ui.order_card.OrderCardActivity
import com.example.benefit.ui.select_card_type.SelectCardTypeActivity
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


    private val viewModel: RegistrationViewModel by activityViewModels()

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
            if ((parentFragment is RegistrationBSD)) {
                (parentFragment as RegistrationBSD).dismiss()
                start<MainActivity> {}
            } else if ((parentFragment is AddCardBSD)) (parentFragment as AddCardBSD).dismiss()
        }

        btnOrderCard.setOnClickListener {
            startActivityForResult(
                Intent(requireActivity(), SelectCardTypeActivity::class.java),
                OrderCardActivity.REQ_ORDER_CARD
            )
//            start<SelectCardTypeActivity> {}
        }

        btnActivate.setOnClickListener {
            findNavController().navigate(R.id.action_regCardActivationFragment_to_regEndFragment)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == AppCompatActivity.RESULT_OK) {
            if (requestCode == OrderCardActivity.REQ_ORDER_CARD) {
                if (((parentFragment as NavHostFragment).parentFragment is RegistrationBSD)) {
                    ((parentFragment as NavHostFragment).parentFragment as RegistrationBSD).dismiss()
                    start<MainActivity> {}
                } else if (((parentFragment as NavHostFragment).parentFragment is AddCardBSD)) ((parentFragment as NavHostFragment).parentFragment as AddCardBSD).dismiss()
            }
        }
    }
}