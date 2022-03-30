package uz.magnumactive.benefit.ui.main.transfer_to_card

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.ui.main.BenefitBSD


class TransferToCardBSD : BenefitBSD() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bsd_transfer_to_card, null)

        return view
    }


    fun navigateToCardTransfer() {
//        nav_host_fragment.findNavController()
//            .navigate(R.id.action_fillCardFragment_to_cardMakeDepositFromAnyCardFragment)
    }

}