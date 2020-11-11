package com.example.benefit.ui.auth.registration

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.benefit.R
import com.example.benefit.ui.auth.login.LoginBSD
import com.example.benefit.ui.auth.login.LoginViewModel
import com.example.benefit.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_reg_end.*
import splitties.fragments.start
import javax.inject.Inject

/**
 * Created by jahon on 03-Sep-20
 */
@AndroidEntryPoint
class RegEndFragment @Inject constructor() : Fragment(R.layout.fragment_reg_end) {


    private val viewModel: RegistrationViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        attachListeners()
    }

    private fun attachListeners() {
        tvSkip.setOnClickListener {
            start<MainActivity> {}
            (parentFragment as RegistrationBSD).dismiss()
        }
        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

    }

}