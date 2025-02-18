package uz.magnumactive.benefit.ui.viewgroups

import android.graphics.Color
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.remote.models.BenefitContactDTO
import uz.magnumactive.benefit.util.Constants
import uz.magnumactive.benefit.util.loadImageUrlAndShrink
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_contact_with_cb.view.*

class BenefitFriendItem(var friend: BenefitContactDTO, var onCheckChanged: (Boolean) -> Unit) :
    Item() {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

        viewHolder.itemView.apply {
            friend.avatar_link?.let {
                photo.loadImageUrlAndShrink(Constants.BASE_URL + it.removeSuffix("/"))
            } ?: run {
                photo.setColorFilter(Color.BLACK)
                photo.setImageResource(R.drawable.ic_baseline_person_24)
            }
            tvNameLastName.text = friend.fullname
            checkBox.isChecked = friend.isChecked

            llContact.setOnClickListener {
                checkBox.toggle()
                friend.isChecked = checkBox.isChecked
                onCheckChanged(friend.isChecked)
            }
        }

    }

    override fun getLayout() = R.layout.item_contact_with_cb

}
