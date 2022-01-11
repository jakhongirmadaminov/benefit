package com.example.benefit.ui.viewgroups

import androidx.core.view.isVisible
import com.example.benefit.R
import com.example.benefit.remote.models.BenefitContactDTO
import com.example.benefit.util.Constants.BASE_URL
import com.example.benefit.util.loadImageUrl
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_contact_square.view.*

data class ItemContactSquare(
    val contact: BenefitContactDTO? = null,
    var onClick: (() -> Unit)? = null
) :
    Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

        viewHolder.itemView.apply {
            contact?.let { friendDTO ->
                ivImage.isVisible = true
                friendDTO.avatar_link?.let {
                    ivImage.loadImageUrl(BASE_URL + it)
                } ?: run {
                    ivImage.setImageResource(R.drawable.ic_baseline_person_24)
                }
                ivDelete.isVisible = true
            } ?: run {
                ivDelete.isVisible = false
                ivImage.isVisible = false
                llAdd.setOnClickListener {
                    onClick?.invoke()
                }
            }


        }

    }

    override fun getLayout() = R.layout.item_contact_square
}
