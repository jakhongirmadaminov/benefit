package uz.magnumactive.benefit.ui.marketplace.place_order

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_place_order.*
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.remote.models.PreparedOrderDTO
import uz.magnumactive.benefit.ui.base.BaseActionbarActivity
import uz.magnumactive.benefit.ui.marketplace.dialogs.SelectAddressOnMapBSD
import uz.magnumactive.benefit.util.RequestState
import java.text.DecimalFormat

class PlaceOrderActivity : BaseActionbarActivity() {


    private val viewModel: PlaceOrderViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_place_order)
        setSupportActionBar(tool_bar)
        super.onCreate(savedInstanceState)

        setupView()
        attachListeners()
        subscribeObservers()
        viewModel.getPreparedOrder()
    }

    fun subscribeObservers() {
        viewModel.preparedOrderResp.observe(this) {
            val resp = it ?: return@observe
            progress.visibility = if (resp is RequestState.Loading) View.VISIBLE else View.INVISIBLE
            when (resp) {
                is RequestState.Error -> {
                    Snackbar.make(llParent, R.string.error, Snackbar.LENGTH_SHORT).show()
                }
                RequestState.Loading -> {}
                is RequestState.Success -> {
                    loadData(resp.value)
                }
            }
        }
    }

    private fun loadData(value: PreparedOrderDTO) {
        tvTotal.text = DecimalFormat("#,###").format(value.totalSumma) + " UZS"
        tvBasketSum.text = DecimalFormat("#,###").format(value.basketTotalSumma) + " UZS"
        tvDeliverySum.text = DecimalFormat("#,###").format(value.deliverySumma) + " UZS"
        tvDeliveryDays.text = getString(R.string.delivery_days, value.deliveryDays)
        if (!value.userCard.isNullOrEmpty()) {
            tvSelectedCard.text = value.userCard[0].panHidden
        }

//        tvAddress.text =

    }

    private fun setupView() {
    }


    private fun attachListeners() {

        payPurchase.setOnClickListener {
            viewModel.selectedLatLng?.let {

            }
        }

        ivSetAddress.setOnClickListener {
            val mapDialog = SelectAddressOnMapBSD { latLng, locationString ->
                tvAddress.text = locationString
                viewModel.selectedLatLng = latLng
                payPurchase.myEnabled(true)
                payPurchase.setTextColor(Color.WHITE)
            }
            mapDialog.show(supportFragmentManager, "")
        }

        ivSetPaymentType.setOnClickListener {

        }

    }
}
