package uz.magnumactive.benefit.ui.marketplace.cart

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_basket.*
import kotlinx.android.synthetic.main.activity_gap_chart.tool_bar
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.remote.models.MyBasketResultDTO
import uz.magnumactive.benefit.ui.base.BaseActionbarActivity
import uz.magnumactive.benefit.ui.marketplace.place_order.PlaceOrderActivity
import uz.magnumactive.benefit.ui.viewgroups.ItemCartEntry
import uz.magnumactive.benefit.ui.viewgroups.ItemProductListEmpty
import uz.magnumactive.benefit.util.RequestState
import java.text.DecimalFormat

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
        lblAdded.setOnClickListener {
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                if(it.resultCode == Activity.RESULT_OK){
                    viewModel.getMyCart()
                }
            }.launch(Intent(this, PlaceOrderActivity::class.java))
        }
    }

    private fun setupView() {
        rvCart.adapter = adapter

    }

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
                }
            }
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
                }
            }
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
        tvGrandTotal.text = DecimalFormat("#,###").format(value.totalSum) + " UZS"
        if (!value.list.isNullOrEmpty()) {
            lblAdded.isEnabled = true
            lblAdded.setTextColor(Color.WHITE)
            ivCleanBasket.visibility = View.VISIBLE
            val cartEntries = arrayListOf<ItemCartEntry>()
            value.list.forEach {
                cartEntries.add(
                    ItemCartEntry(it,
                        onInCrease = {
                            if (viewModel.addToCartResp.value !is RequestState.Loading
                                && viewModel.removeFromCartResp.value !is RequestState.Loading
                            ) {
                                viewModel.addToCart(it.itemInfo?.id!!, 1)
                            }
                        },
                        onDecrease = {
                            if (viewModel.addToCartResp.value !is RequestState.Loading
                                && viewModel.removeFromCartResp.value !is RequestState.Loading
                            ) {
                                viewModel.removeFromCart(it.itemInfo?.id!!, 1)
                            }
                        })
                )
            }
            adapter.update(cartEntries)
        } else {
            lblAdded.setTextColor(ContextCompat.getColor(this, R.color.primary_text))
            lblAdded.isEnabled = false
            ivCleanBasket.visibility = View.INVISIBLE
            adapter.clear()
            adapter.add(ItemProductListEmpty())
        }

    }

}