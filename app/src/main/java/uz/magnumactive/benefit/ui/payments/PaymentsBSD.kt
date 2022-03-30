package uz.magnumactive.benefit.ui.payments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.remote.models.PaynetCategory
import uz.magnumactive.benefit.ui.main.BenefitFixedHeightBSD

const val ARG_PAYNET_CATEGORY = "paynetCategory"

class PaymentsBSD : BenefitFixedHeightBSD() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bsd_payments, null)

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController =
            (childFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).findNavController()

        val paynetCatg = arguments?.getParcelable<PaynetCategory>(ARG_PAYNET_CATEGORY)

        navController.setGraph(R.navigation.payments_nav_graph, Bundle())

        paynetCatg?.let {
            navController.navigate(
                R.id.action_paymentsFragment_to_selectMerchantFragment,
                Bundle().apply {
                    putParcelable(ARG_PAYNET_CATEGORY, paynetCatg)
                },
                NavOptions.Builder().setPopUpTo(R.id.paymentsFragment, true).build()
            );
        }

    }

}