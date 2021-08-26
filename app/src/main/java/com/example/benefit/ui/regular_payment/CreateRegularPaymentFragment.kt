package com.example.benefit.ui.regular_payment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.benefit.R
import com.example.benefit.remote.models.RegularPaymentDTO
import com.example.benefit.ui.base.BaseFragment
import com.example.benefit.ui.viewgroups.ItemPayment
import com.example.benefit.util.SizeUtils
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_create_regular_payment.*
import kotlinx.android.synthetic.main.fragment_create_regular_payment.clParent
import javax.inject.Inject


/**
 * Created by jahon on 03-Sep-20
 */

class CreateRegularPaymentFragment @Inject constructor() :
    BaseFragment(R.layout.fragment_create_regular_payment) {

//    val args by navArgs<TransactionFragmentArgs>()
//    val productId = args.productId

    private val adapter = GroupAdapter<GroupieViewHolder>()

    //    lateinit var transactionDTO: TransactionDTO
    private val viewModel: CreateRegularPaymentViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupViews()

        attachListeners()
        subscribeObservers()
    }


    private fun attachListeners() {
        ivMore.setOnClickListener {
            findNavController().navigate(R.id.action_createRegularPaymentFragment_to_createRegPaymentHowItWorksFragment)
        }

    }

    private fun setupViews() {
        clParent.layoutParams = clParent.layoutParams.apply {
            height = SizeUtils.getScreenHeight(requireActivity()) - SizeUtils.getActionBarHeight(
                requireActivity()
            )

        }

        rvPayments.adapter = adapter

        val data = listOf(
            RegularPaymentDTO("Телефон"),
            RegularPaymentDTO("Интернет"),
            RegularPaymentDTO("Электр.."),
            RegularPaymentDTO("Электр..")
        )
        loadData(data)

    }

    private fun loadData(data: List<RegularPaymentDTO>) {
        adapter.clear()

        data.forEach {
            adapter.add(ItemPayment(it) {
                findNavController().navigate(
                    CreateRegularPaymentFragmentDirections.actionCreateRegularPaymentFragmentToCreateRegPaymentEndFragment(
                        it
                    )
                )
            })
        }


    }

    private fun subscribeObservers() {


    }


}
