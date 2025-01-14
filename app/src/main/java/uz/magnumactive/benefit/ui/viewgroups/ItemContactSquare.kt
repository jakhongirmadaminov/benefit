package uz.magnumactive.benefit.ui.viewgroups

import android.graphics.Color
import androidx.core.view.isVisible
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.remote.models.BenefitContactDTO
import uz.magnumactive.benefit.util.Constants.BASE_URL
import uz.magnumactive.benefit.util.loadImageUrlAndShrink
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_contact_square.view.*
import kotlinx.android.synthetic.main.item_contact_with_cb.view.*

data class ItemContactSquare(
    val contact: BenefitContactDTO? = null,
    var onClick: (() -> Unit)?,
    var onRemove: ((Item) -> Unit)?
) :
    Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

        viewHolder.itemView.apply {
            contact?.let { friendDTO ->
                ivImage.isVisible = true
                friendDTO.avatar_link?.let {
                    ivImage.loadImageUrlAndShrink(BASE_URL + it.removeSuffix("/"))
                } ?: run {
                    photo.setColorFilter(Color.BLACK)
                    ivImage.setImageResource(R.drawable.ic_baseline_person_24)
                }
                ivDelete.isVisible = true
                ivDelete.setOnClickListener {
                    onRemove?.invoke(this@ItemContactSquare)
                }
            } ?: run {
                ivDelete.isVisible = false
                ivImage.isVisible = false
                llAdd.setOnClickListener {
                    onClick?.invoke()
                }
            }


        }

    }

    override fun getLayout() = R.layout.item_contact_square
}
