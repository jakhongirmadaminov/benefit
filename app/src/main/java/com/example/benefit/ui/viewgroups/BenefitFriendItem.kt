package com.example.benefit.ui.viewgroups

import android.graphics.Color
import com.example.benefit.R
import com.example.benefit.remote.models.BenefitContactDTO
import com.example.benefit.util.Constants
import com.example.benefit.util.loadImageUrl
import com.example.benefit.util.loadImageUrlAndShrink
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_contact_with_cb.view.*

class BenefitFriendItem(var friend: BenefitContactDTO, var onCheckChanged: (Boolean) -> Unit) :
    Item() {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

        viewHolder.itemView.apply {
            friend.avatar_link?.let {
                photo.loadImageUrlAndShrink(Constants.BASE_URL + it.removeSuffix("/"))
            } ?: run {
                photo.setColorFilter(Color.BLACK)
                photo.setImageResource(R.drawable.ic_baseline_person_24)
            }
            tvNameLastName.text = friend.fullname
            checkBox.isChecked = friend.isChecked

            llContact.setOnClickListener {
                checkBox.toggle()
                friend.isChecked = checkBox.isChecked
                onCheckChanged(friend.isChecked)
            }
        }

    }

    override fun getLayout() = R.layout.item_contact_with_cb

}
