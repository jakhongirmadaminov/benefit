package uz.magnumactive.benefit.ui.viewgroups

import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.remote.models.PaynetCategory
import uz.magnumactive.benefit.util.AppPrefs
import uz.magnumactive.benefit.util.Constants.UZ
import uz.magnumactive.benefit.util.loadImageUrl
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_payment.view.*

class ItemPaynet(val obj: PaynetCategory, val onClick: (PaynetCategory) -> Unit) : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

        viewHolder.itemView.tvPaymentName.text = if (AppPrefs.language == UZ) obj.titleUz else obj.titleRu
        viewHolder.itemView.clParent.setOnClickListener {
            onClick(obj)
        }
        obj.image?.let {
            viewHolder.itemView.ivLogo.loadImageUrl(it)
        }
    }

    override fun getLayout() = R.layout.item_paynet
}
