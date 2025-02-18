package uz.magnumactive.benefit.ui.viewgroups

import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.remote.models.TransactionDTO
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_transaction_txt_only.view.*
import org.joda.time.format.DateTimeFormat

class ItemTransactionTxtOnly(val obj: TransactionDTO) : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

        viewHolder.itemView.tvDate.text =
            DateTimeFormat.forPattern(("dd.MM.yyyy")).print(obj.date!!)
        viewHolder.itemView.tvSum.text = obj.sum.toString() + " UZS"
        viewHolder.itemView.tvTime.text = obj.type
    }

    override fun getLayout() = R.layout.item_transaction_txt_only
}
