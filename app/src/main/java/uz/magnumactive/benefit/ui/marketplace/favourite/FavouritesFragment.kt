package uz.magnumactive.benefit.ui.marketplace.favourite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_favourites.*
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.ui.base.BaseFragment
import uz.magnumactive.benefit.ui.marketplace.MarketActivity
import uz.magnumactive.benefit.ui.marketplace.dialogs.MarketProductDetailsBSD
import uz.magnumactive.benefit.ui.viewgroups.MarketFavouriteItem
import uz.magnumactive.benefit.util.RequestState


class FavouritesFragment : BaseFragment(R.layout.fragment_favourites) {

    private val adapter = GroupAdapter<GroupieViewHolder>()
    val viewModel: FavouriteViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getFavourites()

        attachListeners()
        subscribeObservers()
        setupRVObservers(viewModel.favouritesResult, rvFavourites, adapter, createItem = {
            MarketFavouriteItem(it,
                onClick = {
                    val dialog = MarketProductDetailsBSD(it.itemInfo!!)
                    dialog.show(childFragmentManager, "")
                },
                onAddToCart = {
                    viewModel.addToCart(it.itemInfo?.id!!, 1)
                },
                removeFromFavourites = {
                    viewModel.removeFromFav(it.itemInfo?.id!!)
                })
        })
    }

    private fun subscribeObservers() {

        viewModel.addToCartResp.observe(viewLifecycleOwner) {
            val resp = it ?: return@observe
            when (resp) {
                is RequestState.Error -> {
                    Snackbar.make(parent, R.string.error, Snackbar.LENGTH_SHORT).show()
                }
                RequestState.Loading -> {}
                is RequestState.Success -> {
                    (requireActivity() as MarketActivity).viewModel.getMyCart()
                    Snackbar.make(parent, R.string.added_to_cart, Snackbar.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.removeFromFavResp.observe(viewLifecycleOwner) {
            val resp = it ?: return@observe
            when (resp) {
                is RequestState.Error -> {
                    Snackbar.make(parent, R.string.error, Snackbar.LENGTH_SHORT).show()
                }
                RequestState.Loading -> {}
                is RequestState.Success -> {
                    viewModel.getFavourites()
                }
            }
        }
    }

    private fun attachListeners() {

    }


    override fun onResume() {
        super.onResume()
        (activity as MarketActivity).setTitle(getString(R.string.favourites))
    }
}