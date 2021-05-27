package com.example.benefit.ui.select_card_type

import android.content.Intent
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.benefit.R
import com.example.benefit.enums.ECardType
import com.example.benefit.ui.base.BaseActivity
import com.example.benefit.ui.main.home.HomeFragment
import com.example.benefit.ui.order_card.OrderCardActivity
import com.example.benefit.ui.order_card.OrderCardActivity.Companion.EXTRA_CARD_TYPE
import com.example.benefit.ui.order_card.OrderCardActivity.Companion.REQ_ORDER_CARD
import kotlinx.android.synthetic.main.activity_select_card_type.*


class SelectCardTypeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_card_type)


        setupViews()
        attachListeners()
        subscribeObservers()

    }

    private fun setupViews() {
        setupCardTypePager()
    }

    private fun setupCardTypePager() {

        cardTypesPager.adapter =
            HomeFragment.WizardPagerAdapter(listOf(page_one, page_two))
        cardTypesPager.offscreenPageLimit = 2
    }

    var cardType = ECardType.ZOOM

    private fun attachListeners() {
        cardTypesPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                cardType = if (position == 0) ECardType.ZOOM else ECardType.SUPREME
            }

            override fun onPageScrollStateChanged(state: Int) {
            }

        })

        btnOrder.setOnClickListener {
            startActivityForResult(Intent(this, OrderCardActivity::class.java).apply {
                putExtra(EXTRA_CARD_TYPE, cardType)
            }, REQ_ORDER_CARD)
        }

        ivBack.setOnClickListener {
            onBackPressed()
        }

    }

    private fun subscribeObservers() {


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            if (requestCode == REQ_ORDER_CARD) {
                setResult(RESULT_OK)
                finish()
            }
        }
    }
}
