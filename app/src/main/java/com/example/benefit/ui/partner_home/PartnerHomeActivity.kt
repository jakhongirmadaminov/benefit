package com.example.benefit.ui.partner_home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.core.view.children
import androidx.lifecycle.lifecycleScope
import com.example.benefit.R
import com.example.benefit.remote.models.Partner
import com.example.benefit.remote.models.PartnerPhotoDTO
import com.example.benefit.stories.screen.EXTRA_STORIES
import com.example.benefit.stories.screen.StoryActivity
import com.example.benefit.ui.base.BaseActivity
import com.example.benefit.ui.main.home.HomeFragment
import com.example.benefit.ui.partners_map.PartnerOnMapActivity
import com.example.benefit.util.SizeUtils
import com.example.benefit.util.loadCircularImage
import com.example.benefit.util.loadImageUrl
import kotlinx.android.synthetic.main.activity_partner_home.*
import kotlinx.android.synthetic.main.item_partner_image.view.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import splitties.activities.start


class PartnerHomeActivity : BaseActivity() {

    lateinit var partner: Partner

    private val viewModel by viewModels<PartnerHomeViewModel>()

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

        setupImagePager(partner.photos_array)
        ivBrandLogo.loadImageUrl(partner.icon_image!!)
        tvBrandName.text = partner.title
        tvContentDesc.text = partner.desc_ru
        tvContentTitle.text = partner.title
        tvBrandSubtitle.text = partner.type_name
//        tvCashBackPercentage.text = partner.title

        tvLikeCount.text = "+" + partner.likes_count?.toString()

        addLikedUserAvatars()


        viewModel.partnerStoriesFlow(partnerId = partner.id).onEach { stories ->
            cardImg.setOnClickListener {
//                start<StoryActivity> {
//                    putExtra(EXTRA_STORIES, stories)
//                }

            }
        }.launchIn(lifecycleScope)

    }

    private fun addLikedUserAvatars() {
        var avatarCount = 0
        partner.last_likes?.forEach {
            it.user_image?.let { image ->
                likedPplContainer.addView(ImageView(this).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        SizeUtils.dpToPx(context, 40).toInt(),
                        SizeUtils.dpToPx(context, 40).toInt()
                    ).apply {
                        marginStart = -SizeUtils.dpToPx(context, 20).toInt()
                    }
                    scaleType = ImageView.ScaleType.CENTER_CROP
                    loadCircularImage(image.removeSuffix("/"), 2f)
                })

            }

            avatarCount++
            if (avatarCount == 3) return@forEach
        }
        likedPplContainer.requestLayout()
    }


    private fun setupImagePager(photos: List<PartnerPhotoDTO>?) {
        imagesPager.adapter = null
        imagesPager.removeAllViews()

        photos?.forEach {
            it.image?.let { image ->
                val chartView = layoutInflater.inflate(R.layout.item_partner_image, null)
                chartView.ivPartnerImage.loadImageUrl(image)
                imagesPager.addView(chartView)
            }
        }

        imagesPager.adapter = HomeFragment.WizardPagerAdapter(imagesPager.children.toList())
        imagesPager.offscreenPageLimit = 10
        imagesPager.currentItem = 0
    }


    private fun attachListeners() {
        ivBack.setOnClickListener {
            finish()
        }

        ivCall.setOnClickListener {
            startActivity(Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:${partner.phone}")
            })
        }

        cbLike.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.sendLikeOrDislike(isChecked, partnerId = partner.id)
        }

        ivMap.setOnClickListener {
            partner.coords_array?.lat?.let {
                start<PartnerOnMapActivity> {
                    putExtra(EXTRA_PARTNER, partner)
                }
            }
        }

    }


}