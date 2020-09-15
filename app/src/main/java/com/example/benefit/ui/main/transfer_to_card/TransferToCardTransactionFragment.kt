package com.example.benefit.ui.main.transfer_to_card

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.benefit.R
import com.example.benefit.ui.branches_atms.BranchesAtmsActivity
import com.example.benefit.ui.main.home.HomeFragment
import com.example.benefit.ui.main.home.card_options.CardOptionsBSD
import com.example.benefit.util.SizeUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_transfer_to_card_transaction.*
import splitties.fragments.start
import javax.inject.Inject


/**
 * Created by jahon on 03-Sep-20
 */
@AndroidEntryPoint
class TransferToCardTransactionFragment @Inject constructor() : Fragment(R.layout.fragment_transfer_to_card_transaction) {


    private val viewModel: TransferToCardViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupViews()

        attachListeners()
        subscribeObservers()
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


    private fun subscribeObservers() {


    }

    private fun attachListeners() {

//        llAskFriends.setOnClickListener {
//            findNavController().navigate(R.id.action_fillCardFragment_to_cardDepositAskFriendsFragment)
//        }
//        llWithCash.setOnClickListener {
//            start<BranchesAtmsActivity> {}
//        }
//        llFromAnyCard.setOnClickListener {
//            findNavController().navigate(R.id.action_fillCardFragment_to_cardMakeDepositFromAnyCardFragment)
//        }
//        llFromOwnCards.setOnClickListener {
//            findNavController().navigate(R.id.action_fillCardFragment_to_cardMakeDepositFromMyCardFragment)
//
//        }

    }

}