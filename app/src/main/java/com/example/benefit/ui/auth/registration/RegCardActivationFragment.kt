package com.example.benefit.ui.auth.registration

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.benefit.R
import com.example.benefit.ui.main.MainActivity
import com.example.benefit.ui.main.home.bsd_add_card.AddCardBSD
import com.example.benefit.ui.order_card.OrderCardActivity
import com.example.benefit.ui.select_card_type.SelectCardTypeActivity
import com.redmadrobot.inputmask.MaskedTextChangedListener
import com.redmadrobot.inputmask.MaskedTextChangedListener.Companion.installOn
import kotlinx.android.synthetic.main.fragment_reg_card_activation.*
import javax.inject.Inject

/**
 * Created by jahon on 03-Sep-20
 */

class RegCardActivationFragment @Inject constructor() :
    Fragment(R.layout.fragment_reg_card_activation) {


    private val viewModel: RegistrationViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupViews()
        attachListeners()
        subscribeObservers()
    }

    private fun subscribeObservers() {

        viewModel.addNewCardResp.observe(viewLifecycleOwner, {
            val result = it ?: return@observe
            val action =
                RegCardActivationFragmentDirections.actionRegCardActivationFragmentToCardConfirm(
                    result.cardId,
                    null
                )
            findNavController().navigate(action)
        })


        viewModel.isLoading.observe(viewLifecycleOwner, {
            when (it ?: return@observe) {
                true -> {
                    tvInfo.visibility = View.GONE
                    progress.visibility = View.VISIBLE
                }
                else -> {
                    tvInfo.visibility = View.VISIBLE
                    tvInfo.text = getString(R.string.warning_card_activation)
                    progress.visibility = View.GONE
                }
            }
        })

        viewModel.errorMessage.observe(viewLifecycleOwner,
            {
                tvInfo.visibility = View.VISIBLE
                tvInfo.text = it ?: return@observe
            })


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

        installOn(edtExpiryDate, "[00]/[00]")
        val listener2 = MaskedTextChangedListener(
            "[00]/[00]",
            edtExpiryDate,
            object : MaskedTextChangedListener.ValueListener {
                override fun onTextChanged(
                    maskFilled: Boolean,
                    extractedValue: String,
                    formattedValue: String
                ) {
                }
            }
        )
        edtExpiryDate.hint = "01/01"
        edtExpiryDate.onFocusChangeListener = listener2
        edtExpiryDate.addTextChangedListener(listener2)
    }

    private fun attachListeners() {

        edtCardNumber.doOnTextChanged { text, start, before, count ->
            if (text.isNullOrBlank()) {
                ivClearCardNumber.visibility = View.INVISIBLE
            } else {
                ivClearCardNumber.visibility = View.VISIBLE
            }
            validateInputs()
        }

        edtCardName.doOnTextChanged { text, start, before, count ->
            validateInputs()
        }

        edtExpiryDate.doOnTextChanged { text, start, before, count ->
            validateInputs()
        }

        ivClearCardNumber.setOnClickListener {
            edtCardNumber.text.clear()
            ivClearCardNumber.visibility = View.INVISIBLE
        }

        tvNext.setOnClickListener {
            if ((parentFragment is RegistrationBSD)) {
                (parentFragment as RegistrationBSD).dismiss()
                startActivity(Intent(requireActivity(), MainActivity::class.java))
            } else if ((parentFragment is AddCardBSD)) (parentFragment as AddCardBSD).dismiss()
        }

        btnOrderCard.setOnClickListener {
            startActivityForResult(
                Intent(requireActivity(), SelectCardTypeActivity::class.java),
                OrderCardActivity.REQ_ORDER_CARD
            )
        }

        btnActivate.setOnClickListener {

            val expString = edtExpiryDate.text.toString().replace("/", "")

            viewModel.addNewCard(
                edtCardName.text.toString(),
                edtCardNumber.text.toString().replace(" ", ""),
                expString.substring(2) + expString.substring(0, 2)
            )
        }
    }

    private fun validateInputs() {
        btnActivate.myEnabled(
            !edtCardNumber.text.isNullOrBlank() &&
                    edtCardNumber.text.length == 19 &&
                    !edtExpiryDate.text.isNullOrBlank() &&
                    edtExpiryDate.text.length == 5 &&
                    !edtCardName.text.isNullOrBlank()
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == AppCompatActivity.RESULT_OK) {
            if (requestCode == OrderCardActivity.REQ_ORDER_CARD) {
                if (((parentFragment as NavHostFragment).parentFragment is RegistrationBSD)) {
                    ((parentFragment as NavHostFragment).parentFragment as RegistrationBSD).dismiss()
                    startActivity(Intent(requireActivity(), MainActivity::class.java))
                } else if (((parentFragment as NavHostFragment).parentFragment is AddCardBSD)) ((parentFragment as NavHostFragment).parentFragment as AddCardBSD).dismiss()
            }
        }
    }
}