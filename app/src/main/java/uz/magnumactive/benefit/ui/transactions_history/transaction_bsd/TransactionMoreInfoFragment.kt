package uz.magnumactive.benefit.ui.transactions_history.transaction_bsd

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_transaction_more_info.*
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.ui.base.BaseFragment
import uz.magnumactive.benefit.util.AppPrefs
import uz.magnumactive.benefit.util.loadImageUrl
import javax.inject.Inject


/**
 * Created by jahon on 03-Sep-20
 */

class TransactionMoreInfoFragment @Inject constructor() :
    BaseFragment(R.layout.fragment_transaction_more_info) {

    val args: TransactionMoreInfoFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupViews()

        attachListeners()
        subscribeObservers()
    }

    private fun setupViews() {
        tvBrandName.text = args.transactionDTO.merchantName
        if (args.transactionDTO.partner?.image.isNullOrBlank()) {
//            ivBrandLogo.isVisible = false
        } else {
            ivBrandLogo.isVisible = true
            ivBrandLogo.loadImageUrl(args.transactionDTO.partner!!.image!!)
        }

        tvDenomination.text = args.transactionDTO.merchantName
        tvReceiptNumber.text = args.transactionDTO.actamt.toString()
        tvCategory.text = args.transactionDTO.categoryName?.getLocalized(AppPrefs.language)
//        tvStatus.text = args.transactionDTO.stat
        tvSum.text = args.transactionDTO.amountWithoutTiyin.toString()
        tvOperationDateAndTime.text =
            args.transactionDTO.dateFormatted + ", " + args.transactionDTO.timeFormatted
//        tvAccountNumber.text = args.transactionDTO.acctbal
//        tvAID.text = args.transactionDTO.a
        tvFromCard.text = args.transactionDTO.hpan
        tvTerminal.text = args.transactionDTO.terminal
        tvReceiptCode.text = args.transactionDTO.terminal
//        tvServicePoint.text = args.transactionDTO.serv

    }

    private fun subscribeObservers() {


    }

    private fun attachListeners() {
        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }


}
