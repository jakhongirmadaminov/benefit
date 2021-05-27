package com.example.benefit.ui.auth.login

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.benefit.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_phone.*
import javax.inject.Inject

/**
 * Created by jahon on 03-Sep-20
 */
import com.example.benefit.ui.base.BaseFragment

class PhoneFragment : BaseFragment(R.layout.fragment_phone) {

    private val viewModel: LoginViewModel by activityViewModels()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

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
                    progress.visibility = View.INVISIBLE
                    lblYoullReceiveCode.visibility = View.VISIBLE
                }
            }
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            lblYoullReceiveCode.text = it ?: return@Observer
            lblYoullReceiveCode.setTextColor(Color.RED)
        })

        viewModel.loginResp.observe(viewLifecycleOwner, Observer {
            it ?: return@Observer
            findNavController().navigate(R.id.action_phoneFragment_to_codeFragment)
        })


    }

    private fun attachListeners() {
        edtPhone.doOnTextChanged { text, start, before, count ->
            lblYoullReceiveCode.setTextColor(
                ContextCompat.getColor(requireContext(), R.color.textlightGrey)
            )

            if (!text.isNullOrBlank() && text.length == 9) {
                btnGetCode.myEnabled(true)
                lblYoullReceiveCode.visibility = View.VISIBLE
                lblYoullReceiveCode.text =
                    getString(R.string.you_will_receive_code, tvPhoneStart.text.toString() + text)
            } else {
                btnGetCode.myEnabled(false)
                lblYoullReceiveCode.visibility = View.GONE
            }

        }

        btnGetCode.setOnClickListener {
            viewModel.login(edtPhone.text.toString())

        }


    }

}