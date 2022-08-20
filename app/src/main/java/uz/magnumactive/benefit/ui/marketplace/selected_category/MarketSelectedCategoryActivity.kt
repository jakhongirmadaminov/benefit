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
import uz.magnumactive.benefit.remote.models.MarketPlaceCategoryObj
import uz.magnumactive.benefit.remote.models.MarketProductDTO
import uz.magnumactive.benefit.ui.base.BaseActionbarActivity
import uz.magnumactive.benefit.ui.marketplace.dialogs.MarketFilterBSD
import uz.magnumactive.benefit.ui.viewgroups.MarketGridProductItem
import uz.magnumactive.benefit.ui.viewgroups.MarketSubCategoryTagItem
import uz.magnumactive.benefit.ui.viewgroups.ProductListEmptyItem
import uz.magnumactive.benefit.util.RequestState


class MarketSelectedCategoryActivity : BaseActionbarActivity() {

    lateinit var selectedCatg: MarketPlaceCategoryObj
    val categoryProductsAdapter = GroupAdapter<GroupieViewHolder>()
    val categoryTagsAdapter = GroupAdapter<GroupieViewHolder>()
    private val viewModel: MarketSelectedCategoryViewModel by viewModels()
    var filterSelection = MarketFilterBSD.FilterEnum.SALE

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_market_selected_category)
        setSupportActionBar(tool_bar)
        super.onCreate(savedInstanceState)
//        tvTitle.text = intent.
        selectedCatg = intent.getParcelableExtra(SELECTED_CATEGORY)!!

        viewModel.getProductsForCategory(selectedCatg.id!!)
        viewModel.getSubcategoriesFor(selectedCatg.id!!)
        setupView()
        attachListeners()
        subscribeObservers()
    }

    private fun attachListeners() {
        ivFilter.setOnClickListener {
            val filterBsd = MarketFilterBSD(
                filterSelection,
                object : MarketFilterBSD.IOnMarketFilterItemSelected {
                    override fun onItemSelected(item: MarketFilterBSD.FilterEnum) {
                        filterSelection = item
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
                is RequestState.Error -> {}
                RequestState.Loading -> {}
                is RequestState.Success -> {
                    loadProducts(resp.value)
                }
            }
        }

        viewModel.subCategories.observe(this) {
            val resp = it ?: return@observe
            when (resp) {
                is RequestState.Error -> {}
                RequestState.Loading -> {}
                is RequestState.Success -> {
                    loadSubCategories(resp.value)
                }
            }
        }
    }

    private fun loadSubCategories(value: MarketAllSubCategoryDTO) {
        categoryTagsAdapter.clear()
        value.subCategory?.forEachIndexed { index, item ->
            val tagItem = MarketSubCategoryTagItem(item) { tagItem, subCategory ->
                selectTag(tagItem, subCategory)
            }
            tagItem.selected = index == 0
            categoryTagsAdapter.add(tagItem)
        }
        categoryTagsAdapter.notifyDataSetChanged()


    }


    private fun selectTag(cardItem: MarketSubCategoryTagItem, subCategory: MarketCategoryDTO) {
        for (i in 0 until categoryTagsAdapter.itemCount) {
            (categoryTagsAdapter.getItem(i) as MarketSubCategoryTagItem).selected = false
        }
        cardItem.selected = true
        categoryTagsAdapter.notifyDataSetChanged()
        viewModel.getProductsForSubCategory(selectedCatg.id!!, subCategory.id!!)
    }

    private fun loadProducts(value: List<MarketProductDTO>) {
        categoryProductsAdapter.clear()
        if (value.isNotEmpty()) {
            rvProducts.layoutManager = GridLayoutManager(this, 2, VERTICAL, false)
            value.forEach {
                categoryProductsAdapter.add(MarketGridProductItem(it) {
                })
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