package com.example.benefit.ui.regular_payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.benefit.R
import com.example.benefit.remote.models.AutoPaymentDTO
import com.example.benefit.ui.main.BenefitFixedHeightBSD

class CreateRegularPaymentBSD : BenefitFixedHeightBSD() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bsd_create_regular_payment, null)
    }

}