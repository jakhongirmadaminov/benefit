package com.example.benefit.ui.partner_home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.benefit.R
import com.example.benefit.remote.models.PartnerCategoryDTO
import com.example.benefit.ui.partner_story.StoryActivity
import com.example.benefit.util.loadImageUrl
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_partner_home.*
import splitties.activities.start
import java.util.ArrayList

class PartnerHomeActivity : AppCompatActivity() {

    private val adapter = GroupAdapter<GroupieViewHolder>()
    lateinit var partner: PartnerCategoryDTO

    companion object {

        const val EXTRA_PARTNER = "PARTNER"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_partner_home)
        partner = intent.getParcelableExtra(EXTRA_PARTNER)!!
        setupViews()
        attachListeners()

    }

    private fun setupViews() {
        imageViewCollapsing.loadImageUrl(partner.image)
        ivBrandLogo.loadImageUrl(partner.icon_image)
        tvBrandName.text = partner.title_ru

    }

    private fun attachListeners() {
        ivBrandLogo.setOnClickListener {
            start<StoryActivity> {
                putExtra(StoryActivity.EXTRA_PARTNER, partner)
            }
        }

        ivBack.setOnClickListener {
            finish()
        }

    }


}