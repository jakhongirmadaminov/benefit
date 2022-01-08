package com.example.benefit.ui.payments

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import com.example.benefit.R
import com.example.benefit.remote.models.PaynetCategory
import com.example.benefit.ui.base.BaseFragment
import com.example.benefit.ui.viewgroups.ItemLoading
import com.example.benefit.ui.viewgroups.ItemPaynet
import com.example.benefit.util.SizeUtils
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_payments.*
import javax.inject.Inject


/**
 * Created by jahon on 03-Sep-20
 */

class PaymentsFragment @Inject constructor() : BaseFragment(R.layout.fragment_payments) {

//    val args by navArgs<TransactionFragmentArgs>()
//    val productId = args.productId

    private val adapter = GroupAdapter<GroupieViewHolder>()

    //    lateinit var transactionDTO: TransactionDTO
    private val viewModel: PaymentsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupViews()

        attachListeners()
        subscribeObservers()
    }


    private fun attachListeners() {

        edtSearch.doOnTextChanged { text, _, _, _ ->
            viewModel.paynetCatgResp.value?.let { paynetCategories ->
                if (text!!.isBlank()) {
                    adapter.clear()
                    loadData(paynetCategories)
                } else {
                    val filtered = paynetCategories.filter {
                        it.titleRu?.contains(text) == true
                                || it.titleUz?.contains(text) == true
                    }
                    if (filtered.isNotEmpty()) {
                        adapter.clear()
                        loadData(filtered)
                    }
                }
            }
        }
    }

    private fun setupViews() {
        clParent.layoutParams = clParent.layoutParams.apply {
            height = SizeUtils.getScreenHeight(requireActivity()) - SizeUtils.getActionBarHeight(
                requireActivity()
            )

        }

        rvPayments.adapter = adapter


    }

    private fun loadData(data: List<PaynetCategory>) {
        adapter.clear()

        data.forEach {
            adapter.add(ItemPaynet(it) {
//                findNavController().navigate(
//                    CreateRegularPaymentFragmentDirections.actionCreateRegularPaymentFragmentToCreateRegPaymentEndFragment(
//                        it
//                    )
//                )
            })
        }


    }

    private fun subscribeObservers() {

        viewModel.paynetCatgResp.observe(viewLifecycleOwner) {
            loadData(it)
        }

        viewModel.isLoadingPaynetCategories.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) addLoadingSpinner()
        }
    }

    private fun addLoadingSpinner() {
        adapter.clear()
        adapter.add(ItemLoading())
    }


}
