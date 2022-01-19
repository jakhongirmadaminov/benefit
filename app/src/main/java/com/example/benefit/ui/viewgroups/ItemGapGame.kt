package com.example.benefit.ui.viewgroups

import android.widget.ImageView
import android.widget.LinearLayout
import com.example.benefit.R
import com.example.benefit.remote.models.GapGameDTO
import com.example.benefit.util.Constants
import com.example.benefit.util.SizeUtils.dpToPx
import com.example.benefit.util.loadCircularImage
import com.example.benefit.util.loadDrawableCircleCrop
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_gap_game.view.*

data class ItemGapGame(val it: GapGameDTO, val onClick: () -> Unit) : Item() {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.apply {
            lblCardTitle.text = it.name
            tvPlayerCount.text = context.getString(R.string.count) + ": " + it.members!!.size
            lblOne.text = context.getString(R.string.from_each_player_by_sum, it.summa)
//            lblTwo.text =context.getString(R.string.start_from, it.)
//            lblThree.text =
            llAvatars.removeAllViews()
            it.members.forEach {
                llAvatars.addView(ImageView(context).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        dpToPx(context, 40).toInt(),
                        dpToPx(context, 40).toInt()
                    ).apply {
                        marginStart = -dpToPx(context, 20).toInt()
                    }

                    scaleType = ImageView.ScaleType.CENTER_CROP
                    it.userInfo?.avatarLink?.let { avatar ->
                        if (avatar.endsWith(".png") || avatar.endsWith(".jpg")) {
                            loadCircularImage(Constants.BASE_URL + avatar.removeSuffix("/"), 2f)
                        } else {
                            loadDrawableCircleCrop(R.drawable.ic_avatar_sample)
                        }
                    }
                })
            }
            llAvatars.requestLayout()
            clParent.setOnClickListener { onClick() }
        }
    }

    override fun getLayout() = R.layout.item_gap_game
}
