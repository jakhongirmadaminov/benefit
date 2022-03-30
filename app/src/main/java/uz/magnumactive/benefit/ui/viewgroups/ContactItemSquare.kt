package uz.magnumactive.benefit.ui.viewgroups

import android.net.Uri
import android.view.View
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.remote.models.FriendDTO
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_contact_square.view.*

class ContactItemSquare(val friend: FriendDTO? = null) : Item() {


    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        if (friend != null) {
            viewHolder.itemView.ivDelete.visibility = View.VISIBLE
            viewHolder.itemView.ivImage.visibility = View.VISIBLE
            viewHolder.itemView.llAdd.visibility = View.GONE
            if (friend.photoUri != null) viewHolder.itemView.ivImage.setImageURI(Uri.parse(friend.photoUri!!))
        }else{
            viewHolder.itemView.ivDelete.visibility = View.INVISIBLE
            viewHolder.itemView.llAdd.visibility = View.VISIBLE

        }

    }

    override fun getLayout() = R.layout.item_contact_square


}
