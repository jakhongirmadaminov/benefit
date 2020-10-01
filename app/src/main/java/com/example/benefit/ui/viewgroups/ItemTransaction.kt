package com.example.benefit.ui.viewgroups

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.benefit.R
import com.example.benefit.remote.models.TransactionDTO
import com.example.benefit.ui.transactions_history.transaction_bsd.TransactionBSD
import com.example.benefit.ui.transactions_history.transaction_bsd.TransactionFragment
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_transaction.view.*

class ItemTransaction(val obj: TransactionDTO) : Item() {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.tvAmount.text = obj.sum.toString()
        viewHolder.itemView.tvBankName.text = obj.title
        viewHolder.itemView.tvTransactionType.text = obj.type
        viewHolder.itemView.clParent.setOnClickListener {
            val dialog = TransactionBSD()
            dialog.arguments = Bundle().apply {
                putParcelable(TransactionBSD.ARG_TRANSACTION_DTO, obj)
            }
            dialog.show(
                (viewHolder.itemView.context as AppCompatActivity).supportFragmentManager,
                ""
            )
        }
    }

    override fun getLayout() = R.layout.item_transaction

}
