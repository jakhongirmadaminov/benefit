package com.example.benefit.ui.transactions_history.transaction_bsd

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.benefit.R
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_transaction_more_info.*
import javax.inject.Inject


/**
 * Created by jahon on 03-Sep-20
 */

class TransactionMoreInfoFragment @Inject constructor() :
    Fragment(R.layout.fragment_transaction_more_info) {

    private val adapter = GroupAdapter<GroupieViewHolder>()
    val args: TransactionMoreInfoFragmentArgs by navArgs()
    private val viewModel: TransactionViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupViews()

        attachListeners()
        subscribeObservers()
    }

    private fun setupViews() {
        tvBrandName.text = args.transactionDTO.merchantName

    }

    private fun subscribeObservers() {


    }

    private fun attachListeners() {
        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }


}
