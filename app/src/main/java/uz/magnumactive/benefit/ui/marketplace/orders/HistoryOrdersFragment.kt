package uz.magnumactive.benefit.ui.marketplace.orders

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_payments_and_transfers.*
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.ui.base.BaseFragment


class HistoryOrdersFragment : BaseFragment(R.layout.fragment_history_orders) {

//    private val adapter = GroupAdapter<GroupieViewHolder>()
//    val viewModel: PaymentsAndTransfersViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewModel.getMyCards()
//        viewModel.getMyAutoPayments()

        setupViews()
        attachListeners()
        subscribeObservers()
    }

    private fun subscribeObservers() {

    }

    private fun setupViews() {

    }


    private fun attachListeners() {

    }
}