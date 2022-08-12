package uz.magnumactive.benefit.ui.viewgroups

import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_market_catalog.view.*
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.remote.models.MarketPlaceCategoryObj

class MarketCatalogItem(val categoryObj: MarketPlaceCategoryObj) : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

        viewHolder.itemView.apply {
            title.text = categoryObj.title?.getLocalized()
        }
    }

    override fun getLayout() = R.layout.item_market_catalog

}
