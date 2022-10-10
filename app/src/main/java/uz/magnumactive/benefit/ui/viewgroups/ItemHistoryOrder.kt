package uz.magnumactive.benefit.ui.viewgroups

import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_history_order.view.*
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.remote.models.ActiveOrderDTO

class ItemHistoryOrder(val obj: ActiveOrderDTO) : Item() {


    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        val dateString: String = obj.createdText?.split(" ")!![0]
        val timeString: String = obj.createdText?.split(" ")!![1]
        viewHolder.itemView.apply {
            tvDate.text = dateString
            tvTime.text = timeString
            tvTotalSum.text = "${obj.totalSumma} UZS"

        }

    }

    override fun getLayout() = R.layout.item_history_order
}