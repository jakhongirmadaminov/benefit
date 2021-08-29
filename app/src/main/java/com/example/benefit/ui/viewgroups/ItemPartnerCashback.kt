package com.example.benefit.ui.viewgroups

import com.example.benefit.R
import com.example.benefit.remote.models.Partner
import com.example.benefit.util.loadImageUrl
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_partner_cashback.view.*

class ItemPartnerCashback(val partner: Partner, val onClick: (partner: Partner) -> Unit) : Item() {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

        viewHolder.itemView.ivIcon.loadImageUrl(partner.icon_image!!)
        viewHolder.itemView.tvBrand.text = partner.title

        viewHolder.itemView.clParent.setOnClickListener {
            onClick(partner)
        }

    }

    override fun getLayout() = R.layout.item_partner_cashback

}
