package uz.magnumactive.benefit.ui.viewgroups

import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_centered_text_grey.view.*
import uz.magnumactive.benefit.R

class ItemCenteredTextGrey(val text: String) : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

        viewHolder.itemView.apply {
            tvCenteredText.text = text
        }
    }

    override fun getLayout() = R.layout.item_centered_text_grey

}

