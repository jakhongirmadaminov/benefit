package com.example.benefit.ui.viewgroups

import com.example.benefit.R
import com.example.benefit.remote.models.PartnerCategoryDTO
import com.example.benefit.remote.models.PartnerDTO
import com.example.benefit.ui.selected_partners_category.SelectedPartnersCategoryActivity
import com.example.benefit.util.loadImageUrl
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_partner.view.*
import splitties.activities.start

class ItemPartnerCategory(val partner: PartnerCategoryDTO) : Item() {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

        viewHolder.itemView.ivImage.loadImageUrl(partner.image)
        viewHolder.itemView.tvPartnerTitle.text = partner.title_ru
        viewHolder.itemView.cardCategory.setOnClickListener {
            viewHolder.itemView.context.start<SelectedPartnersCategoryActivity> {
                putExtra(SelectedPartnersCategoryActivity.EXTRA_CATEGORY, partner)
            }
        }

    }

    override fun getLayout() = R.layout.item_partner

}
