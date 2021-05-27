package com.example.benefit.ui.partner_home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.benefit.R
import com.example.benefit.remote.models.Partner
import com.example.benefit.ui.base.BaseActivity
import com.example.benefit.ui.partner_story.StoryActivity
import com.example.benefit.util.loadImageUrl
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_partner_home.*
import splitties.activities.start


class PartnerHomeActivity : BaseActivity() {

    private val adapter = GroupAdapter<GroupieViewHolder>()
    lateinit var partner: Partner


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
        imageViewCollapsing.loadImageUrl(partner.image!!)
        ivBrandLogo.loadImageUrl(partner.icon_image!!)
        tvBrandName.text = partner.title
        tvContentDesc.text = partner.desc_ru
        tvContentTitle.text = partner.title
        tvBrandSubtitle.text = partner.type_name
//        tvCashBackPercentage.text = partner.title

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

        ivCall.setOnClickListener {
            startActivity(Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:${partner.phone}")
            })
        }

    }


}