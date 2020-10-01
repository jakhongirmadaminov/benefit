package com.example.benefit.ui.create_regular_payment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.benefit.R
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


/**
 * Created by jahon on 03-Sep-20
 */
@AndroidEntryPoint
class CreateRegularPaymentFragment @Inject constructor() : Fragment(R.layout.fragment_create_regular_payment) {

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


    }

    private fun setupViews() {



    }

    private fun subscribeObservers() {


    }


}
