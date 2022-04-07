package uz.magnumactive.benefit.ui.transactions_history.transaction_bsd

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.remote.models.TransactionAnalyticsDTO
import uz.magnumactive.benefit.remote.models.TransactionInOutDTO
import uz.magnumactive.benefit.ui.main.BenefitFixedHeightBSD


class TransactionBSD : BenefitFixedHeightBSD() {

    companion object {
        const val ARG_TRANSACTION_DTO = "TRANSACTION_DTO"
        const val ARG_TRANSACTIONS_REPORT = "transactionsReport"
        const val ARG_TRANSACTIONS_ANALYTICS = "transactionsAnalytics"
    }

    lateinit var transactionDTO: TransactionAnalyticsDTO
    var transactionsReport: ArrayList<TransactionInOutDTO>? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        transactionDTO = requireArguments().getParcelable(ARG_TRANSACTION_DTO)!!
        transactionsReport = requireArguments().getParcelableArrayList(ARG_TRANSACTIONS_REPORT)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bsd_transaction, container)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (childFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).findNavController()
            .setGraph(R.navigation.transaction_nav_graph, Bundle().apply {
                putParcelable(ARG_TRANSACTION_DTO, transactionDTO)
                transactionsReport?.let {
                    putParcelableArrayList(ARG_TRANSACTIONS_REPORT, transactionsReport)
                }
            })

    }

    fun navigateToCardTransfer() {
//        nav_host_fragment.findNavController()
//            .navigate(R.id.action_fillCardFragment_to_cardMakeDepositFromAnyCardFragment)
    }

}