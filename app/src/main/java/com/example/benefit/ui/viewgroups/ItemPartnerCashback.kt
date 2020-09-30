package com.example.benefit.ui.viewgroups

import com.example.benefit.R
import com.example.benefit.remote.models.PartnerCategoryDTO
import com.example.benefit.ui.partner_home.PartnerHomeActivity
import com.example.benefit.util.loadImageUrl
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_partner_cashback.view.*
import splitties.activities.start

class ItemPartnerCashback(val partner: PartnerCategoryDTO) : Item() {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

        viewHolder.itemView.ivIcon.loadImageUrl(partner.image)
        viewHolder.itemView.tvBrand.text = partner.title_ru

        viewHolder.itemView.clParent.setOnClickListener {
            viewHolder.itemView.context.start<PartnerHomeActivity> {
                putExtra(PartnerHomeActivity.EXTRA_PARTNER, partner)
            }
        }

    }

    override fun getLayout() = R.layout.item_partner_cashback

}
