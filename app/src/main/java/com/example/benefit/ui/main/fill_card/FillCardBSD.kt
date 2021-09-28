package com.example.benefit.ui.main.fill_card

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.benefit.R
import com.example.benefit.remote.models.CardDTO
import com.example.benefit.ui.main.BenefitBSD
import com.example.benefit.ui.main.fill_card.FillCardFragment.Companion.ARG_CARD
import com.example.benefit.ui.main.fill_card.FillCardFragment.Companion.ARG_CARDS
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.bsd_fill_card.*

@AndroidEntryPoint
class FillCardBSD : BenefitBSD() {

    private val viewModel: FillCardViewModel by viewModels()
    var cardBeingFilled: CardDTO? = null
    var selectableCards: List<CardDTO>? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        cardBeingFilled = arguments?.getParcelable(ARG_CARD)
        selectableCards = arguments?.getParcelableArrayList(ARG_CARDS)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.bsd_fill_card, container)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (cardBeingFilled == null || selectableCards == null) {
            subscribeObservers()
            viewModel.getMyCards()
        } else {
            (childFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).findNavController()
                .setGraph(R.navigation.fill_card_nav_graph, Bundle().apply {
                    putParcelable(ARG_CARD, cardBeingFilled)
                    putParcelableArrayList(ARG_CARDS, ArrayList(selectableCards!!))
                })
        }
    }

    private fun subscribeObservers() {

        viewModel.isLoadingCards.observe(viewLifecycleOwner) {
            progress.isVisible = it
        }

        viewModel.cardsResp.observe(viewLifecycleOwner) {
            (childFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).findNavController()
                .setGraph(R.navigation.fill_card_nav_graph, Bundle().apply {
                    putParcelable(ARG_CARD, cardBeingFilled ?: it[0])
                    putParcelableArrayList(ARG_CARDS, ArrayList(it))
                })
        }
    }

}