package uz.magnumactive.benefit.ui.viewgroups

import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.grid_item_market_product.view.*
import kotlinx.android.synthetic.main.item_market_sale.view.ivPicture
import kotlinx.android.synthetic.main.item_market_sale.view.tvItemTitle
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.remote.models.MarketProductDTO
import uz.magnumactive.benefit.util.loadImageUrl

class MarketGridProductItem(val obj: MarketProductDTO, val onClick: () -> Unit) : Item() {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

        viewHolder.itemView.apply {
            obj.photos?.let {
                if (it.isNotEmpty()) {
                    ivPicture.loadImageUrl(it[0].photos!!)
                }
            }

            tvItemTitle.text = obj.title?.getLocalized()
            card.setOnClickListener {
                onClick()
            }
        }

    }

    override fun getLayout() = R.layout.grid_item_market_product

}
