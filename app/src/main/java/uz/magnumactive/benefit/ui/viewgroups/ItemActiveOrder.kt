package uz.magnumactive.benefit.ui.viewgroups

import android.view.View.inflate
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_active_order.view.*
import kotlinx.android.synthetic.main.item_active_order_entry.view.*
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.remote.models.ActiveOrderDTO
import java.text.DecimalFormat

class ItemActiveOrder(val obj: ActiveOrderDTO) : Item() {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.apply {
            llItemsContainer.removeAllViews()
            obj.orderItems?.forEach { entryProduct ->
                val entryView = inflate(context, R.layout.item_active_order_entry, null)
                entryView.tvEntrySum.text =
                    DecimalFormat("#,###").format(entryProduct!!.summa!!) + " UZS"
                entryView.tvTitle.text = entryProduct.itemInfo?.title?.getLocalized()
                llItemsContainer.addView(entryView)
            }

            tvTotalSum.text = obj.totalSumma.toString()
            tvStatus.text = obj.statusTranslate?.getLocalized()
        }

    }

    override fun getLayout() = R.layout.item_active_order
}