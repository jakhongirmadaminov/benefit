package com.example.benefit.ui.main.home.card_options

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.benefit.R
import com.example.benefit.ui.main.home.HomeFragment
import com.example.benefit.util.SizeUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_card_change_design.*
import javax.inject.Inject

/**
 * Created by jahon on 03-Sep-20
 */
@AndroidEntryPoint
class CardChangeDesignFragment @Inject constructor() :
    Fragment(R.layout.fragment_card_change_design) {


    private val viewModel: CardOptionsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupViews()
        attachListeners()
        subscribeObservers()
    }

    private fun setupViews() {


        val cardView = layoutInflater.inflate(R.layout.item_card, null)
        val cardView2 = layoutInflater.inflate(R.layout.item_card, null)

        cardView.setOnClickListener {
            CardOptionsBSD().show(childFragmentManager, "")
        }

        cardsPager.addView(cardView)
        cardsPager.addView(cardView2)

        cardsPager.adapter = HomeFragment.WizardPagerAdapter(listOf(cardView, cardView2))
        cardsPager.offscreenPageLimit = 2
        cardsPager.clipToPadding = false
        cardsPager.setPadding(
            SizeUtils.dpToPx(requireContext(), 26).toInt(),
            0,
            SizeUtils.dpToPx(requireContext(), 26).toInt(),
            0
        )
        cardsPager.pageMargin = SizeUtils.dpToPx(requireContext(), 15).toInt()


    }

    private fun subscribeObservers() {


    }

    private fun attachListeners() {
        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

    }

}