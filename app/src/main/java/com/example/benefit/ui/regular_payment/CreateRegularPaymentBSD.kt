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

const val ARG_PAYMENT_DTO = "paymentDTO"

class CreateRegularPaymentBSD : BenefitFixedHeightBSD() {


    private val viewModel: CreateRegularPaymentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bsd_create_regular_payment, null)

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        (childFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).findNavController()
//            .setGraph(R.navigation.create_regular_payment_nav_graph, Bundle().apply {
//                putParcelable(
//                    ARG_PAYMENT_DTO,
//                    requireArguments()[ARG_PAYMENT_DTO] as AutoPaymentDTO
//                )
//            })

    }

}