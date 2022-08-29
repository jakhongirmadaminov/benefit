package uz.magnumactive.benefit.ui.marketplace

import android.os.Bundle
import androidx.activity.viewModels
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_basket.*
import kotlinx.android.synthetic.main.activity_gap_chart.tool_bar
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.remote.models.MyCartResultDTO
import uz.magnumactive.benefit.ui.base.BaseActionbarActivity
import uz.magnumactive.benefit.ui.viewgroups.ItemCartEntry
import uz.magnumactive.benefit.ui.viewgroups.ItemLoading
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
        setupView()
        subscribeObservers()
    }

    private fun setupView() {
        rvCart.adapter = adapter

    }

    fun subscribeObservers() {
        viewModel.myCart.observe(this) {
            val resp = it ?: return@observe
            when (resp) {
                is RequestState.Error -> {
                    adapter.clear()
                }
                RequestState.Loading -> {
                    adapter.clear()
                    adapter.add(ItemLoading())
                }
                is RequestState.Success -> {
                    loadData(resp.value)
                }
            }
            adapter.notifyDataSetChanged()
        }
    }

    private fun loadData(value: MyCartResultDTO) {
        adapter.clear()
        if (!value.products.isNullOrEmpty()) {
            value.products.forEach {
                adapter.add(
                    ItemCartEntry(it,
                        onInCrease = {

                        },
                        onDecrease = {

                        })
                )
            }

        } else {
            adapter.add(ItemProductListEmpty())
        }

    }

}