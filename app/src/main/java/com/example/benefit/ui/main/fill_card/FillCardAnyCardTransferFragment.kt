package com.example.benefit.ui.main.fill_card

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.benefit.R
import com.example.benefit.ui.main.home.HomeFragment
import com.example.benefit.ui.main.home.card_options.CardOptionsBSD
import com.example.benefit.ui.main.home.card_options.CardOptionsViewModel
import com.example.benefit.util.SizeUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_fill_from_any_card_transfer.*
import javax.inject.Inject

/**
 * Created by jahon on 03-Sep-20
 */
@AndroidEntryPoint
class FillCardAnyCardTransferFragment @Inject constructor() : Fragment(R.layout.fragment_fill_from_any_card_transfer) {


    private val viewModel: CardOptionsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        attachListeners()
        subscribeObservers()
    }

    private fun subscribeObservers() {


    }

    private fun attachListeners() {
        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    private fun setupViews() {


        val cardView = layoutInflater.inflate(R.layout.item_card_small, null)
        val cardView2 = layoutInflater.inflate(R.layout.item_card_small, null)

        cardView.setOnClickListener {
            CardOptionsBSD().show(childFragmentManager, "")
        }

        cardsPagerSmall.addView(cardView)
        cardsPagerSmall.addView(cardView2)

        cardsPagerSmall.adapter = HomeFragment.WizardPagerAdapter(listOf(cardView, cardView2))
        cardsPagerSmall.offscreenPageLimit = 2
        cardsPagerSmall.clipToPadding = false
        cardsPagerSmall.setPadding(
            SizeUtils.dpToPx(requireContext(), 26).toInt(),
            0,
            SizeUtils.dpToPx(requireContext(), 26).toInt(),
            0
        )
        cardsPagerSmall.pageMargin = SizeUtils.dpToPx(requireContext(), 15).toInt()


    }
    
}