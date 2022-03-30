package uz.magnumactive.benefit.ui.viewgroups

import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.remote.models.PaynetMerchant
import uz.magnumactive.benefit.util.loadImageUrl
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_payment.view.*

class ItemPaynetMerchant(val obj: PaynetMerchant, val onClick: (PaynetMerchant) -> Unit) : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

        viewHolder.itemView.apply {
            tvPaymentName.text = /*if (AppPrefs.language == UZ) */
                obj.titleShort /*else obj.titleShort*/
            clParent.setOnClickListener { onClick(obj) }
            obj.image?.let {
                ivLogo.loadImageUrl(it)
            }
        }
    }

    override fun getLayout() = R.layout.item_merchants
}
