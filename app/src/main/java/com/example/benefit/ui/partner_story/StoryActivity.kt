package com.example.benefit.ui.partner_story

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.benefit.R
import com.example.benefit.remote.models.PartnerCategoryDTO
import com.example.benefit.util.loadImageUrl
import jp.shts.android.storiesprogressview.StoriesProgressView
import jp.shts.android.storiesprogressview.StoriesProgressView.StoriesListener
import kotlinx.android.synthetic.main.activity_story.*


class StoryActivity : AppCompatActivity(), StoriesListener {

    companion object {

        const val EXTRA_PARTNER = "PARTNER"
    }

    lateinit var partner: PartnerCategoryDTO

    private var storiesProgressView: StoriesProgressView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story)

        partner = intent.getParcelableExtra(EXTRA_PARTNER)!!
        image.loadImageUrl(partner.image)

        storiesProgressView!!.setStoriesCount(5) // <- set stories
        storiesProgressView!!.setStoryDuration(1200L) // <- set a story duration
        storiesProgressView!!.setStoriesListener(this) // <- set listener
        storiesProgressView!!.startStories() // <- start progress
    }

    override fun onNext() {
//        Toast.makeText(this, "onNext", Toast.LENGTH_SHORT).show()
    }

    override fun onPrev() {
        // Call when finished revserse animation.
//        Toast.makeText(this, "onPrev", Toast.LENGTH_SHORT).show()
    }

    override fun onComplete() {
//        Toast.makeText(this, "onComplete", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        // Very important !
        storiesProgressView!!.destroy()
        super.onDestroy()
    }
}