package com.example.benefit.ui.viewgroups

import android.graphics.Color
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.example.benefit.R
import com.example.benefit.remote.models.PartnerCategoryDTO
import com.example.benefit.util.loadImageUrl
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_category_small.view.*


class ItemCategorySmall(
    val obj: PartnerCategoryDTO,
    var expense: Long? = null,
    var percentage: Float? = null
) : Item() {


    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.tvCategoryTitle.text = obj.title_ru
        viewHolder.itemView.ivIcon.loadImageUrl(obj.icon_image)
        val unwrappedDrawable =
            ContextCompat.getDrawable(viewHolder.itemView.context, R.drawable.shape_grey_rounded_10dp)
        val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable!!)
        DrawableCompat.setTint(wrappedDrawable, Color.parseColor(obj.color))


        viewHolder.itemView.ivIconBg.background = wrappedDrawable
        viewHolder.itemView.ivIcon.setColorFilter(
            Color.WHITE,
            android.graphics.PorterDuff.Mode.SRC_IN
        )
        percentage?.let {
            val shortPercentage =
                if (it.toString().length > 4) it.toString().substring(0, 4) else it.toString()
            viewHolder.itemView.tvPercentage.text = "$shortPercentage %"
        } ?: run {
            viewHolder.itemView.tvPercentage.text = "0 %"
        }
        expense?.let {

            viewHolder.itemView.tvCategoryTotalExpense.text = expense.toString() + " UZS"
        } ?: run {
            viewHolder.itemView.tvCategoryTotalExpense.text = "0 UZS"
        }
    }

    override fun getLayout() = R.layout.item_category_small

}
