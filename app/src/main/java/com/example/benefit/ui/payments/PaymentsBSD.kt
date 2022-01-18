package com.example.benefit.ui.payments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.benefit.R
import com.example.benefit.ui.main.BenefitBSD
import com.example.benefit.ui.main.BenefitFixedHeightBSD
import com.example.benefit.ui.transactions_history.transaction_bsd.TransactionBSD.Companion.ARG_TRANSACTION_DTO


class PaymentsBSD : BenefitFixedHeightBSD() {


    private val viewModel: PaymentsViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bsd_payments, null)

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (childFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).findNavController()
            .setGraph(R.navigation.payments_nav_graph, Bundle().apply {
//               if () putParcelable(ARG_TRANSACTION_DTO, transactionDTO)
            })

    }

}