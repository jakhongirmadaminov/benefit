package uz.magnumactive.benefit.ui.viewgroups

import android.view.View
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.remote.models.AutoPaymentDTO
import uz.magnumactive.benefit.util.loadImageUrl
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_payment_square.view.*

class ItemRegularPayment(
    val obj: AutoPaymentDTO? = null,
    val onClick: ((obj: AutoPaymentDTO) -> Unit)? = null,
    val onClickAdd: (() -> Unit)? = null
) : Item() {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        if (obj != null) {
            viewHolder.itemView.ivImage.visibility = View.VISIBLE
            obj.providerInfo?.image?.let {
                viewHolder.itemView.ivImage.loadImageUrl(it)
            }
            viewHolder.itemView.tvPaymentName.text = obj.title
            viewHolder.itemView.ivImage.setOnClickListener {
                onClick!!(obj)
            }
        } else {
            viewHolder.itemView.ivImage.visibility = View.INVISIBLE
            viewHolder.itemView.tvPaymentName.text = ""
            viewHolder.itemView.llAdd.setOnClickListener {
                onClickAdd!!()
            }
        }
    }

    override fun getLayout() = R.layout.item_payment_square

}
