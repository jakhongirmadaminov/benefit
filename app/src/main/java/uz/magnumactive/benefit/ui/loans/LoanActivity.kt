package uz.magnumactive.benefit.ui.loans

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_loan.*
import org.joda.time.DateTime
import org.joda.time.Days
import org.joda.time.format.DateTimeFormat
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.enums.ECardType
import uz.magnumactive.benefit.remote.models.CardDTO
import uz.magnumactive.benefit.remote.models.RespLoanInfo
import uz.magnumactive.benefit.ui.base.BaseActionbarActivity
import uz.magnumactive.benefit.ui.loans.loans_chart.EXTRA_LOAN_INFO
import uz.magnumactive.benefit.ui.loans.loans_chart.LoansChartActivity
import uz.magnumactive.benefit.ui.order_card.OrderCardActivity
import uz.magnumactive.benefit.ui.transactions_history.TransactionsHistoryActivity.Companion.EXTRA_CARD
import uz.magnumactive.benefit.util.ResultError
import uz.magnumactive.benefit.util.ResultSuccess
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
        value.responseBody?.let {
            tvSum.text = DecimalFormat("#,###").format(value.responseBody?.sumLoan) + " UZS"
            tvConfirmedSum.text =
                DecimalFormat("#,###").format(value.responseBody!!.sumLoan!! - value.responseBody!!.depPime!!) + " UZS"
            tvUsed.text =
                DecimalFormat("#,###").format(value.responseBody!!.depPime!!) + " UZS"
            tvDeadline.text = value.responseBody.closeDate!!
            tvRate.text = "18% " + getString(R.string.yearly)
            val closeDate =
                DateTimeFormat.forPattern(("dd.MM.yyyy"))
                    .parseDateTime(value.responseBody.closeDate)
            val startDate = closeDate.minusDays(365)

            val daysPassed = Days.daysBetween(
                startDate.withTimeAtStartOfDay(),
                DateTime.now().withTimeAtStartOfDay()
            ).days
            progress.progress = 100 * daysPassed / 365

            btnDetails.setOnClickListener {
                startActivity(Intent(this, LoansChartActivity::class.java).apply {
                    putExtra(EXTRA_LOAN_INFO, value.responseBody)
                    putExtra(EXTRA_CARD, supremeCard)
                })
            }
        } ?: run {
            btnNewLimit.isClickable = true
            btnNewLimit.setOnClickListener {
                startActivityForResult(Intent(this, OrderCardActivity::class.java).apply {
                    putExtra(OrderCardActivity.EXTRA_CARD_TYPE, ECardType.SUPREME)
                }, OrderCardActivity.REQ_ORDER_CARD)
            }
        }
    }

    private fun attachListeners() {


        swipeRefresh.setOnRefreshListener {
            if (viewModel.loanId != null) viewModel.getLoanInfoById(viewModel.loanId!!)
            else viewModel.getLoanIdByPan(supremeCard.panOpen!!)
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == OrderCardActivity.REQ_ORDER_CARD) {
                viewModel.getLoanIdByPan(supremeCard.panOpen!!)
            }
        }
    }

}