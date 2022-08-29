package uz.magnumactive.benefit.ui.viewgroups

import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_cart_entry.view.*
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.remote.models.MarketBasketProductDTO
import java.text.DecimalFormat

class ItemCartEntry(
    val obj: MarketBasketProductDTO,
    val onInCrease: (ItemCartEntry) -> Unit,
    val onDecrease: (ItemCartEntry) -> Unit
) : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

        viewHolder.itemView.apply {
            tvTitle.text = obj.itemInfo?.title?.getLocalized() ?: ""
            tvEntrySum.text =
                DecimalFormat("#,###").format(obj.itemInfo?.realSumma!! * obj.count!!) + " UZS"
            tvCount.text = obj.count.toString()
            ivMinus.setOnClickListener {
                if (obj.count!! > 1) {
                    onDecrease(this@ItemCartEntry)
                }
            }
            ivPlus.setOnClickListener {
                onInCrease(this@ItemCartEntry)
            }
        }

    }

    override fun getLayout() = R.layout.item_cart_entry

    override fun hasSameContentAs(other: com.xwray.groupie.Item<*>): Boolean {
        return this.obj == (other as ItemCartEntry).obj
    }
}
