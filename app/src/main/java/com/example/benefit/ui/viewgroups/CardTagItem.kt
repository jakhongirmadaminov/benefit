package com.example.benefit.ui.viewgroups

import android.graphics.Color
import androidx.core.content.ContextCompat
import com.example.benefit.R
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_card_tag.view.*

class CardTagItem(val cardTitle: String, val onClick: (item: CardTagItem) -> Unit) : Item() {

    var selected = false

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.tvCardTitle.text = cardTitle

        if (selected) {
            viewHolder.itemView.tvCardTitle.setBackgroundResource(R.drawable.selector_orange)
            viewHolder.itemView.tvCardTitle.setTextColor(Color.WHITE)
        } else {
            viewHolder.itemView.tvCardTitle.setBackgroundResource(R.drawable.selector_grey_rounded)
            viewHolder.itemView.tvCardTitle.setTextColor(
                ContextCompat.getColor(
                    viewHolder.itemView.context,
                    R.color.grey_text
                )
            )
        }

        viewHolder.itemView.tvCardTitle.setOnClickListener {
            onClick(this)
        }

    }

    override fun getLayout() = R.layout.item_card_tag

}
