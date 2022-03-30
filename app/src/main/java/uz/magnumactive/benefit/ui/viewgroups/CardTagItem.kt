package uz.magnumactive.benefit.ui.viewgroups

import android.graphics.Color
import androidx.core.content.ContextCompat
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.remote.models.CardDTO
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_card_tag.view.*

class CardTagItem(val cardDTO: CardDTO, val onClick: (item: CardTagItem, cardDto:CardDTO) -> Unit) : Item() {

    var selected = false

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.tvCardTitle.text = cardDTO.card_title

        if (selected) {
            viewHolder.itemView.tvCardTitle.setBackgroundResource(R.drawable.selector_orange)
            viewHolder.itemView.tvCardTitle.setTextColor(Color.WHITE)
        } else {
            viewHolder.itemView.tvCardTitle.setBackgroundResource(R.drawable.selector_grey_rounded)
            viewHolder.itemView.tvCardTitle.setTextColor(
                ContextCompat.getColor(
                    viewHolder.itemView.context,
                    R.color.grey_text
                )
            )
        }

        viewHolder.itemView.tvCardTitle.setOnClickListener {
            onClick(this,cardDTO)
        }

    }

    override fun getLayout() = R.layout.item_card_tag

}
