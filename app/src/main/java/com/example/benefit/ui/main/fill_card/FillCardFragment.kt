package com.example.benefit.ui.main.fill_card

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.benefit.R
import com.example.benefit.remote.models.CardDTO
import com.example.benefit.ui.branches_atms.BranchesAtmsActivity
import com.example.benefit.ui.main.home.HomeFragment
import com.example.benefit.ui.main.home.card_options.CardOptionsBSD
import com.example.benefit.util.SizeUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_fill_card.*
import splitties.fragments.start
import javax.inject.Inject

/**
 * Created by jahon on 03-Sep-20
 */
@AndroidEntryPoint
class FillCardFragment @Inject constructor() : Fragment(R.layout.fragment_fill_card) {

    companion object {
        const val ARG_CARD = "CARD"
        const val ARG_CARDS = "CARDS"
    }


    private val viewModel: FillCardViewModel by viewModels()
    lateinit var cardBeingFilled: CardDTO
    lateinit var selectableCards: List<CardDTO>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        cardBeingFilled = requireArguments().getParcelable(ARG_CARD)!!
        selectableCards = requireArguments().getParcelableArrayList(ARG_CARDS)!!
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupViews()

        attachListeners()
        subscribeObservers()
    }

    private fun setupViews() {


        val cardView = layoutInflater.inflate(R.layout.item_card_small, null)

        cardView.setOnClickListener {
            CardOptionsBSD().show(childFragmentManager, "")
        }

        cardsPager.addView(cardView)
        val cardView2 = layoutInflater.inflate(R.layout.item_card_small, null)
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

        llAskFriends.setOnClickListener {
            findNavController().navigate(R.id.action_fillCardFragment_to_cardDepositAskFriendsFragment)
        }
        llWithCash.setOnClickListener {
            start<BranchesAtmsActivity> {}
        }
        llFromAnyCard.setOnClickListener {
            findNavController().navigate(R.id.action_fillCardFragment_to_cardMakeDepositFromAnyCardFragment)
        }
        llFromOwnCards.setOnClickListener {
            findNavController().navigate(R.id.action_fillCardFragment_to_cardMakeDepositFromMyCardFragment)

        }

    }

}