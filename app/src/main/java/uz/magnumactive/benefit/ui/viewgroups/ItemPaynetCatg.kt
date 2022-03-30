package uz.magnumactive.benefit.ui.viewgroups

import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.remote.models.PaynetCategory
import uz.magnumactive.benefit.util.loadImageUrl
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_paynet_category.card
import kotlinx.android.synthetic.main.item_paynet_category.ivImage
import kotlinx.android.synthetic.main.item_paynet_category.tvPaymentName

class ItemPaynetCatg(val paynetCategory: PaynetCategory, val onClick: (PaynetCategory) -> Unit) :
    Item() {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.card.setBackgroundResource(R.drawable.shape_oval_white)
        if (paynetCategory.paymentTypeEnum != null) {
            viewHolder.ivImage.setImageResource(paynetCategory.imageResource!!)
            viewHolder.tvPaymentName.text = paynetCategory.titleRu

        } else {
            viewHolder.ivImage.loadImageUrl(paynetCategory.image!!)
            viewHolder.tvPaymentName.text = paynetCategory.titleRu
        }
        viewHolder.card.setOnClickListener {
            onClick(paynetCategory)
        }
    }

    override fun getLayout() = R.layout.item_paynet_category

}
