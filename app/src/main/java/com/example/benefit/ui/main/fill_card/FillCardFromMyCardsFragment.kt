package com.example.benefit.ui.main.fill_card

/**
 * Created by jahon on 03-Sep-20
 */
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager.widget.ViewPager
import com.example.benefit.R
import com.example.benefit.ui.base.BaseFragment
import com.example.benefit.ui.main.home.HomeFragment
import com.example.benefit.ui.main.home.card_options.CardOptionsViewModel
import com.example.benefit.util.SizeUtils
import kotlinx.android.synthetic.main.fragment_fill_from_my_cards.*
import kotlinx.android.synthetic.main.item_card_small.view.*
import java.text.DecimalFormat

class FillCardFromMyCardsFragment : BaseFragment(R.layout.fragment_fill_from_my_cards) {


    private val viewModel: CardOptionsViewModel by viewModels()

    val navArgs: FillCardFromMyCardsFragmentArgs by navArgs()

    var cardFromIndex = 0
    var cardToIndex = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        attachListeners()
        subscribeObservers()
    }

    private fun setupViews() {

        val cardViews = navArgs.cards?.map {
            val cardView = layoutInflater.inflate(R.layout.item_card_small, null)
            cardView.cardName.text = it.card_title
            cardView.tvAmount.text =
                DecimalFormat("#,###").format(it.balance?.dropLast(2)?.toInt()) + " UZS"
            cardView.tvCardEndNum.text = "*" + it.pan!!.substring(it.pan!!.length - 4)
            cardsToPagerSmall.addView(cardView)
            cardView
        }

        cardsToPagerSmall.adapter = HomeFragment.WizardPagerAdapter(cardViews!!)
        cardsToPagerSmall.offscreenPageLimit = 2
        cardsToPagerSmall.clipToPadding = false
        cardsToPagerSmall.setPadding(
            SizeUtils.dpToPx(requireContext(), 26).toInt(),
            0,
            SizeUtils.dpToPx(requireContext(), 26).toInt(),
            0
        )
        cardsToPagerSmall.pageMargin = SizeUtils.dpToPx(requireContext(), 15).toInt()
        cardToIndex = navArgs.cards!!.indexOf(navArgs.card!!)
        cardsToPagerSmall.currentItem = cardToIndex

        cardsToPagerSmall.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                if (cardFromIndex == position) {
                    navArgs.cards!!.forEachIndexed { index, cardDTO ->
                        if (position != index) {
                            cardFromIndex = index
                            return@forEachIndexed
                        }
                    }
                    cardsFromPagerSmall.currentItem = cardFromIndex
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })

        val cardViews2 = navArgs.cards?.map {
            val cardView = layoutInflater.inflate(R.layout.item_card_small, null)
            cardView.cardName.text = it.card_title
            cardView.tvAmount.text =
                DecimalFormat("#,###").format(it.balance?.dropLast(2)?.toInt()) + " UZS"
            cardView.tvCardEndNum.text = "*" + it.pan!!.substring(it.pan!!.length - 4)
            cardsFromPagerSmall.addView(cardView)
            cardView
        }

        cardsFromPagerSmall.adapter = HomeFragment.WizardPagerAdapter(cardViews2!!)
        cardsFromPagerSmall.offscreenPageLimit = 2
        cardsFromPagerSmall.clipToPadding = false
        cardsFromPagerSmall.setPadding(
            SizeUtils.dpToPx(requireContext(), 26).toInt(),
            0,
            SizeUtils.dpToPx(requireContext(), 26).toInt(),
            0
        )
        cardsFromPagerSmall.pageMargin = SizeUtils.dpToPx(requireContext(), 15).toInt()

        navArgs.cards!!.forEachIndexed { index, cardDTO ->
            if (cardDTO != navArgs.card) {
                cardFromIndex = index
                return@forEachIndexed
            }
        }
        cardsFromPagerSmall.currentItem = cardFromIndex

        cardsFromPagerSmall.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                if (cardToIndex == position) {
                    navArgs.cards!!.forEachIndexed { index, cardDTO ->
                        if (position != index) {
                            cardToIndex = index
                            return@forEachIndexed
                        }
                    }
                    cardsToPagerSmall.currentItem = cardToIndex
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })

    }

    private fun subscribeObservers() {


    }

    private fun attachListeners() {
        ivBack.setOnClickListener {
            (parentFragment as NavHostFragment).requireParentFragment()!!.findNavController()
                .popBackStack()
        }

    }

}