package uz.magnumactive.benefit.ui.marketplace.catalog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_catalog.*
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.remote.models.MarketPlaceCategoryObj
import uz.magnumactive.benefit.remote.models.MarketProductDTO
import uz.magnumactive.benefit.ui.base.BaseFragment
import uz.magnumactive.benefit.ui.marketplace.MarketActivity
import uz.magnumactive.benefit.ui.viewgroups.MarketCatalogItem
import uz.magnumactive.benefit.ui.viewgroups.MarketSaleItem
import uz.magnumactive.benefit.util.RequestState


class CatalogFragment : BaseFragment(R.layout.fragment_catalog) {

    private val categoriesAdapter = GroupAdapter<GroupieViewHolder>()
    private val saleItemsAdapter = GroupAdapter<GroupieViewHolder>()
    val viewModel: CatalogViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCatalog()
        viewModel.getSaleItems()

        setupViews()
        attachListeners()
        subscribeObservers()
    }

    private fun subscribeObservers() {

        viewModel.catalogResult.observe(viewLifecycleOwner) {
            when (it) {
                is RequestState.Error -> {}
                RequestState.Loading -> {}
                is RequestState.Success -> {
                    loadCategories(it.value)
                }
            }
        }


        viewModel.saleItemsResult.observe(viewLifecycleOwner) {
            when (it) {
                is RequestState.Error -> {}
                RequestState.Loading -> {}
                is RequestState.Success -> {
                    loadSaleItems(it.value)
                }
            }
        }

    }

    private fun loadSaleItems(result: List<MarketProductDTO>) {
        saleItemsAdapter.clear()
        result.forEach {
            saleItemsAdapter.add(MarketSaleItem(it))
        }

    }

    private fun loadCategories(result: List<MarketPlaceCategoryObj>) {
        categoriesAdapter.clear()
        result.forEach {
            categoriesAdapter.add(MarketCatalogItem(it))
        }
    }

    private fun setupViews() {

        rvCatalog.adapter = categoriesAdapter
        rvMarket.adapter = saleItemsAdapter

    }


    private fun attachListeners() {

    }

    override fun onResume() {
        super.onResume()
        (activity as MarketActivity).setTitle(getString(R.string.market))
    }
}