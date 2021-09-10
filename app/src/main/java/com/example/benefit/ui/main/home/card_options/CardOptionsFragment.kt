package com.example.benefit.ui.main.home.card_options

/**
 * Created by jahon on 03-Sep-20
 */
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.benefit.R
import com.example.benefit.remote.models.CardDTO
import com.example.benefit.remote.models.CardsDTO
import com.example.benefit.ui.DialogLoading
import com.example.benefit.ui.base.BaseFragment
import com.example.benefit.ui.main.fill_card.FillCardBSD
import com.example.benefit.ui.main.fill_card.FillCardFragment
import com.example.benefit.ui.transactions_history.TransactionsHistoryActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_card_options.*
import splitties.fragments.start
import java.text.DecimalFormat

class CardOptionsFragment : BaseFragment(R.layout.fragment_card_options),
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
        tvBalance.text = "${DecimalFormat("#,###").format(card.balance!!.dropLast(2).toInt())} UZS"
        tvCardNumber.text = card.panHidden
        tvCardOwner.text = card.fullName
        tvExpiryDate.text = card.expiry.toString()
        tvCardName.text = card.card_title

        card.setBackgroundInto(ivCardBg, tvCardTitle)

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
            start<TransactionsHistoryActivity> {
                putParcelableArrayListExtra(
                    TransactionsHistoryActivity.EXTRA_CARDS,
                    ArrayList(selectableCards)
                )
                putExtra(TransactionsHistoryActivity.EXTRA_CARD, card)
            }
        }
        llMakeDeposit.setOnClickListener {
            ((parentFragment as NavHostFragment).parentFragment as CardOptionsBSD).dismiss()

            val dialog = FillCardBSD()
            dialog.arguments = Bundle().apply {
                putParcelable(FillCardFragment.ARG_CARD, card)
                putParcelableArrayList(FillCardFragment.ARG_CARDS, ArrayList(selectableCards))
            }
            dialog.show(requireActivity().supportFragmentManager, "")
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
