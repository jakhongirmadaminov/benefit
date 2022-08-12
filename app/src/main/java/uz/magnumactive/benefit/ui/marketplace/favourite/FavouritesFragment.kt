package uz.magnumactive.benefit.ui.marketplace.favourite

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_payments_and_transfers.*
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.ui.base.BaseFragment
import uz.magnumactive.benefit.ui.marketplace.MarketActivity


class FavouritesFragment : BaseFragment(R.layout.fragment_favourites) {

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



    override fun onResume() {
        super.onResume()
        (activity as MarketActivity).setTitle(getString(R.string.favourites))
    }
}