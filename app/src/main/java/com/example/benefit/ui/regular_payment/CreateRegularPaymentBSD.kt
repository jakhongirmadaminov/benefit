package com.example.benefit.ui.regular_payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.benefit.R
import com.example.benefit.util.MyNestedScrollBSDialog
import dagger.hilt.android.AndroidEntryPoint



class CreateRegularPaymentBSD : MyNestedScrollBSDialog() {


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
//        (childFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment). findNavController()
//            .setGraph(R.navigation.transaction_nav_graph, Bundle().apply {
//                putParcelable(ARG_TRANSACTION_DTO, transactionDTO)
//            })

    }

}