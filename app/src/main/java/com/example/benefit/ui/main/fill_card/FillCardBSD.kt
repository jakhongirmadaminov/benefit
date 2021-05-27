package com.example.benefit.ui.main.fill_card

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.benefit.R
import com.example.benefit.remote.models.CardDTO
import com.example.benefit.ui.main.fill_card.FillCardFragment.Companion.ARG_CARD
import com.example.benefit.ui.main.fill_card.FillCardFragment.Companion.ARG_CARDS
import com.example.benefit.util.MyBSDialog
import dagger.hilt.android.AndroidEntryPoint



class FillCardBSD : MyBSDialog() {

    private val viewModel: FillCardViewModel by viewModels()
    lateinit var cardBeingFilled: CardDTO
    lateinit var selectableCards: List<CardDTO>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        cardBeingFilled = requireArguments().getParcelable(ARG_CARD)!!
        selectableCards = requireArguments().getParcelableArrayList(ARG_CARDS)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bsd_fill_card, null)

        return view
    }


//    fun navigateToCardTransfer() {
//        nav_host_fragment.findNavController()
//            .navigate(R.id.action_fillCardFragment_to_cardMakeDepositFromAnyCardFragment)
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (childFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).findNavController()
            .setGraph(R.navigation.fill_card_nav_graph, Bundle().apply {
                putParcelable(ARG_CARD, cardBeingFilled)
                putParcelableArrayList(ARG_CARDS, ArrayList(selectableCards))
            })

    }
}