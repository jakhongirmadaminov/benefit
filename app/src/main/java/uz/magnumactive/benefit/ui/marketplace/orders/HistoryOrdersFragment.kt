package uz.magnumactive.benefit.ui.marketplace.orders

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_history_orders.*
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.ui.base.BaseFragment
import uz.magnumactive.benefit.ui.viewgroups.ItemHistoryOrder


class HistoryOrdersFragment : BaseFragment(R.layout.fragment_history_orders) {

    private val adapter = GroupAdapter<GroupieViewHolder>()
    val viewModel: HistoryOrdersViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeObservers()
    }

    override fun onResume() {
        super.onResume()
        viewModel.getHistoryOrders()
    }

    fun subscribeObservers() {
        setupRVObservers(
            viewModel.historyOrders,
            rvHistoryOrders,
            adapter,
            LinearLayoutManager(context)
        ) {
            ItemHistoryOrder(it)
        }
    }

}