package uz.magnumactive.benefit.ui.marketplace.selected_category

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.grid_item_market_product.view.*
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.remote.models.MarketProductDTO
import uz.magnumactive.benefit.util.loadImageUrl
import java.text.DecimalFormat


class MarketProductPagingAdapter(
    val context: Context,
    val onClick: (MarketProductDTO) -> Unit,
    val onAddToCart: (MarketProductDTO) -> Unit
) : PagingDataAdapter<MarketProductDTO, MarketProductPagingAdapter.MarketGridProductItemViewHolder>(
    MarketProductDTOComparator
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : MarketGridProductItemViewHolder {
        val formNameView = LayoutInflater.from(parent.context)
            .inflate(R.layout.grid_item_market_product, parent, false)
        return MarketGridProductItemViewHolder(formNameView)
    }


    override fun onBindViewHolder(holder: MarketGridProductItemViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class MarketGridProductItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(obj: MarketProductDTO) {
            view.apply {
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
                    onClick(obj)
                }
                btnAddToCard.setOnClickListener {
                    onAddToCart(obj)
                }
            }
        }
    }

    object MarketProductDTOComparator : DiffUtil.ItemCallback<MarketProductDTO>() {
        override fun areItemsTheSame(oldItem: MarketProductDTO, newItem: MarketProductDTO) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: MarketProductDTO, newItem: MarketProductDTO) =
            oldItem == newItem
    }

}