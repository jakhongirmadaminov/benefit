package uz.magnumactive.benefit.ui.viewgroups

import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.remote.models.CardBgDTO
import uz.magnumactive.benefit.util.loadImageUrl
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_card_design.*

class ItemCardDesign(val obj: CardBgDTO, val onClick: () -> Unit) : Item() {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

        viewHolder.ivCardPattern.loadImageUrl(obj.image)
        viewHolder.cardDesignItem.setOnClickListener {
            onClick()
        }

    }

    override fun getLayout() = R.layout.item_card_design

}
