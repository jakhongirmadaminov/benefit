package com.example.benefit.ui.main.home.card_options

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.benefit.R
import com.example.benefit.remote.models.CardDTO
import com.example.benefit.remote.models.CardsDTO
import com.example.benefit.ui.DialogLoading
import com.example.benefit.ui.main.fill_card.FillCardBSD
import com.example.benefit.ui.main.fill_card.FillCardFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_card_options.*
import javax.inject.Inject

/**
 * Created by jahon on 03-Sep-20
 */
@AndroidEntryPoint
class CardOptionsFragment @Inject constructor() : Fragment(R.layout.fragment_card_options),
    IOnCardAction {

    lateinit var card: CardDTO
    private val viewModel: CardOptionsViewModel by viewModels()
    lateinit var selectableCards: List<CardDTO>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        card = requireArguments().getParcelable(FillCardFragment.ARG_CARD)!!
        selectableCards = requireArguments().getParcelableArrayList(FillCardFragment.ARG_CARDS)!!
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        attachListeners()
        subscribeObservers()
    }

    private fun setupViews() {
        cardParent.setBackgroundResource(R.drawable.shape_top_rounded)
        tvBalance.text = card.balance
        tvCardNumber.text = card.pan
        tvCardOwner.text = card.fullName
        tvExpiryDate.text = card.expiry.toString()
        tvCardName.text = card.card_title


    }

    private fun subscribeObservers() {
        viewModel.deleteCardResp.observe(viewLifecycleOwner, {
            val result = it ?: return@observe
        })
        viewModel.isLoading.observe(viewLifecycleOwner, {
            val result = it ?: return@observe
            if (result) loadingDialog.show(parentFragmentManager, "")
            else loadingDialog.dismiss()
        })
        viewModel.blockCardResp.observe(viewLifecycleOwner, {
            val result = it ?: return@observe
        })
        viewModel.errorMessage.observe(viewLifecycleOwner, {
            val result = it ?: return@observe
            Snackbar.make(clParent, result, Snackbar.LENGTH_SHORT).show()
        })

    }

    private fun attachListeners() {
        llBlockCard.setOnClickListener {
            val dialog = DialogBlockCard()
            dialog.setTargetFragment(this, 1)
            dialog.show(parentFragmentManager, "")
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
            val cards = CardsDTO()
            selectableCards.forEach { cards.add(it) }

            val action =
                CardOptionsFragmentDirections.actionCardOptionsFragmentToCardChangeDesignFragment(
                    cards,
                    card
                )
            findNavController().navigate(action)
        }

        llDeleteCard.setOnClickListener {
            val dialog = DialogDeleteCard()
            dialog.setTargetFragment(this, 1)
            dialog.show(parentFragmentManager, "")
        }

    }

    val loadingDialog = DialogLoading()
    override fun onCardDeleteClick() {
        viewModel.deleteCard(card.id!!)
    }

    override fun onCardBlockClick() {
        viewModel.blockCard(card.id!!)
    }

}

interface IOnCardAction {

    fun onCardDeleteClick()
    fun onCardBlockClick()

}
