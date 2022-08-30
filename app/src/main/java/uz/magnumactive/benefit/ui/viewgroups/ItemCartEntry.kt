package uz.magnumactive.benefit.ui.viewgroups

import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_cart_entry.view.*
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.remote.models.MarketBasketProductDTO
import java.text.DecimalFormat

class ItemCartEntry(
    val obj: MarketBasketProductDTO,
    val onInCrease: () -> Unit,
    val onDecrease: () -> Unit
) : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

        viewHolder.itemView.apply {
            tvTitle.text = obj.itemInfo?.title?.getLocalized() ?: ""
            tvEntrySum.text =
                DecimalFormat("#,###").format(obj.itemInfo?.realSumma!! * obj.count!!) + " UZS"
            tvCount.text = obj.count.toString()
            ivMinus.setOnClickListener {
                    onDecrease()
            }
            ivPlus.setOnClickListener {
                onInCrease()
            }
        }

    }

    override fun getLayout() = R.layout.item_cart_entry

    override fun hasSameContentAs(other: com.xwray.groupie.Item<*>): Boolean {
        return this.obj == (other as ItemCartEntry).obj
    }
}
