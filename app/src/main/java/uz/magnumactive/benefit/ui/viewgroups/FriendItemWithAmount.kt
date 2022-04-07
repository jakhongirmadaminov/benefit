package uz.magnumactive.benefit.ui.viewgroups

import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_contact_with_share_amount.view.*
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.remote.models.BenefitContactDTO
import uz.magnumactive.benefit.util.AppPrefs
import uz.magnumactive.benefit.util.Constants
import uz.magnumactive.benefit.util.loadImageUrl

class FriendItemWithAmount(
    var friend: BenefitContactDTO,
    var onAmountChanged: () -> Unit
) : Item() {

    var edtAmount: EditText? = null

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        edtAmount = viewHolder.itemView.edtAmount
        edtAmount?.setText(friend.payingAmount?.toString() ?: "")
        edtAmount?.doOnTextChanged { text, start, before, count ->
            friend.payingAmount = text.toString().toIntOrNull() ?: 0
            onAmountChanged()
        }
        viewHolder.itemView.tvNameLastName.text = friend.fullname
        if (friend.isMe) {
            viewHolder.itemView.cardPhoto.setBackgroundColor(
                ContextCompat.getColor(
                    viewHolder.itemView.context,
                    R.color.colorAccent
                )
            )
            viewHolder.itemView.photo.loadImageUrl(AppPrefs.avatar!!)
        } else {
            viewHolder.itemView.cardPhoto.setBackgroundColor(
                ContextCompat.getColor(
                    viewHolder.itemView.context,
                    R.color.white
                )
            )
            viewHolder.itemView.photo.loadImageUrl(
                Constants.BASE_URL + friend.avatar_link?.removeSuffix("/")
            )
        }
    }

    override fun unbind(viewHolder: GroupieViewHolder) {
        super.unbind(viewHolder)
        edtAmount = null
    }

    override fun getLayout() = R.layout.item_contact_with_share_amount

    fun setAmount(equalizedAmount: Long) {
        edtAmount?.setText(equalizedAmount.toString())
    }


}
