package uz.magnumactive.benefit.ui.viewgroups

import android.graphics.Paint
import android.view.View
import androidx.core.view.isVisible
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_market_favourite.view.*
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.remote.models.MarketFavouriteProductDTO
import uz.magnumactive.benefit.util.loadImageUrl
import java.text.DecimalFormat

class MarketFavouriteItem(
    val obj: MarketFavouriteProductDTO,
    val onClick: () -> Unit,
    val onAddToCart: () -> Unit,
    val removeFromFavourites: () -> Unit,
) : Item() {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

        viewHolder.itemView.apply {
            tvStrikeThroughPrice.paintFlags =
                tvStrikeThroughPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            obj.itemInfo?.let { product ->
                product.photos?.let {
                    if (it.isNotEmpty()) {
                        ivPicture.loadImageUrl(it[0].photos!!)
                    }
                }
                if (product.salePercent != null && product.salePercent > 0) {
                    tvSalePercentage.isVisible = true
                    tvSalePercentage.text = product.salePercent.toString()
                    tvStrikeThroughPrice.isVisible = true
                    tvStrikeThroughPrice.text = product.oldSumma.toString()
                } else {
                    tvStrikeThroughPrice.visibility = View.GONE
                    tvSalePercentage.isVisible = false
                }
                tvPrice.text = DecimalFormat("#,###").format(product.realSumma) + " UZS"
//            tvItemCode.text =
                tvItemTitle.text = product.title?.getLocalized()
                card.setOnClickListener {
                    onClick()
                }
                btnAddToCard.setOnClickListener {
                    onAddToCart()
                }
                btnRemove.setOnClickListener {
                    removeFromFavourites()
                }
            }
        }

    }

    override fun getLayout() = R.layout.item_market_favourite

}
