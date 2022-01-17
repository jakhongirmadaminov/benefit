package com.example.benefit.ui.viewgroups

import com.example.benefit.R
import com.example.benefit.remote.models.GapGameDTO
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_gap_game.view.*

data class ItemGapGame(val it: GapGameDTO, val onClick: () -> Unit) : Item() {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.apply {
            lblCardTitle.text = it.name
            tvPlayerCount.text = context.getString(R.string.count) + ": " + it.members!!.size
        }
    }

    override fun getLayout() = R.layout.item_gap_game
}
