package com.example.benefit.ui.viewgroups

import com.example.benefit.R
import com.example.benefit.remote.models.NewsDTO
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item

class ItemNews(val news: NewsDTO) : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
//viewHolder.ivImage.loadImageUrl(news.)
    }

    override fun getLayout() = R.layout.item_news

}
