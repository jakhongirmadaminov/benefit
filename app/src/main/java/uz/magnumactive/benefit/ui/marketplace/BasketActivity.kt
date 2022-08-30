package uz.magnumactive.benefit.ui.marketplace

import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_basket.*
import kotlinx.android.synthetic.main.activity_gap_chart.tool_bar
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.remote.models.MyBasketResultDTO
import uz.magnumactive.benefit.ui.base.BaseActionbarActivity
import uz.magnumactive.benefit.ui.viewgroups.ItemCartEntry
import uz.magnumactive.benefit.ui.viewgroups.ItemProductListEmpty
import uz.magnumactive.benefit.util.RequestState

class BasketActivity : BaseActionbarActivity() {

    private val adapter = GroupAdapter<GroupieViewHolder>()
    val viewModel: BasketViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_basket)
        setSupportActionBar(tool_bar)
        super.onCreate(savedInstanceState)

        viewModel.getMyCart()
        attachListeners()
        setupView()
        subscribeObservers()
    }

    private fun attachListeners() {

        swipeRefresh.setOnRefreshListener {
            viewModel.getMyCart()
        }

        ivCleanBasket.setOnClickListener {
            viewModel.cleanBasket()
        }
    }

    private fun setupView() {
        rvCart.adapter = adapter

    }

//    var manipulatingItem: Pair<ItemCartEntry, Int>? = null

    fun subscribeObservers() {

        viewModel.cleanBasketResp.observe(this) {
            val resp = it ?: return@observe
            swipeRefresh.isRefreshing = resp is RequestState.Loading
            when (resp) {
                is RequestState.Error -> {
                    Snackbar.make(llParent, R.string.error, Snackbar.LENGTH_SHORT).show()
                }
                RequestState.Loading -> {
                }
                is RequestState.Success -> {
                    viewModel.getMyCart()
//                    loadData(resp.value)
                }
            }
        }

        viewModel.addToCartResp.observe(this) { requestState ->
            val resp = requestState ?: return@observe
            swipeRefresh.isRefreshing = resp is RequestState.Loading
            when (resp) {
                is RequestState.Error -> {
                    Snackbar.make(llParent, R.string.error, Snackbar.LENGTH_SHORT).show()
                }
                RequestState.Loading -> {
                }
                is RequestState.Success -> {
                    viewModel.getMyCart()
//                    loadData(resp.value)

//                    manipulatingItem?.let {
//                        it.first.obj.count = it.first.obj.count?.plus(it.second)
//                        adapter.notifyItemChanged(it.first.getPosition(it.first))
//                    }
                }
            }

//            manipulatingItem = null
        }

        viewModel.removeFromCartResp.observe(this) { requestState ->
            val resp = requestState ?: return@observe
            swipeRefresh.isRefreshing = resp is RequestState.Loading
            when (resp) {
                is RequestState.Error -> {
                    Snackbar.make(llParent, R.string.error, Snackbar.LENGTH_SHORT).show()
                }
                RequestState.Loading -> {
                }
                is RequestState.Success -> {
                    viewModel.getMyCart()
                    //                    loadData(resp.value)
//                    manipulatingItem?.let {
//                        it.first.obj.count = it.first.obj.count?.plus(it.second)
//                        adapter.notifyItemChanged(it.first.getPosition(it.first))
//                    }
                }
            }

//            manipulatingItem = null
        }

        viewModel.myCart.observe(this) {
            val resp = it ?: return@observe
            swipeRefresh.isRefreshing = resp is RequestState.Loading
            when (resp) {
                is RequestState.Error -> {
                    Snackbar.make(llParent, R.string.error, Snackbar.LENGTH_SHORT).show()
                }
                RequestState.Loading -> {
                }
                is RequestState.Success -> {
                    loadData(resp.value)
                }
            }
        }
    }

    private fun loadData(value: MyBasketResultDTO) {
        if (!value.list.isNullOrEmpty()) {
            val cartEntries = arrayListOf<ItemCartEntry>()
            value.list.forEach {
                cartEntries.add(
                    ItemCartEntry(it,
                        onInCrease = { itemCartEntry ->
                            if (viewModel.addToCartResp.value !is RequestState.Loading
                                && viewModel.removeFromCartResp.value !is RequestState.Loading
                            ) {
//                                manipulatingItem = Pair(itemCartEntry, 1)
                                viewModel.addToCart(it.itemInfo?.id!!, 1)
                            }
                        },
                        onDecrease = { itemCartEntry ->
                            if (viewModel.addToCartResp.value !is RequestState.Loading
                                && viewModel.removeFromCartResp.value !is RequestState.Loading
                            ) {
//                                manipulatingItem = Pair(itemCartEntry, -1)
                                viewModel.removeFromCart(it.itemInfo?.id!!, 1)
                            }
                        })
                )
            }
            adapter.update(cartEntries)
        } else {
            adapter.clear()
            adapter.add(ItemProductListEmpty())
        }

    }

}