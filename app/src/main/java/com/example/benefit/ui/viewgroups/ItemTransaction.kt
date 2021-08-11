package com.example.benefit.ui.viewgroups

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.benefit.R
import com.example.benefit.remote.models.TransactionAnalyticsDTO
import com.example.benefit.ui.transactions_history.transaction_bsd.TransactionBSD
import com.example.benefit.util.TransTypeTranslator
import com.example.benefit.util.loadImageUrl
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_transaction.view.*
import java.text.DecimalFormat

class ItemTransaction(val obj: TransactionAnalyticsDTO) : Item() {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.tvAmount.text =
            DecimalFormat("#,###").format(obj.amountWithoutTiyin) + " UZS"
        viewHolder.itemView.tvBankName.text = obj.merchantName

        viewHolder.itemView.tvMinus.isVisible = obj.isCredit == false

        obj.partner?.image?.let {
            viewHolder.itemView.icBankLogo.loadImageUrl(it)
        }
        viewHolder.itemView.tvTransactionType.text = TransTypeTranslator.translate(obj.transType)
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
