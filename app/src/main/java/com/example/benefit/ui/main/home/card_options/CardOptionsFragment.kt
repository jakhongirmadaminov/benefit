package com.example.benefit.ui.main.home.card_options

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.benefit.R
import com.example.benefit.ui.main.home.fill_card.FillCardBSD
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_card_options.*
import javax.inject.Inject

/**
 * Created by jahon on 03-Sep-20
 */
@AndroidEntryPoint
class CardOptionsFragment @Inject constructor() : Fragment(R.layout.fragment_card_options) {


    private val viewModel: CardOptionsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupViews()

        attachListeners()
        subscribeObservers()
    }

    private fun setupViews() {
        cardParent.setBackgroundResource(R.drawable.shape_top_rounded)
    }

    private fun subscribeObservers() {


    }

    private fun attachListeners() {
        llBlockCard.setOnClickListener {
            DialogBlockCard().show(childFragmentManager, "")
        }
        llHistory.setOnClickListener {
        }
        llMakeDeposit.setOnClickListener {
            ((parentFragment as NavHostFragment).parentFragment as CardOptionsBSD).dismiss()
            FillCardBSD().show(
                requireParentFragment().requireParentFragment()
                    .requireParentFragment().childFragmentManager,
                ""
            )
        }
        llNotifications.setOnClickListener {
            findNavController().navigate(R.id.action_cardOptionsFragment_to_cardNotificationsFragment)
        }
        llChangeCardDesign.setOnClickListener {
            findNavController().navigate(R.id.action_cardOptionsFragment_to_cardChangeDesignFragment)
        }

    }

}