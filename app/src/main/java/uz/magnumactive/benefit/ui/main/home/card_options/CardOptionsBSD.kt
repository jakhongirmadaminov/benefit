package uz.magnumactive.benefit.ui.main.home.card_options

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.remote.models.CardDTO
import uz.magnumactive.benefit.ui.main.BenefitBSD
import uz.magnumactive.benefit.ui.main.fill_card.FillCardFragment



class CardOptionsBSD : BenefitBSD() {

    lateinit var cardBeingFilled: CardDTO
    lateinit var selectableCards: List<CardDTO>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        cardBeingFilled = requireArguments().getParcelable(FillCardFragment.ARG_CARD)!!
        selectableCards = requireArguments().getParcelableArrayList(FillCardFragment.ARG_CARDS)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = inflater.inflate(R.layout.bsd_card_options, container)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (childFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).findNavController()
            .setGraph(R.navigation.card_options_nav_graph, Bundle().apply {
                putParcelable(FillCardFragment.ARG_CARD, cardBeingFilled)
                putParcelableArrayList(FillCardFragment.ARG_CARDS, ArrayList(selectableCards))
            })

    }
}