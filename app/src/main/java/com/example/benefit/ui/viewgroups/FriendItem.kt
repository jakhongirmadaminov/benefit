package com.example.benefit.ui.viewgroups

import com.example.benefit.R
import com.example.benefit.remote.models.FriendDTO
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_contact_with_cb.view.*

class FriendItem(var friend: FriendDTO) : Item() {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

        viewHolder.itemView.tvNameLastName.text = friend.name + " " + friend.phone
    }

    override fun getLayout() = R.layout.item_contact_with_cb

}
