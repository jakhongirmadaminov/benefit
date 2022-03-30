package uz.magnumactive.benefit.ui.loans.loans_chart

import android.os.Bundle
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.remote.models.CardDTO
import uz.magnumactive.benefit.remote.models.LoanBody
import uz.magnumactive.benefit.ui.base.BaseActionbarActivity
import uz.magnumactive.benefit.ui.main.fill_card.FillCardBSD
import uz.magnumactive.benefit.ui.main.fill_card.FillCardFragment
import kotlinx.android.synthetic.main.activity_loans_chart.*
import org.joda.time.DateTime
import org.joda.time.Days
import org.joda.time.format.DateTimeFormat
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

        tvLoanPaidAmount.text = DecimalFormat("#,###").format(loanInfo.depPime!!) + " UZS"
        tvRate.text = DecimalFormat("#,###").format(loanInfo.perCurr!!) + " UZS"
        tvMainLoan.text = DecimalFormat("#,###").format(loanInfo.depPime!!) + " UZS"
        tvForPay.text =   DecimalFormat("#,###").format(loanInfo.perCurr!!) + " UZS"
        val closeDate =
            DateTimeFormat.forPattern(("dd.MM.yyyy")).parseDateTime(loanInfo.closeDate!!)
        val startDate = closeDate.minusDays(365)

        val daysPassed = Days.daysBetween(startDate.withTimeAtStartOfDay(), DateTime.now().withTimeAtStartOfDay()).days

        circularProgress.setProgress(daysPassed/365f)

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