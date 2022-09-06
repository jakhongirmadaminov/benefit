package uz.magnumactive.benefit.ui.viewgroups

import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_benefit_market.view.*
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.remote.models.MarketPlaceCategoryObj
import uz.magnumactive.benefit.util.loadImageUrl

class BenefitMarketItem(var marketItem: MarketPlaceCategoryObj, val onClick: () -> Unit) : Item() {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.apply {
            marketItem.image?.let {
                ivMarketItem.loadImageUrl(it)
            }
            tvMarketItemTitle.text = marketItem.title?.getLocalized()
            cardMarketItem.setOnClickListener {
                onClick()
            }
        }

    }

    override fun getLayout() = R.layout.item_benefit_market

}
