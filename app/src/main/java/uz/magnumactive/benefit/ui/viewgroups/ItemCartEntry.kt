package uz.magnumactive.benefit.ui.viewgroups

import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.remote.models.MarketProductDTO

class ItemCartEntry(
    val it: MarketProductDTO,
    val onInCrease: () -> Unit,
    val onDecrease: () -> Unit
) : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

        viewHolder.itemView.apply { }

    }

    override fun getLayout() = R.layout.item_cart_entry

}
