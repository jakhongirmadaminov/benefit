package com.example.benefit.ui.main.transfer_to_card

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.benefit.R
import com.example.benefit.ui.main.BenefitBSD

import dagger.hilt.android.AndroidEntryPoint



class TransferToCardBSD : BenefitBSD() {

    private val viewModel: TransferToCardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bsd_transfer_to_card, null)

        return view
    }


    fun navigateToCardTransfer() {
//        nav_host_fragment.findNavController()
//            .navigate(R.id.action_fillCardFragment_to_cardMakeDepositFromAnyCardFragment)
    }

}