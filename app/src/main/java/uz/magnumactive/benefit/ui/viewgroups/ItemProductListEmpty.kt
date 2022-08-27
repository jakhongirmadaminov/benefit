package uz.magnumactive.benefit.ui.viewgroups

import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import uz.magnumactive.benefit.R

class ItemProductListEmpty : Item() {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
    }

    override fun getLayout() = R.layout.item_market_products_empty

}
