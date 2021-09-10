package com.example.benefit.ui.loans

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.example.benefit.R
import com.example.benefit.remote.models.CardDTO
import com.example.benefit.remote.models.RespLoanInfo
import com.example.benefit.ui.base.BaseActionbarActivity
import com.example.benefit.ui.loans.loans_chart.EXTRA_LOAN_INFO
import com.example.benefit.ui.loans.loans_chart.LoansChartActivity
import com.example.benefit.ui.transactions_history.TransactionsHistoryActivity.Companion.EXTRA_CARD
import com.example.benefit.util.ResultError
import com.example.benefit.util.ResultSuccess
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_loan.*
import java.text.DecimalFormat


class LoanActivity : BaseActionbarActivity() {

    private val viewModel: LoansViewModel by viewModels()
    lateinit var supremeCard: CardDTO

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_loan)
        setSupportActionBar(tool_bar)
        super.onCreate(savedInstanceState)

        supremeCard = intent.getParcelableExtra(EXTRA_CARD)!!
        viewModel.getLoanIdByPan(supremeCard.panOpen!!)
        attachListeners()
        subscribeObservers()
    }

    private fun subscribeObservers() {
        viewModel.isLoading.observe(this) {
            swipeRefresh.isRefreshing = it
        }

        viewModel.respLoanInfo.observe(this) {
            val resp = it ?: return@observe
            when (resp) {
                is ResultError -> {
                    Snackbar.make(swipeRefresh, resp.message ?: "Error", Snackbar.LENGTH_SHORT)
                        .show()
                }
                is ResultSuccess -> {
                    populateData(resp.value)
                }
            }
        }

    }

    private fun populateData(value: RespLoanInfo) {
        tvSum.text = DecimalFormat("#,###").format(value.responseBody?.sumLoan) + " UZS"
        tvConfirmedSum.text =
            DecimalFormat("#,###").format(value.responseBody!!.sumLoan!! - value.responseBody!!.depPime!!) + " UZS"
        tvUsed.text =
            DecimalFormat("#,###").format(value.responseBody!!.depPime!!) + " UZS"
        tvDeadline.text = value.responseBody.closeDate!!
        tvRate.text = "18% " + getString(R.string.yearly)

        btnDetails.setOnClickListener {
            startActivity(Intent(this, LoansChartActivity::class.java).apply {
                putExtra(EXTRA_LOAN_INFO, value.responseBody)
                putExtra(EXTRA_CARD, supremeCard)
            })
        }
    }

    private fun attachListeners() {


        swipeRefresh.setOnRefreshListener {
            if (viewModel.loanId != null) viewModel.getLoanInfoById(viewModel.loanId!!)
            else viewModel.getLoanIdByPan(supremeCard.panOpen!!)
        }

    }


}