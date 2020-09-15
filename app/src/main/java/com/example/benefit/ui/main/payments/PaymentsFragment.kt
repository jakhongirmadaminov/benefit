package com.example.benefit.ui.main.payments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.benefit.R
import com.example.benefit.ui.main.fill_card.FillCardBSD
import com.example.benefit.ui.main.transfer_to_card.TransferToCardBSD
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_payments.*

@AndroidEntryPoint
class PaymentsFragment : Fragment(R.layout.fragment_payments) {

    private lateinit var paymentsViewModel: PaymentsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        attachListeners()
    }

    private fun attachListeners() {

        clMakeDepo.setOnClickListener {
            val bsd = FillCardBSD()
//            bsd.navigateToCardTransfer()
            bsd.show(childFragmentManager, "")
        }

        clTransferToCard.setOnClickListener {
            TransferToCardBSD().show(childFragmentManager, "")
        }
    }
}