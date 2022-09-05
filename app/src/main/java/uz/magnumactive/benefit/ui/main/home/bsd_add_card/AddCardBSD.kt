package uz.magnumactive.benefit.ui.main.home.bsd_add_card

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.ui.main.BenefitFixedHeightBSD


const val IS_FROM_MARKETPLACE = "IS_FROM_MARKETPLACE"

class AddCardBSD(val isFromMarketPlace: Boolean = false, val cardAdded: (() -> Unit)? = null) :
    BenefitFixedHeightBSD() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bsd_add_card, container)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (childFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).findNavController()
            .setGraph(R.navigation.add_card_nav_graph, Bundle().apply {
                putBoolean(IS_FROM_MARKETPLACE, isFromMarketPlace)
            })
    }
}