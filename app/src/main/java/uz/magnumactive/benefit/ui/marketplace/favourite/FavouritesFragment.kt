package uz.magnumactive.benefit.ui.marketplace.favourite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_favourites.*
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.remote.models.MarketPlaceCategoryObj
import uz.magnumactive.benefit.remote.models.MarketProductDTO
import uz.magnumactive.benefit.ui.base.BaseFragment
import uz.magnumactive.benefit.ui.marketplace.MarketActivity
import uz.magnumactive.benefit.ui.viewgroups.MarketFavouriteItem
import uz.magnumactive.benefit.util.RequestState


class FavouritesFragment : BaseFragment(R.layout.fragment_favourites) {

    private val adapter = GroupAdapter<GroupieViewHolder>()
    val viewModel: FavouriteViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getFavourites()

        setupViews()
        attachListeners()
        subscribeObservers()
    }

    private fun subscribeObservers() {
        viewModel.favouritesResult.observe(viewLifecycleOwner) {
            when (it) {
                is RequestState.Error -> {}
                RequestState.Loading -> {}
                is RequestState.Success -> {
                    loadResult(it.value)
                }
            }
        }

    }

    private fun loadResult(items: List<MarketProductDTO>) {

        adapter.clear()
        items.forEach {
            adapter.add(MarketFavouriteItem(it))
        }

    }

    private fun setupViews() {
        rvFavourites.adapter = adapter

    }


    private fun attachListeners() {

    }


    override fun onResume() {
        super.onResume()
        (activity as MarketActivity).setTitle(getString(R.string.favourites))
    }
}