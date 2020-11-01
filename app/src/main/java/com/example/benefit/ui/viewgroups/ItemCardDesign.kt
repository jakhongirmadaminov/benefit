package com.example.benefit.ui.viewgroups

import com.example.benefit.R
import com.example.benefit.remote.models.CardBgDTO
import com.example.benefit.util.loadImageUrl
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_card_design.*

class ItemCardDesign(val it: CardBgDTO, val onClick: () -> Unit) : Item() {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

        viewHolder.ivCardPattern.loadImageUrl(it.image)
        viewHolder.cardDesignItem.setOnClickListener {
            onClick()
        }

    }

    override fun getLayout() = R.layout.item_card_design

}
