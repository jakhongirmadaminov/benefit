package com.example.benefit.ui.partner_story

import android.os.Bundle
import com.example.benefit.R
import com.example.benefit.remote.models.Partner
import com.example.benefit.ui.base.BaseActivity
import com.example.benefit.util.loadImageUrl
import jp.shts.android.storiesprogressview.StoriesProgressView.StoriesListener
import kotlinx.android.synthetic.main.activity_story.*


class StoryActivity : BaseActivity(), StoriesListener {

    companion object {

        const val EXTRA_PARTNER = "PARTNER"
    }

    lateinit var partner: Partner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story)

        partner = intent.getParcelableExtra(EXTRA_PARTNER)!!

        setupViews()

        attachListeners()
    }

    private fun setupViews() {
        image.loadImageUrl(partner.image!!)
        storiesProgressView!!.setStoriesCount(5) // <- set stories
        storiesProgressView!!.setStoryDuration(1200L) // <- set a story duration
        storiesProgressView!!.setStoriesListener(this) // <- set listener
        storiesProgressView!!.startStories() // <- start progress
        ivBrandLogo.loadImageUrl(partner.icon_image!!)
        tvBrand.text = partner.title
    }

    private fun attachListeners() {
        icClose.setOnClickListener {
            finish()
        }
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