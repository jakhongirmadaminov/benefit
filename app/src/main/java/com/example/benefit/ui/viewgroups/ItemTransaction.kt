package com.example.benefit.ui.viewgroups

import androidx.appcompat.app.AppCompatActivity
import com.example.benefit.R
import com.example.benefit.remote.models.TransactionDTO
import com.example.benefit.ui.transactions_history.transaction_bsd.TransactionBSD
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_transaction.view.*

class ItemTransaction(val obj: TransactionDTO) : Item() {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.tvAmount.text = obj.sum.toString()
        viewHolder.itemView.tvBankName.text = obj.title
        viewHolder.itemView.tvTransactionType.text = obj.type
        viewHolder.itemView.clParent.setOnClickListener {
            TransactionBSD().show(
                (viewHolder.itemView.context as AppCompatActivity).supportFragmentManager,
                ""
            )
        }
    }

    override fun getLayout() = R.layout.item_transaction

}
