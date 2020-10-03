package com.example.benefit.ui.regular_payment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.benefit.R
import com.example.benefit.remote.models.RegularPaymentDTO
import com.example.benefit.ui.transactions_history.transaction_bsd.TransactionBSD
import com.example.benefit.util.MyBSDialog
import com.example.benefit.util.MyNestedScrollBSDialog
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RegularPaymentBSD : MyBSDialog() {


    companion object{
        const val ARG_REGULAR_PAYMENT_DTO="REGULAR_PAYMENT_DTO"
    }

    private val viewModel: RegularPaymentViewModel by viewModels()
    lateinit var regularPaymentDTO: RegularPaymentDTO

    override fun onAttach(context: Context) {
        super.onAttach(context)

        regularPaymentDTO = requireArguments().getParcelable(ARG_REGULAR_PAYMENT_DTO)!!

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bsd_regular_payment, null)

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (childFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).findNavController()
            .setGraph(R.navigation.regular_payment_nav_graph, Bundle().apply {
                putParcelable(ARG_REGULAR_PAYMENT_DTO, regularPaymentDTO)
            })

    }

}