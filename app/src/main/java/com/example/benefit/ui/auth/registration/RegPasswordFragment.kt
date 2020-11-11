package com.example.benefit.ui.auth.registration

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.benefit.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_reg_password.*
import javax.inject.Inject

/**
 * Created by jahon on 03-Sep-20
 */
@AndroidEntryPoint
class RegPasswordFragment @Inject constructor() : Fragment(R.layout.fragment_reg_password) {


    private val viewModel: RegistrationViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        attachListeners()
        subscribeObservers()
    }

    private fun subscribeObservers() {

        viewModel.setPasswordResp.observe(viewLifecycleOwner, {
            findNavController().navigate(R.id.action_regPasswordFragment_to_regProfileSetupFragment)
        })


        viewModel.isLoading.observe(viewLifecycleOwner, {
            when (it ?: return@observe) {
                true -> {
                    tvError.visibility = View.GONE
                    progress.visibility = View.VISIBLE
                }
                else -> {
                    progress.visibility = View.GONE
                }
            }
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, {
            tvError.text = it ?: return@observe
            tvError.visibility = View.VISIBLE

        })

    }

    private fun attachListeners() {

        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        edtCode.doOnTextChanged { text, start, before, count ->
            if (!text.isNullOrBlank() && text.length == 4) {
                btnConfirm.myEnabled(true)
            } else {
                btnConfirm.myEnabled(false)
            }

        }

        btnConfirm.setOnClickListener {
            viewModel.setPassword(edtCode.text.toString())
        }
    }

}