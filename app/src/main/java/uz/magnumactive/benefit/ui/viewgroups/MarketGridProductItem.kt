package uz.magnumactive.benefit.ui.viewgroups

import android.graphics.Paint
import android.view.View
import androidx.core.view.isVisible
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.grid_item_market_product.view.*
import kotlinx.android.synthetic.main.item_market_sale.view.ivPicture
import kotlinx.android.synthetic.main.item_market_sale.view.tvItemTitle
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.remote.models.MarketProductDTO
import uz.magnumactive.benefit.util.loadImageUrl
import java.text.DecimalFormat

class MarketGridProductItem(
    val obj: MarketProductDTO,
    val onClick: () -> Unit,
    val onAddToCart: () -> Unit
) : Item() {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

        viewHolder.itemView.apply {
            tvStrikeThroughPrice.paintFlags =
                tvStrikeThroughPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            obj.photos?.let {
                if (it.isNotEmpty()) {
                    ivPicture.loadImageUrl(it[0].photos!!)
                }
            }
            if (obj.salePercent != null && obj.salePercent > 0) {
                tvSalePercentage.isVisible = true
                tvSalePercentage.text = obj.salePercent.toString()
                tvStrikeThroughPrice.isVisible = true
                tvStrikeThroughPrice.text = obj.oldSumma.toString()
            } else {
                tvStrikeThroughPrice.visibility = View.GONE
                tvSalePercentage.isVisible = false
            }
            tvPrice.text = DecimalFormat("#,###").format(obj.realSumma) + " UZS"
//            tvItemCode.text =
            tvItemTitle.text = obj.title?.getLocalized()
            card.setOnClickListener {
                onClick()
            }
            btnAddToCard.setOnClickListener {
                onAddToCart()
            }
        }

    }

    override fun getLayout() = R.layout.grid_item_market_product

}
