package com.example.benefit.ui.viewgroups

import com.example.benefit.R
import com.example.benefit.stories.data.StoryUser
import com.example.benefit.util.loadImageUrl
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_news.view.*


class ItemStory(val storyUser: StoryUser, val onClick: (StoryUser) -> Unit) : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.apply {
            ivImage.loadImageUrl(storyUser.profilePicUrl)
            card.setOnClickListener {
                onClick(storyUser)
            }
        }
    }

    override fun getLayout() = R.layout.item_news

}
