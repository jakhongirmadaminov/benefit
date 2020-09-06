package com.example.benefit.ui.auth.registration

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.benefit.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_reg_code.*
import kotlinx.android.synthetic.main.fragment_reg_code.ivBack
import javax.inject.Inject

/**
 * Created by jahon on 03-Sep-20
 */
@AndroidEntryPoint
class RegCodeFragment @Inject constructor() : Fragment(R.layout.fragment_reg_code) {


    private val regViewModel: RegistrationViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        attachListeners()
    }

    private fun attachListeners() {
        edtCode.doOnTextChanged { text, start, before, count ->
            if (!text.isNullOrBlank() && text.length == 4) {
                btnConfirm.myEnabled(true)
            } else {
                btnConfirm.myEnabled(false)
            }
        }

        btnConfirm.setOnClickListener {
            findNavController().navigate(R.id.action_regCodeFragment_to_regProfileSetupFragment)
        }

        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}