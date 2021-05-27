package com.example.benefit.ui.main.transfer_to_card

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.benefit.R
import com.example.benefit.ui.main.home.card_options.CardOptionsBSD
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_transfer_to_card.*
import kotlinx.android.synthetic.main.fragment_transfer_to_card.ivBack
import javax.inject.Inject


/**
 * Created by jahon on 03-Sep-20
 */
import com.example.benefit.ui.base.BaseFragment

class TransferToCardFragment : BaseFragment(R.layout.fragment_transfer_to_card) {


    private val viewModel: TransferToCardViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupViews()

        attachListeners()
        subscribeObservers()
    }

    private fun setupViews() {


//        val cardView = layoutInflater.inflate(R.layout.item_card_small, null)
//
//        cardView.setOnClickListener {
//            CardOptionsBSD().show(childFragmentManager, "")
//        }
//
//        cardsPager.addView(cardView)
//        val cardView2 = layoutInflater.inflate(R.layout.item_card_small, null)
//        cardsPager.addView(cardView2)
//
//
//        cardsPager.adapter = HomeFragment.WizardPagerAdapter(listOf(cardView, cardView2))
//        cardsPager.offscreenPageLimit = 2
//        cardsPager.clipToPadding = false
//        cardsPager.setPadding(
//            SizeUtils.dpToPx(requireContext(), 26).toInt(),
//            0,
//            SizeUtils.dpToPx(requireContext(), 26).toInt(),
//            0
//        )
//        cardsPager.pageMargin = SizeUtils.dpToPx(requireContext(), 15).toInt()


    }

    private fun subscribeObservers() {


    }

    private fun attachListeners() {

        ivBack.setOnClickListener {
            ((parentFragment as NavHostFragment).parentFragment as TransferToCardBSD).dismiss()
        }



        tvNext.setOnClickListener {
            findNavController().navigate(R.id.action_transferToCardFragment_to_transferToCardTransactionFragment)
        }
//        llWithCash.setOnClickListener {
//            startActivity(Intent(requireActivity(), BranchesAtmsActivity::class.java))
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