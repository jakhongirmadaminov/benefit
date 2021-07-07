package com.example.benefit.ui.viewgroups

import android.content.Intent
import android.net.Uri
import com.example.benefit.R
import com.example.benefit.remote.models.NewsDTO
import com.example.benefit.util.loadImageUrl
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_news.view.*


class ItemNews(val news: NewsDTO) : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.apply {
            ivImage.loadImageUrl(news.image)
            card.setOnClickListener {
                val url = if (!news.url_link.startsWith("http://")
                    && !news.url_link.startsWith("https://")
                )
                    "http://" + news.url_link else news.url_link
                it.context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            }
        }
    }

    override fun getLayout() = R.layout.item_news

}
