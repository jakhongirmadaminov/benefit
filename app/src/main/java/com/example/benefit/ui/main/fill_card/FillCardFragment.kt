package com.example.benefit.ui.main.fill_card

/**
 * Created by jahon on 03-Sep-20
 */
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.example.benefit.R
import com.example.benefit.remote.models.CardDTO
import com.example.benefit.remote.models.CardsDTO
import com.example.benefit.ui.base.BaseFragment
import com.example.benefit.ui.branches_atms.BranchesAtmsActivity
import com.example.benefit.ui.main.home.HomeFragment
import com.example.benefit.util.SizeUtils
import kotlinx.android.synthetic.main.fragment_fill_card.*
import kotlinx.android.synthetic.main.item_card_small.view.*
import java.text.DecimalFormat

class FillCardFragment : BaseFragment(R.layout.fragment_fill_card) {

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

        llFromOwnCards.isVisible = selectableCards.size > 1

        cardsPager.adapter = null
        val cardViews = selectableCards.map {
            val cardView = layoutInflater.inflate(R.layout.item_card_small, null)
            cardView.cardName.text = it.card_title
            cardView.tvAmount.text =
                DecimalFormat("#,###").format(it.balance?.dropLast(2)?.toInt()) + " UZS"
            cardView.tvCardEndNum.text = "*" + it.pan!!.substring(it.pan!!.length - 4)
            it.setMiniBackgroundInto(cardView.ivCardBg)
            cardsPager.addView(cardView)
            cardView
        }

        cardsPager.adapter = HomeFragment.WizardPagerAdapter(cardViews)

        cardsPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                cardBeingFilled = selectableCards[position]
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })

        cardsPager.offscreenPageLimit = 10
        cardsPager.clipToPadding = false
        cardsPager.setPadding(
            SizeUtils.dpToPx(requireContext(), 26).toInt(),
            0,
            SizeUtils.dpToPx(requireContext(), 26).toInt(),
            0
        )
        cardsPager.pageMargin = SizeUtils.dpToPx(requireContext(), 15).toInt()

        for (i in selectableCards.indices) {
            if (selectableCards[i].own_id!! == cardBeingFilled.own_id!!) {
                cardsPager.currentItem = i
            }
        }

    }

    private fun subscribeObservers() {


    }

    private fun attachListeners() {

        llAskFriends.setOnClickListener {
            findNavController().navigate(R.id.action_fillCardFragment_to_cardDepositAskFriendsFragment)
        }
        llWithCash.setOnClickListener {
            startActivity(Intent(requireActivity(), BranchesAtmsActivity::class.java))
        }
        llFromAnyCard.setOnClickListener {
            val dir =
                FillCardFragmentDirections.actionFillCardFragmentToCardMakeDepositFromAnyCardFragment(
                    CardsDTO(selectableCards),
                    cardBeingFilled
                )
            findNavController().navigate(dir)
        }
        llFromOwnCards.setOnClickListener {
            val dir =
                FillCardFragmentDirections.actionFillCardFragmentToCardMakeDepositFromMyCardFragment(
                    CardsDTO(selectableCards),
                    cardBeingFilled,
                )
            findNavController().navigate(dir)

        }

    }

}