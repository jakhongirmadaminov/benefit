package uz.magnumactive.benefit.ui.viewgroups

import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.remote.models.ActiveOrderDTO
import uz.magnumactive.benefit.remote.models.HistoryOrderDTO

class ItemHistoryOrder(val obj: HistoryOrderDTO) : Item() {


    override fun bind(viewHolder: GroupieViewHolder, position: Int) {


    }

    override fun getLayout() = R.layout.item_history_order
}