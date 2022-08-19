package uz.magnumactive.benefit.ui.marketplace.selected_category

import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_gap_chart.*
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.remote.models.MarketPlaceCategoryObj
import uz.magnumactive.benefit.ui.base.BaseActionbarActivity
import uz.magnumactive.benefit.util.RequestState


class MarketSelectedCategoryActivity : BaseActionbarActivity() {

    lateinit var selectedCatg: MarketPlaceCategoryObj
    val categoryProductsAdapter = GroupAdapter<GroupieViewHolder>()
    val categoryTagsAdapter = GroupAdapter<GroupieViewHolder>()
    private val viewModel: MarketSelectedCategoryViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_market_selected_category)
        setSupportActionBar(tool_bar)
        super.onCreate(savedInstanceState)
//        tvTitle.text = intent.
        selectedCatg = intent.getParcelableExtra(SELECTED_CATEGORY)!!

        viewModel.getProductsForCategory(selectedCatg.id!!)

        subscribeObservers()
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
    }

    private fun loadProducts(value: List<MarketPlaceCategoryObj>) {
        categoryProductsAdapter.clear()

    }

    companion object {
        const val SELECTED_CATEGORY = "SELECTED_CATEGORY"
    }

}