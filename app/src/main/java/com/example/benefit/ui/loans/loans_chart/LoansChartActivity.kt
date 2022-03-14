package com.example.benefit.ui.loans.loans_chart

import android.os.Bundle
import com.example.benefit.R
import com.example.benefit.remote.models.CardDTO
import com.example.benefit.remote.models.LoanBody
import com.example.benefit.ui.base.BaseActionbarActivity
import com.example.benefit.ui.main.fill_card.FillCardBSD
import com.example.benefit.ui.main.fill_card.FillCardFragment
import com.example.benefit.ui.transactions_history.TransactionsHistoryActivity.Companion.EXTRA_CARD
import kotlinx.android.synthetic.main.activity_loans_chart.*
import java.text.DecimalFormat

val EXTRA_LOAN_INFO = "LOAN_INFO"
val EXTRA_CARD = "CARD"

class LoansChartActivity : BaseActionbarActivity() {


    lateinit var card: CardDTO
    lateinit var loanInfo: LoanBody

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_loans_chart)
        setSupportActionBar(tool_bar)
        super.onCreate(savedInstanceState)

        loanInfo = intent.getParcelableExtra(EXTRA_LOAN_INFO)!!
        card = intent.getParcelableExtra(EXTRA_CARD)!!

        setupViews()
        attachListeners()
    }

    private fun setupViews() {

        tvLoanPaidAmount.text = DecimalFormat("#,###").format(loanInfo.sumLoan!!) + " UZS"
        tvRate.text = "18% " + getString(R.string.yearly)
        tvMainLoan.text = DecimalFormat("#,###").format(loanInfo.sumLoan!!) + " UZS"
//        tvForPay.text =   DecimalFormat("#,###").format(loanInfo.sumLoan!!) + " UZS"

        circularProgress.setProgress((loanInfo.depPime!! / loanInfo.sumLoan!!).toFloat())

    }


    private fun attachListeners() {
        btnPay.setOnClickListener {
            FillCardBSD().apply {
                arguments = Bundle().apply {
                    putParcelable(FillCardFragment.ARG_CARD, card)
                    putBoolean(FillCardFragment.ARG_IS_LOAN, true)
                }
            }.show(supportFragmentManager, "")
        }
    }
}