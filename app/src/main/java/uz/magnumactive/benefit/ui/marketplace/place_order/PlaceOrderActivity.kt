package uz.magnumactive.benefit.ui.marketplace.place_order

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
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

        viewModel.placeOrderResp.observe(this) {
            val resp = it ?: return@observe
            progress.visibility = if (resp is RequestState.Loading) View.VISIBLE else View.INVISIBLE
            when (resp) {
                is RequestState.Error -> {
                    Snackbar.make(
                        llParent,
                        resp.message ?: getString(R.string.error),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                RequestState.Loading -> {

                }
                is RequestState.Success -> {
                    BSDOrderPlaced().show(supportFragmentManager, "")
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
            value.userCard[0].isChecked = true
            tvSelectedCard.text = value.userCard[0].panHidden
            viewModel.selectedCard = value.userCard[0]
        }
    }

    private fun setupView() {
    }


    private fun attachListeners() {

        payPurchase.setOnClickListener {
            viewModel.selectedLatLng?.let { latLng ->
                viewModel.placeOrder(latLng)
            }
        }

        ivSetAddress.setOnClickListener {
            val mapDialog = SelectAddressOnMapBSD { latLng, locationString ->
                tvAddress.text = locationString
                viewModel.addressString = locationString ?: ""
                viewModel.selectedLatLng = latLng
                setupPayBtn()
            }
            mapDialog.show(supportFragmentManager, "")
        }

        ivSetPaymentType.setOnClickListener {
            (viewModel.preparedOrderResp.value as? RequestState.Success)?.value?.userCard?.let { cards ->
                val dialog = CardsBSD(cards,
                    selectedCard = { selectedCard ->
                        tvSelectedCard.text = selectedCard.panHidden
                        viewModel.selectedCard = selectedCard
                        setupPayBtn()
                    },
                    cardAdded = {
                        viewModel.getPreparedOrder()
                    }
                )
                dialog.show(supportFragmentManager, "")
            }
        }
    }

    private fun setupPayBtn() {
        if (viewModel.selectedCard != null && viewModel.selectedLatLng != null) {
            payPurchase.myEnabled(true)
            payPurchase.setTextColor(Color.WHITE)
        } else {
            payPurchase.myEnabled(false)
            payPurchase.setTextColor(ContextCompat.getColor(this, R.color.textlightGrey))
        }
    }

    companion object{
        const val ORDER_PLACEMENT_SUCCESS= 232
    }

}


