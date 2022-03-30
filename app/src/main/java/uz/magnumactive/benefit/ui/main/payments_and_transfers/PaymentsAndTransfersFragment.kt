package uz.magnumactive.benefit.ui.main.payments_and_transfers

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.remote.models.AutoPaymentDTO
import uz.magnumactive.benefit.ui.base.BaseFragment
import uz.magnumactive.benefit.ui.gap.GapActivity
import uz.magnumactive.benefit.ui.main.fill_card.FillCardBSD
import uz.magnumactive.benefit.ui.main.fill_card.FillCardFragment
import uz.magnumactive.benefit.ui.main.home.DialogPleaseAddCard
import uz.magnumactive.benefit.ui.main.home.KEY_ADD_CARD
import uz.magnumactive.benefit.ui.main.home.bsd_add_card.AddCardBSD
import uz.magnumactive.benefit.ui.main.transfer_to_card.TransferToCardBSD
import uz.magnumactive.benefit.ui.payments.PaymentsBSD
import uz.magnumactive.benefit.ui.regular_payment.CreateRegularPaymentBSD
import uz.magnumactive.benefit.ui.regular_payment.RegularPaymentBSD
import uz.magnumactive.benefit.ui.regular_payment.RegularPaymentBSD.Companion.RESULT_REGULAR_PAYMENT_DELETE
import uz.magnumactive.benefit.ui.viewgroups.ItemRegularPayment
import uz.magnumactive.benefit.util.RequestState
import uz.magnumactive.benefit.util.setLoadingSpinner
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