package uz.magnumactive.benefit.ui.marketplace.favourite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_favourites.*
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.ui.base.BaseFragment
import uz.magnumactive.benefit.ui.marketplace.MarketActivity
import uz.magnumactive.benefit.ui.marketplace.dialogs.MarketProductDetailsBSD
import uz.magnumactive.benefit.ui.viewgroups.MarketFavouriteItem


class FavouritesFragment : BaseFragment(R.layout.fragment_favourites) {

    private val adapter = GroupAdapter<GroupieViewHolder>()
    val viewModel: FavouriteViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getFavourites()

        attachListeners()
        setupRVObservers(viewModel.favouritesResult, rvFavourites, adapter, createItem = {
            MarketFavouriteItem(it,
                onClick = {
                    val dialog = MarketProductDetailsBSD(it.itemInfo!!)
                    dialog.show(childFragmentManager, "")
                },
                onAddToCart = {

                })
        })
    }

    private fun attachListeners() {

    }


    override fun onResume() {
        super.onResume()
        (activity as MarketActivity).setTitle(getString(R.string.favourites))
    }
}