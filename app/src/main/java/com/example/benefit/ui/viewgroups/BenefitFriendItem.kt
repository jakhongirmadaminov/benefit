package com.example.benefit.ui.viewgroups

import com.example.benefit.R
import com.example.benefit.remote.models.BenefitContactDTO
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_contact_with_cb.view.*

class BenefitFriendItem(var friend: BenefitContactDTO, var onCheckChanged: () -> Unit) : Item() {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

        viewHolder.itemView.tvNameLastName.text = friend.fullname
        viewHolder.itemView.checkBox.isChecked = friend.isChecked

        viewHolder.itemView.llContact.setOnClickListener {
            viewHolder.itemView.checkBox.toggle()
            friend.isChecked = viewHolder.itemView.checkBox.isChecked
            onCheckChanged()
        }

    }

    override fun getLayout() = R.layout.item_contact_with_cb

}
