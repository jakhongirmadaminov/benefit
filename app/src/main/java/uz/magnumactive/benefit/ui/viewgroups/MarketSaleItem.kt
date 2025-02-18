package uz.magnumactive.benefit.ui.viewgroups

import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_market_sale.view.*
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.remote.models.MarketProductDTO
import uz.magnumactive.benefit.util.loadImageUrl

class MarketSaleItem(
    val obj: MarketProductDTO,
    val onClick: () -> Unit,
    val onAddToCart: () -> Unit
) : Item() {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

        viewHolder.itemView.apply {
            obj.photos?.let {
                if (it.isNotEmpty()) {
                    ivPicture.loadImageUrl(it[0].photos!!)
                }
            }

            tvItemTitle.text = obj.title?.getLocalized()

            cardProduct.setOnClickListener { onClick() }
            btnAddToCard.setOnClickListener { onAddToCart() }
        }

    }

    override fun getLayout() = R.layout.item_market_sale

}
