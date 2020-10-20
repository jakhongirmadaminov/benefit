package com.example.benefit.ui.viewgroups

import com.example.benefit.R
import com.example.benefit.remote.models.PaynetCategory
import com.example.benefit.util.loadImageUrl
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_paynet_category.*

class ItemPaynetCatg(val paynetCategory: PaynetCategory, val onClick: () -> Unit) : Item() {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.card.setBackgroundResource(R.drawable.shape_oval_white)
        if (paynetCategory.paymentTypeEnum != null) {
            viewHolder.ivImage.setImageResource(paynetCategory.imageResource!!)
            viewHolder.tvPaymentName.text = paynetCategory.titleRu
           viewHolder.card.setOnClickListener {
               onClick()
           }
        } else {
            viewHolder.ivImage.loadImageUrl(paynetCategory.image!!)
            viewHolder.tvPaymentName.text = paynetCategory.titleRu
        }
    }

    override fun getLayout() = R.layout.item_paynet_category

}
