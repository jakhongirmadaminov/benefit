package com.example.benefit.ui.regular_payment

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.benefit.R
import com.example.benefit.remote.models.PaynetCategory
import com.example.benefit.ui.base.BaseFragment
import com.example.benefit.ui.viewgroups.ItemPaynet
import com.example.benefit.util.SizeUtils
import com.example.benefit.util.setLoadingSpinner
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_create_regular_payment.*
import javax.inject.Inject

/**
 * Created by jahon on 03-Sep-20
 */

class CreateRegularPaymentFragment @Inject constructor() :
    BaseFragment(R.layout.fragment_create_regular_payment) {

    private val adapter = GroupAdapter<GroupieViewHolder>()
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

        edtSearch.doOnTextChanged { text, _, _, _ ->
            viewModel.paynetCatgResp.value?.let { paynetCategories ->
                if (text!!.isBlank()) {
                    adapter.clear()
                    loadData(paynetCategories)
                } else {
                    val filtered = paynetCategories.filter {
                        it.titleRu?.lowercase()?.contains(text.toString().lowercase()) == true ||
                                it.titleUz?.lowercase()
                                    ?.contains(text.toString().lowercase()) == true
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
                findNavController().navigate(
                    CreateRegularPaymentFragmentDirections.actionCreateRegularPaymentFragmentToSelectMerchantFragment2(
                        it,
                    )
                )
            })
        }
    }

    private fun subscribeObservers() {

        viewModel.paynetCatgResp.observe(viewLifecycleOwner) {
            loadData(it)
        }

        viewModel.isLoadingPaynetCategories.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) adapter.setLoadingSpinner() else adapter.clear()
        }
    }


}
