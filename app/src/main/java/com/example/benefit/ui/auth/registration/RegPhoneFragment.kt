package com.example.benefit.ui.auth.registration

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.benefit.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_reg_phone.*
import javax.inject.Inject

/**
 * Created by jahon on 03-Sep-20
 */
@AndroidEntryPoint
class RegPhoneFragment @Inject constructor() : Fragment(R.layout.fragment_reg_phone) {


    private val viewModel: RegistrationViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        attachListeners()
        subscribeObservers()
    }

    private fun subscribeObservers() {

        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            when (it ?: return@Observer) {
                true -> {
                    progress.visibility = View.VISIBLE
                    lblYoullReceiveCode.visibility = View.INVISIBLE
                }
                else -> {
                    progress.visibility = View.GONE
                    lblYoullReceiveCode.visibility = View.VISIBLE
                }
            }
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            lblYoullReceiveCode.text = it ?: return@Observer
            lblYoullReceiveCode.visibility = View.VISIBLE
            lblYoullReceiveCode.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.error_red
                )
            )
        })

        viewModel.signUpResp.observe(viewLifecycleOwner, Observer {

            findNavController().navigate(R.id.action_regPhoneFragment_to_regCodeFragment)


        })


    }


    private fun attachListeners() {
        edtPhone.doOnTextChanged { text, start, before, count ->
            if (!text.isNullOrBlank() && text.length == 9 && cbTermsAgree.isChecked) {
                btnGetCode.myEnabled(true)
                lblYoullReceiveCode.visibility = View.VISIBLE
                lblYoullReceiveCode.text =
                    getString(R.string.you_will_receive_code, tvPhoneStart.text.toString() + text)
                lblYoullReceiveCode.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.textlightGrey
                    )
                )
            } else {
                btnGetCode.myEnabled(false)
                lblYoullReceiveCode.visibility = View.GONE

            }

        }

        cbTermsAgree.setOnCheckedChangeListener { buttonView, isChecked ->
            if (!edtPhone.text.isNullOrBlank() && edtPhone.text.length == 9 && cbTermsAgree.isChecked) {
                btnGetCode.myEnabled(true)
                lblYoullReceiveCode.visibility = View.VISIBLE
                lblYoullReceiveCode.text =
                    getString(
                        R.string.you_will_receive_code,
                        tvPhoneStart.text.toString() + edtPhone.text
                    )
            } else {
                btnGetCode.myEnabled(false)
                lblYoullReceiveCode.visibility = View.GONE
            }
        }
        btnGetCode.setOnClickListener {
            viewModel.signup(edtPhone.text.toString())
        }
    }


}