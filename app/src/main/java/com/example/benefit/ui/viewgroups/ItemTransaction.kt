package com.example.benefit.ui.viewgroups

import android.util.TypedValue
import androidx.core.view.isVisible
import com.example.benefit.R
import com.example.benefit.remote.models.TransactionAnalyticsDTO
import com.example.benefit.util.TransTypeTranslator
import com.example.benefit.util.loadImageUrl
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_transaction.view.*
import java.text.DecimalFormat


class ItemTransaction(val obj: TransactionAnalyticsDTO, val onClick: (() -> Unit)? = null) :
    Item() {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.tvAmount.text =
            DecimalFormat("#,###").format(obj.amountWithoutTiyin) + " UZS"
        viewHolder.itemView.tvBankName.text = obj.merchantName

        viewHolder.itemView.tvMinus.isVisible = obj.isCredit == false

        obj.partner?.image?.let {
            viewHolder.itemView.icBankLogo.loadImageUrl(it)
        }
        viewHolder.itemView.tvTransactionType.text = TransTypeTranslator.translate(obj.transType)

        onClick?.let { click ->
            viewHolder.itemView.isClickable = true
            viewHolder.itemView.isFocusable = true
            val outValue = TypedValue()
            viewHolder.itemView.context.theme.resolveAttribute(
                android.R.attr.selectableItemBackground,
                outValue,
                true
            )
            viewHolder.itemView.setBackgroundResource(outValue.resourceId)
            viewHolder.itemView.clParent.setOnClickListener {
                click()
            }
        } ?: run {
            viewHolder.itemView.isClickable = false
            viewHolder.itemView.isFocusable = false
        }

    }

    override fun getLayout() = R.layout.item_transaction

}
