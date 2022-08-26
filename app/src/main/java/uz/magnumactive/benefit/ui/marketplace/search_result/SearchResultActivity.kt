package uz.magnumactive.benefit.ui.marketplace.search_result

import android.os.Bundle
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_gap_chart.tool_bar
import kotlinx.android.synthetic.main.activity_market_search_result.*
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.remote.models.MarketProductDTO
import uz.magnumactive.benefit.ui.base.BaseActionbarActivity
import uz.magnumactive.benefit.ui.viewgroups.MarketGridProductItem


class SearchResultActivity : BaseActionbarActivity() {

    private lateinit var searchResult: ArrayList<MarketProductDTO>
    private val productsAdapter = GroupAdapter<GroupieViewHolder>()
//    private val viewModel: MarketSearchResultViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_market_search_result)
        setSupportActionBar(tool_bar)
        super.onCreate(savedInstanceState)
        searchResult = intent.getParcelableArrayListExtra(SEARCH_RESULT)!!
        setupView(searchResult)
        attachListeners()
    }

    private fun attachListeners() {

    }

    private fun setupView(searchResult: ArrayList<MarketProductDTO>) {

        rvSearchResults.adapter = productsAdapter

        searchResult.forEach {
            productsAdapter.add(MarketGridProductItem(it, onClick = {

            }, onAddToCart = {

            }))
        }
        productsAdapter.notifyDataSetChanged()

    }


    companion object {
        const val SEARCH_RESULT = "SEARCH_RESULT"
    }
}