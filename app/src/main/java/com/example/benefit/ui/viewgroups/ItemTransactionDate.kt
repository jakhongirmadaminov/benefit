package com.example.benefit.ui.viewgroups

import com.example.benefit.R
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_transaction_date.view.*
import org.joda.time.format.DateTimeFormat

class ItemTransactionDate(val date: Int) : Item() {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        val dateStr = DateTimeFormat.forPattern("dd MMM yyyy")
            .print(DateTimeFormat.forPattern("yyyyMMdd").parseDateTime(date.toString()))

        viewHolder.itemView.tvDate.text = dateStr
    }

    override fun getLayout() = R.layout.item_transaction_date

}
