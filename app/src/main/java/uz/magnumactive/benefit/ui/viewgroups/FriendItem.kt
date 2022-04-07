package uz.magnumactive.benefit.ui.viewgroups

import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_contact_with_cb.view.*
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.remote.models.BenefitContactDTO
import uz.magnumactive.benefit.util.Constants
import uz.magnumactive.benefit.util.loadImageUrl

class FriendItem(var friend: BenefitContactDTO, var onCheckChanged: () -> Unit) : Item() {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {

        viewHolder.itemView.tvNameLastName.text = friend.fullname
        viewHolder.itemView.checkBox.isChecked = friend.isChecked
        viewHolder.itemView.photo.loadImageUrl(
            Constants.BASE_URL + friend.avatar_link?.removeSuffix("/")
        )

        viewHolder.itemView.llContact.setOnClickListener {
            viewHolder.itemView.checkBox.toggle()
            friend.isChecked = viewHolder.itemView.checkBox.isChecked
            onCheckChanged()
        }

    }

    override fun getLayout() = R.layout.item_contact_with_cb

}
