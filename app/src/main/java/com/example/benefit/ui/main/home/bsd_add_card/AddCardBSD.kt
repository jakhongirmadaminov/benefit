package com.example.benefit.ui.main.home.bsd_add_card

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.benefit.R
import com.example.benefit.ui.auth.registration.RegistrationViewModel
import com.example.benefit.util.MyBSDialog
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddCardBSD : MyBSDialog() {

    private val viewModel: RegistrationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bsd_add_card, container)

        return view
    }


}