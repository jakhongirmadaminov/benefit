package uz.magnumactive.benefit.ui.marketplace.orders

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_active_orders.*
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.ui.base.BaseFragment
import uz.magnumactive.benefit.ui.viewgroups.ItemActiveOrder


class ActiveOrdersFragment : BaseFragment(R.layout.fragment_active_orders) {

    private val adapter = GroupAdapter<GroupieViewHolder>()
    val viewModel: ActiveOrdersViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getActiveOrders()
        setupViews()
        attachListeners()
        subscribeObservers()
    }

     fun subscribeObservers() {
        setupRVObservers(
            viewModel.activeOrders,
            progress,
            rvActiveOrders,
            adapter,
        ) {
            ItemActiveOrder(it)
        }
    }

    private fun setupViews() {
        rvActiveOrders.adapter = adapter
    }

    private fun attachListeners() {

    }
}