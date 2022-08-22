package uz.magnumactive.benefit.ui.marketplace.selected_category

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_gap_chart.tool_bar
import kotlinx.android.synthetic.main.activity_market_selected_category.*
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.remote.models.MarketAllSubCategoryDTO
import uz.magnumactive.benefit.remote.models.MarketCategoryDTO
import uz.magnumactive.benefit.remote.models.MarketProductDTO
import uz.magnumactive.benefit.remote.models.TypeName
import uz.magnumactive.benefit.ui.base.BaseActionbarActivity
import uz.magnumactive.benefit.ui.marketplace.dialogs.MarketFilterBSD
import uz.magnumactive.benefit.ui.marketplace.dialogs.MarketProductDetailsBSD
import uz.magnumactive.benefit.ui.viewgroups.ItemLoading
import uz.magnumactive.benefit.ui.viewgroups.MarketGridProductItem
import uz.magnumactive.benefit.ui.viewgroups.MarketSubCategoryTagItem
import uz.magnumactive.benefit.ui.viewgroups.ProductListEmptyItem
import uz.magnumactive.benefit.util.RequestState


class MarketSelectedCategoryActivity : BaseActionbarActivity() {

    private val categoryProductsAdapter = GroupAdapter<GroupieViewHolder>()
    private val categoryTagsAdapter = GroupAdapter<GroupieViewHolder>()
    private val viewModel: MarketSelectedCategoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_market_selected_category)
        setSupportActionBar(tool_bar)
        super.onCreate(savedInstanceState)
        viewModel.selectedCatg = intent.getParcelableExtra(SELECTED_CATEGORY)!!
        tvTitle.text = viewModel.selectedCatg.title?.getLocalized()

        viewModel.getProductsForCategory()
        viewModel.getSubcategoriesFor()
        setupView()
        attachListeners()
        subscribeObservers()
    }

    private fun attachListeners() {
        ivFilter.setOnClickListener {
            val filterBsd = MarketFilterBSD(
                viewModel.filterSelection,
                object : MarketFilterBSD.IOnMarketFilterItemSelected {
                    override fun onItemSelected(item: MarketFilterBSD.FilterEnum) {
                        viewModel.filterSelection = item
                    }
                })
            filterBsd.show(supportFragmentManager, "")
        }
    }

    private fun setupView() {

        rvProducts.adapter = categoryProductsAdapter
        rvTags.adapter = categoryTagsAdapter
    }

    private fun subscribeObservers() {

        viewModel.categoryProductsResult.observe(this) {
            val resp = it ?: return@observe
            when (resp) {
                is RequestState.Error -> {
                    categoryProductsAdapter.clear()
                }
                RequestState.Loading -> {
                    categoryProductsAdapter.clear()
                    rvProducts.layoutManager = LinearLayoutManager(this)
                    categoryProductsAdapter.add(ItemLoading())
                }
                is RequestState.Success -> {
                    loadProducts(resp.value)
                }
            }
        }

        viewModel.subCategories.observe(this) {
            val resp = it ?: return@observe
            when (resp) {
                is RequestState.Error -> {
                }
                RequestState.Loading -> {
                }
                is RequestState.Success -> {
                    loadSubCategories(resp.value)
                }
            }
        }
    }

    private fun loadSubCategories(value: MarketAllSubCategoryDTO) {
        categoryTagsAdapter.clear()
        value.subCategory?.let { subCategory ->
            if (subCategory.isNotEmpty()) {
                val tagItem = MarketSubCategoryTagItem(
                    MarketCategoryDTO(
                        title = TypeName(
                            getString(R.string.all),
                            getString(R.string.all),
                            getString(R.string.all)
                        )
                    )
                ) { tagItem, _ ->
                    viewModel.selectedSubCatg = null
                    selectTag(tagItem)
                }
                tagItem.selected = true
                categoryTagsAdapter.add(tagItem)
            }
            subCategory.forEach { item ->
                val tagItem = MarketSubCategoryTagItem(item) { tagItem, subCategory ->
                    selectTag(tagItem)
                    viewModel.selectedSubCatg = subCategory
                }
                categoryTagsAdapter.add(tagItem)
            }
        }
        categoryTagsAdapter.notifyDataSetChanged()
    }

    private fun selectTag(cardItem: MarketSubCategoryTagItem) {
        for (i in 0 until categoryTagsAdapter.itemCount) {
            (categoryTagsAdapter.getItem(i) as MarketSubCategoryTagItem).selected = false
        }
        cardItem.selected = true
        categoryTagsAdapter.notifyDataSetChanged()
    }

    private fun loadProducts(value: List<MarketProductDTO>) {
        categoryProductsAdapter.clear()
        if (value.isNotEmpty()) {
            rvProducts.layoutManager = GridLayoutManager(this, 2, VERTICAL, false)
            value.forEach {
                categoryProductsAdapter.add(
                    MarketGridProductItem(it,
                        onClick = {
                            val detailsBSD = MarketProductDetailsBSD(it)
                            detailsBSD.show(supportFragmentManager, "")
                        },
                        onAddToCart = {

                        })
                )
            }
        } else {
            rvProducts.layoutManager = LinearLayoutManager(this)
            categoryProductsAdapter.add(ProductListEmptyItem())
        }
        categoryProductsAdapter.notifyDataSetChanged()
    }

    companion object {
        const val SELECTED_CATEGORY = "SELECTED_CATEGORY"
    }

}