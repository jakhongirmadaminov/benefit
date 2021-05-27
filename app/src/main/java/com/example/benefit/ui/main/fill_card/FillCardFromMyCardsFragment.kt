package com.example.benefit.ui.main.fill_card

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.benefit.R
import com.example.benefit.ui.main.home.HomeFragment
import com.example.benefit.ui.main.home.card_options.CardOptionsViewModel
import com.example.benefit.util.SizeUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_fill_from_my_cards.*
import javax.inject.Inject

/**
 * Created by jahon on 03-Sep-20
 */
import com.example.benefit.ui.base.BaseFragment

class FillCardFromMyCardsFragment : BaseFragment(R.layout.fragment_fill_from_my_cards) {


    private val viewModel: CardOptionsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupViews()
        attachListeners()
        subscribeObservers()
    }

    private fun setupViews() {

        val cardView = layoutInflater.inflate(R.layout.item_card_small, null)
        val cardView2 = layoutInflater.inflate(R.layout.item_card_small, null)

        cardsToPagerSmall.addView(cardView)
        cardsToPagerSmall.addView(cardView2)

        cardsToPagerSmall.adapter = HomeFragment.WizardPagerAdapter(listOf(cardView, cardView2))
        cardsToPagerSmall.offscreenPageLimit = 2
        cardsToPagerSmall.clipToPadding = false
        cardsToPagerSmall.setPadding(
            SizeUtils.dpToPx(requireContext(), 26).toInt(),
            0,
            SizeUtils.dpToPx(requireContext(), 26).toInt(),
            0
        )
        cardsToPagerSmall.pageMargin = SizeUtils.dpToPx(requireContext(), 15).toInt()



        val cardView3 = layoutInflater.inflate(R.layout.item_card_small, null)
        val cardView4 = layoutInflater.inflate(R.layout.item_card_small, null)

        cardsFromPagerSmall.addView(cardView3)
        cardsFromPagerSmall.addView(cardView4)

        cardsFromPagerSmall.adapter = HomeFragment.WizardPagerAdapter(listOf(cardView3, cardView4))
        cardsFromPagerSmall.offscreenPageLimit = 2
        cardsFromPagerSmall.clipToPadding = false
        cardsFromPagerSmall.setPadding(
            SizeUtils.dpToPx(requireContext(), 26).toInt(),
            0,
            SizeUtils.dpToPx(requireContext(), 26).toInt(),
            0
        )
        cardsFromPagerSmall.pageMargin = SizeUtils.dpToPx(requireContext(), 15).toInt()



    }

    private fun subscribeObservers() {


    }

    private fun attachListeners() {
        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

    }

}