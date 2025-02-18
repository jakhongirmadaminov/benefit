package uz.magnumactive.benefit.ui.marketplace.catalog

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_catalog.*
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.remote.models.MarketPlaceCategoryObj
import uz.magnumactive.benefit.remote.models.MarketProductDTO
import uz.magnumactive.benefit.ui.base.BaseFragment
import uz.magnumactive.benefit.ui.marketplace.EXTRA_SELECTED_MARKET_CATEGORY
import uz.magnumactive.benefit.ui.marketplace.MarketActivity
import uz.magnumactive.benefit.ui.marketplace.dialogs.MarketProductDetailsBSD
import uz.magnumactive.benefit.ui.marketplace.search_result.SearchResultActivity
import uz.magnumactive.benefit.ui.marketplace.selected_category.MarketSelectedCategoryActivity
import uz.magnumactive.benefit.ui.marketplace.selected_category.MarketSelectedCategoryActivity.Companion.SELECTED_CATEGORY
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

        arguments?.getParcelable<MarketPlaceCategoryObj>(EXTRA_SELECTED_MARKET_CATEGORY)
            ?.let {
                startActivity(
                    Intent(
                        requireActivity(),
                        MarketSelectedCategoryActivity::class.java
                    ).apply { putExtra(SELECTED_CATEGORY, it) })
            }
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

        viewModel.searchResult.observe(viewLifecycleOwner) {
            when (it) {
                is RequestState.Error -> {
                    searchProgress.visibility = View.INVISIBLE
                }
                RequestState.Loading -> {
                    searchProgress.visibility = View.VISIBLE
                }
                is RequestState.Success -> {
                    searchProgress.visibility = View.INVISIBLE
                    startActivity(
                        Intent(
                            requireActivity(),
                            SearchResultActivity::class.java
                        ).apply {
                            putParcelableArrayListExtra(
                                SearchResultActivity.SEARCH_RESULT,
                                ArrayList(it.value.result)
                            )
                        })
                }
            }
        }

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

    }

    private fun loadSaleItems(result: List<MarketProductDTO>) {
        saleItemsAdapter.clear()
        llMarketPlace.isVisible = result.isNotEmpty()
        rvMarket.isVisible = result.isNotEmpty()

        result.forEach {
            saleItemsAdapter.add(MarketSaleItem(it,
                onClick = {
                    val dialog = MarketProductDetailsBSD(it)
                    dialog.show(childFragmentManager, "")
                },
                onAddToCart = { viewModel.addToCart(it.id!!, 1) }
            ))
        }
    }

    private fun loadCategories(result: List<MarketPlaceCategoryObj>) {
        categoriesAdapter.clear()
        result.forEach {
            categoriesAdapter.add(MarketCatalogItem(it) {
                requireContext().startActivity(
                    Intent(requireActivity(), MarketSelectedCategoryActivity::class.java).apply {
                        putExtra(SELECTED_CATEGORY, it)
                    })
            })
        }
    }

    private fun setupViews() {

        rvCatalog.adapter = categoriesAdapter
        rvMarket.adapter = saleItemsAdapter

    }


    private fun attachListeners() {
        searchView.doOnTextChanged { text, start, before, count ->
            ivClear.isVisible = !text.isNullOrEmpty()
        }

        searchView.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.search(searchView.text.toString())
                return@OnEditorActionListener true
            }
            false
        })

        ivClear.setOnClickListener {
            searchView.text.clear()
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as MarketActivity).setTitle(getString(R.string.market))
    }
}