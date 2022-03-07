package com.example.benefit.ui.main.payments_and_transfers

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.benefit.R
import com.example.benefit.remote.models.AutoPaymentDTO
import com.example.benefit.ui.base.BaseFragment
import com.example.benefit.ui.gap.GapActivity
import com.example.benefit.ui.main.fill_card.FillCardBSD
import com.example.benefit.ui.main.fill_card.FillCardFragment
import com.example.benefit.ui.main.home.DialogPleaseAddCard
import com.example.benefit.ui.main.home.KEY_ADD_CARD
import com.example.benefit.ui.main.home.bsd_add_card.AddCardBSD
import com.example.benefit.ui.main.transfer_to_card.TransferToCardBSD
import com.example.benefit.ui.payments.PaymentsBSD
import com.example.benefit.ui.regular_payment.CreateRegularPaymentBSD
import com.example.benefit.ui.regular_payment.RegularPaymentBSD
import com.example.benefit.ui.regular_payment.RegularPaymentBSD.Companion.RESULT_REGULAR_PAYMENT_DELETE
import com.example.benefit.ui.viewgroups.ItemRegularPayment
import com.example.benefit.util.RequestState
import com.example.benefit.util.setLoadingSpinner
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_payments_and_transfers.*


class PaymentsAndTransfersFragment : BaseFragment(R.layout.fragment_payments_and_transfers) {

    private val adapter = GroupAdapter<GroupieViewHolder>()
    val viewModel: PaymentsAndTransfersViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getMyCards()
        viewModel.getMyAutoPayments()

        setupViews()
        attachListeners()
        subscribeObservers()
    }

    private fun subscribeObservers() {
        viewModel.autoPaymentsReqState.observe(viewLifecycleOwner) { reqState ->

            when (reqState) {
                is RequestState.Error -> {
                    adapter.clear()
                }
                RequestState.Loading -> adapter.setLoadingSpinner()
                is RequestState.Success -> {
                    loadData(reqState.value)
                }
            }
        }
    }

    private fun setupViews() {
        rvRegularPayments.adapter = adapter
        adapter.clear()
    }

    private fun loadData(data: List<AutoPaymentDTO>) {
        adapter.clear()
        data.forEach {
            adapter.add(ItemRegularPayment(it, {
                val dialog = RegularPaymentBSD()
                childFragmentManager.setFragmentResultListener(
                    RESULT_REGULAR_PAYMENT_DELETE,
                    viewLifecycleOwner
                ) { requestKey, result ->
                    viewModel.getMyCards()
                    viewModel.getMyAutoPayments()
                }
                dialog.arguments = Bundle().apply {
                    putParcelable(RegularPaymentBSD.ARG_REGULAR_PAYMENT_DTO, it)
                }
                dialog.show(childFragmentManager, "")
            }) { })
        }
        if (adapter.itemCount < 8) {
            adapter.add(ItemRegularPayment(null, {}) {
                val dialog = CreateRegularPaymentBSD()
                dialog.arguments = Bundle().apply {

                }
                dialog.show(childFragmentManager, "")

            })
        }
    }

    private fun attachListeners() {
        clPay.setOnClickListener {
            if (viewModel.cardsResp.value.isNullOrEmpty()) {
                val dialog = DialogPleaseAddCard()
                childFragmentManager.setFragmentResultListener(
                    KEY_ADD_CARD,
                    viewLifecycleOwner
                ) { requestKey, result ->
                    AddCardBSD().show(childFragmentManager, "")
                }
                dialog.show(childFragmentManager, "")
            } else {
                PaymentsBSD().show(childFragmentManager, "")
            }
        }

        clMakeDepo.setOnClickListener {
            if (viewModel.cardsResp.value.isNullOrEmpty()) {
                val dialog = DialogPleaseAddCard()
                childFragmentManager.setFragmentResultListener(
                    KEY_ADD_CARD,
                    viewLifecycleOwner
                ) { requestKey, result ->
                    AddCardBSD().show(childFragmentManager, "")
                }
                dialog.show(childFragmentManager, "")
            } else {
                val dialog = FillCardBSD()
                dialog.arguments = Bundle().apply {
                    putParcelable(FillCardFragment.ARG_CARD, viewModel.cardsResp.value!![0])
                    putParcelableArrayList(
                        FillCardFragment.ARG_CARDS,
                        ArrayList(viewModel.cardsResp.value!!)
                    )
                }
                dialog.show(requireActivity().supportFragmentManager, "")
            }
        }

        clTransferToCard.setOnClickListener {
            if (viewModel.cardsResp.value.isNullOrEmpty()) {
                val dialog = DialogPleaseAddCard()
                childFragmentManager.setFragmentResultListener(
                    KEY_ADD_CARD,
                    viewLifecycleOwner
                ) { requestKey, result ->
                    AddCardBSD().show(childFragmentManager, "")
                }
                dialog.show(childFragmentManager, "")
            } else {
                TransferToCardBSD().show(childFragmentManager, "")
            }
        }

        clGap.setOnClickListener {
            startActivity(Intent(requireActivity(), GapActivity::class.java))
        }
    }
}