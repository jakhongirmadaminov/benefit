package com.example.benefit.ui.viewgroups

import com.example.benefit.R
import com.example.benefit.remote.models.RegularPaymentDTO
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_payment.view.*

class ItemPayment(val obj: RegularPaymentDTO, val onClick: () -> Unit) : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

        viewHolder.itemView.tvPaymentName.text = obj.name
        viewHolder.itemView.clParent.setOnClickListener {
            onClick()
        }

    }

    override fun getLayout() = R.layout.item_payment
}
