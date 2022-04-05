package uz.magnumactive.benefit.ui.partner_home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.core.view.children
import androidx.lifecycle.lifecycleScope
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.remote.models.PartnerPhotoDTO
import uz.magnumactive.benefit.stories.screen.PageChangeListener
import uz.magnumactive.benefit.ui.base.BaseActivity
import uz.magnumactive.benefit.ui.main.home.HomeFragment
import uz.magnumactive.benefit.ui.partners_map.PartnerOnMapActivity
import uz.magnumactive.benefit.util.SizeUtils
import uz.magnumactive.benefit.util.loadCircularImage
import uz.magnumactive.benefit.util.loadImageUrl
import kotlinx.android.synthetic.main.activity_partner_home.*
import kotlinx.android.synthetic.main.item_partner_image.view.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import splitties.activities.start


class PartnerHomeActivity : BaseActivity() {


    private val viewModel by viewModels<PartnerHomeViewModel>()

    companion object {
        const val EXTRA_PARTNER = "PARTNER"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_partner_home)
        viewModel.partner.value = intent.getParcelableExtra(EXTRA_PARTNER)!!
        attachListeners()
        subscribeObservers()

    }

    private fun subscribeObservers() {
        viewModel.partner.observe(this) {
            setupViews()
        }
    }

    private fun setupViews() {

        setupImagePager(viewModel.partner.value?.photos_array)
        ivBrandLogo.loadImageUrl(viewModel.partner.value?.icon_image!!)
        tvBrandName.text = viewModel.partner.value?.title
        tvContentDesc.text = viewModel.partner.value?.desc_ru
        tvContentTitle.text = viewModel.partner.value?.title
        tvBrandSubtitle.text = viewModel.partner.value?.type_name
//        tvCashBackPercentage.text = viewModel.partner.value?.title

        tvLikeCount.text = "+" + viewModel.partner.value?.likes_count?.toString()

        addLikedUserAvatars()


        viewModel.partnerStoriesFlow(partnerId = viewModel.partner.value!!.id).onEach { stories ->
            cardImg.setOnClickListener {
//                start<StoryActivity> {
//                    putExtra(EXTRA_STORIES, stories)
//                }

            }
        }.launchIn(lifecycleScope)

    }

    private fun addLikedUserAvatars() {
        var avatarCount = 0
        likedPplContainer.removeAllViews()
        viewModel.partner.value?.last_likes?.forEach {
            it.user_image?.let { image ->
                likedPplContainer.addView(ImageView(this).apply {
                    layoutParams = LinearLayout.LayoutParams(
                        SizeUtils.dpToPx(context, 30).toInt(),
                        SizeUtils.dpToPx(context, 30).toInt()
                    )/*.apply {
                        marginStart = -SizeUtils.dpToPx(context, 20).toInt()
                    }*/
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
        tvImageCount.text = "1/${imagesPager.childCount}"

        imagesPager.addOnPageChangeListener(
            object : PageChangeListener() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    tvImageCount.text = "${position + 1}/${imagesPager.childCount}"
                }

                override fun onPageScrollCanceled() {
                }

            }
        )
    }


    private fun attachListeners() {
        ivBack.setOnClickListener {
            finish()
        }

        ivCall.setOnClickListener {
            startActivity(Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:${viewModel.partner.value?.phone}")
            })
        }

        cbLike.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.sendLikeOrDislike(isChecked, partnerId = viewModel.partner.value!!.id)
        }

        ivMap.setOnClickListener {
            viewModel.partner.value?.coords_array?.lat?.let {
                start<PartnerOnMapActivity> {
                    putExtra(EXTRA_PARTNER, viewModel.partner.value)
                }
            }
        }

    }


}