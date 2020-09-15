package com.example.benefit.ui.viewgroups

import com.example.benefit.R
import com.example.benefit.remote.models.PartnerDTO
import com.example.benefit.util.loadImageUrl
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_partner.view.*

class ItemPartner(val partner: PartnerDTO) : Item() {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

        viewHolder.itemView.ivImage.loadImageUrl(partner.image!!)
        viewHolder.itemView.tvPartnerTitle.text = partner.title

    }

    override fun getLayout() = R.layout.item_partner

}
