package uz.magnumactive.benefit.ui.viewgroups

import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.remote.models.FriendDTO
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_contact_img_name.view.*

class FriendItemImgName(var friend: FriendDTO) : Item() {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.tvName.text = friend.name
    }

    override fun getLayout() = R.layout.item_contact_img_name

}
