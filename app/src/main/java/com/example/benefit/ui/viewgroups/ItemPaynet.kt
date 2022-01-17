package com.example.benefit.ui.viewgroups

import com.example.benefit.R
import com.example.benefit.remote.models.PaynetCategory
import com.example.benefit.util.AppPrefs
import com.example.benefit.util.Constants.UZ
import com.example.benefit.util.loadImageUrl
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_payment.view.*

class ItemPaynet(val obj: PaynetCategory, val onClick: (PaynetCategory) -> Unit) : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

        viewHolder.itemView.tvPaymentName.text =
            if (AppPrefs.language == UZ) obj.titleUz else obj.titleRu
        viewHolder.itemView.clParent.setOnClickListener {
            onClick(obj)
        }
        obj.image?.let {
            viewHolder.itemView.ivLogo.loadImageUrl(it)
        }
    }

    override fun getLayout() = R.layout.item_paynet
}
