package com.example.benefit.ui.transactions_history

import android.os.Bundle
import android.view.MenuItem
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.core.view.get
import com.example.benefit.R
import com.example.benefit.remote.models.CardDTO
import com.example.benefit.remote.models.TransactionAnalyticsDTO
import com.example.benefit.remote.models.TransactionInOutDTO
import com.example.benefit.ui.base.BaseActivity
import com.example.benefit.ui.expenses_by_categories.ARG_CARDS
import com.example.benefit.ui.main.home.HomeFragment
import com.example.benefit.ui.transactions_history.transaction_bsd.TransactionBSD
import com.example.benefit.ui.viewgroups.CardTagItem
import com.example.benefit.ui.viewgroups.ItemTransaction
import com.example.benefit.ui.viewgroups.ItemTransactionDate
import com.example.benefit.util.*
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_transactions_history.*
import kotlinx.android.synthetic.main.item_line_chart.view.*
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import java.text.DecimalFormat


class TransactionsHistoryActivity : BaseActivity(), OnChartValueSelectedListener {

    companion object {
        const val EXTRA_CARD = "CARD"
        const val EXTRA_CARDS = "CARDS"
    }

    var selectedCardId: Int = 0
    lateinit var myCards: List<CardDTO>

    private val viewModel: TransactionsHistoryViewModel by viewModels()

    var selectedMonthOffset = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transactions_history)
        myCards = intent.getParcelableArrayListExtra(ARG_CARDS)
        selectedCardId = myCards[0].id!!
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.elevation = 0F


        setupViews()
        attachListeners()
        subscribeObservers()

        viewModel.getTransactionsHistory(selectedCardId)
    }

    private fun subscribeObservers() {


        viewModel.transactionsAnalyticsResp.observe(this) {
            when (it) {
                is ResultError -> {
                    Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show()
                }
                is ResultSuccess -> {
//                    loadGraph(it.value)
                }
            }
        }

        viewModel.transactionsReportResp.observe(this) {
            when (it) {
                is ResultError -> {
//                    tvExpenses.text = ""
                }
                is ResultSuccess -> {
                    setupChartPager(it.value)

                }
            }
        }

        viewModel.analyticsReportLoading.observe(this) {

        }

        viewModel.totalExpenseReportLoading.observe(this) {

        }
        viewModel.isLoading.observe(this) {
            swipeRefresh.isRefreshing = it
        }

    }

    private fun attachListeners() {

        swipeRefresh.setOnRefreshListener {
            viewModel.getTransactionsHistory(selectedCardId)
        }

        cardMonthIncome.setOnClickListener {
            cbMonthIncome.isChecked = true
            cbMonthSpent.isChecked = false
            if (viewModel.transactionsAnalyticsResp.value != null && viewModel.transactionsReportResp.value!! is ResultSuccess) {
                setupChartPager((viewModel.transactionsReportResp.value!! as ResultSuccess).value)
            }
        }
        cardMonthSpent.setOnClickListener {
            cbMonthSpent.isChecked = true
            cbMonthIncome.isChecked = false
            if (viewModel.transactionsAnalyticsResp.value != null && viewModel.transactionsReportResp.value!! is ResultSuccess) {
                setupChartPager((viewModel.transactionsReportResp.value!! as ResultSuccess).value)
            }
        }
    }

    private fun setupViews() {
        rvCardTags.adapter = tagsAdapter
        rvTransactions.adapter = transactionsAdapter
        cbMonthSpent.performClick()
        setupCardTags()
    }


    private fun setupChartPager(value: List<TransactionInOutDTO>) {
        chartPager.adapter = null
        chartPager.removeAllViews()
        val chartView = layoutInflater.inflate(R.layout.item_line_chart, null)
        val chartView2 = layoutInflater.inflate(R.layout.item_line_chart, null)

        chartView2.rbMonth6.isChecked = true
        chartView.llMonths.children.forEachIndexed { index, view ->
            (view as? TextView)?.text =
                DateTimeFormat.forPattern("MMM").print(DateTime.now().minusMonths(11 - index))
        }

        chartView2.llMonths.children.forEachIndexed { index, view ->
            (view as? TextView)?.text =
                DateTimeFormat.forPattern("MMM").print(DateTime.now().minusMonths(5 - index))
        }

        chartView.radioGroup.children.forEachIndexed { index, view ->
            (view as RadioButton).setOnCheckedChangeListener { compoundButton, b ->
                if (b) {
                    selectedMonthOffset = 11 - index
                    loadTransactions(
                        (viewModel.transactionsAnalyticsResp.value as ResultSuccess).value[selectedMonthOffset]
                    )
                }
            }
        }
        chartView2.radioGroup.children.forEachIndexed { index, view ->
            (view as RadioButton).setOnCheckedChangeListener { compoundButton, b ->
                if (b) {
                    selectedMonthOffset = 5 - index
                    loadTransactions(
                        (viewModel.transactionsAnalyticsResp.value as ResultSuccess).value[selectedMonthOffset]
                    )
                }
            }
        }



        makeChart(chartView.chart, value.drop(6))
        makeChart(chartView2.chart, value.dropLast(6))

        chartPager.addView(chartView)
        chartPager.addView(chartView2)


        chartPager.adapter = HomeFragment.WizardPagerAdapter(listOf(chartView, chartView2))
        chartPager.offscreenPageLimit = 2
        chartPager.clipToPadding = false
        chartPager.setPadding(
            SizeUtils.dpToPx(this, 26).toInt(),
            0,
            SizeUtils.dpToPx(this, 26).toInt(),
            0
        )
        chartPager.pageMargin = SizeUtils.dpToPx(this, 26).toInt()
        chartPager.currentItem = 1
        loadTransactions((viewModel.transactionsAnalyticsResp.value as ResultSuccess).value[0])
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        e?.let {
            showExpenses(it.x.toInt())
        }
    }

    override fun onNothingSelected() {

    }
// if isCredit false znachit eto expense
// if isCredit true znachit eto popolnenie

    private fun showExpenses(barIndex: Int) {
        if (chartPager.currentItem == 0) {
            chartPager[0].radioGroup.children.forEachIndexed { index, view ->
                (view as RadioButton).isChecked = index == barIndex
            }
            selectedMonthOffset = 11 - barIndex
        } else {
            chartPager[1].radioGroup.children.forEachIndexed { index, view ->
                (view as RadioButton).isChecked = index == barIndex
            }
            selectedMonthOffset = 5 - barIndex
        }
        loadTransactions((viewModel.transactionsAnalyticsResp.value as ResultSuccess).value[selectedMonthOffset])
    }

    val transactionsAdapter = GroupAdapter<GroupieViewHolder>()
    private fun loadTransactions(value: List<TransactionAnalyticsDTO>) {
        transactionsAdapter.clear()
        var dateString: Int? = null
        value.forEach {
            if (cbMonthSpent.isChecked && !it.isCredit!! || cbMonthIncome.isChecked && it.isCredit!!) {
                if (it.udate != dateString) {
                    transactionsAdapter.add(ItemTransactionDate(it.udate!!))
                    dateString = it.udate
                }
                transactionsAdapter.add(ItemTransaction(it) {
                    val dialog = TransactionBSD()
                    dialog.arguments = Bundle().apply {
                        putParcelable(TransactionBSD.ARG_TRANSACTION_DTO, it)
                    }
                    dialog.show(supportFragmentManager, "")
                })
            }
        }

        var totalExpense = 0L
        var totalIncome = 0L
        value.forEach {
            if (!it.isCredit!!) {
                totalExpense += it.amountWithoutTiyin!!
            } else if (it.isCredit) {
                totalIncome += it.amountWithoutTiyin!!
            }
        }
        tvIncomeOnMonthAmount.text =
            getString(R.string.sums, DecimalFormat("#,###").format(totalIncome))
        lblIncomeOnMonth.text =
            getString(R.string.income_for_month) + " " + if (DateTime.now().monthOfYear - selectedMonthOffset - 1 >= 0) Constants.MONTHS[AppPrefs.language]!![DateTime.now().monthOfYear - selectedMonthOffset - 1]
            else Constants.MONTHS[AppPrefs.language]!![DateTime.now().monthOfYear - selectedMonthOffset - 1 + 12]

        tvSpentOnMonthAmount.text =
            getString(R.string.sums, DecimalFormat("#,###").format(totalExpense))
        lblSpentOnMonth.text =
            getString(R.string.expense_for_month) + " " + if (DateTime.now().monthOfYear - selectedMonthOffset - 1 >= 0) Constants.MONTHS[AppPrefs.language]!![DateTime.now().monthOfYear - selectedMonthOffset - 1]
            else Constants.MONTHS[AppPrefs.language]!![DateTime.now().monthOfYear - selectedMonthOffset - 1 + 12]


    }

    val tagsAdapter = GroupAdapter<GroupieViewHolder>()

    private fun setupCardTags() {

        myCards.forEachIndexed { index, cardDTO ->
            val cardItem = CardTagItem(cardDTO) { cardItem, card ->
                for (i in 0 until tagsAdapter.itemCount) {
                    (tagsAdapter.getItem(i) as CardTagItem).selected = false
                }
                cardItem.selected = true
                selectedCardId = card.id!!
                tagsAdapter.notifyDataSetChanged()
                viewModel.getTransactionsHistory(selectedCardId)
            }
            cardItem.selected = index == 0
            tagsAdapter.add(cardItem)
        }

    }

    private fun makeChart(chart: LineChart, value: List<TransactionInOutDTO>) {
        val entries = ArrayList<Entry>()
        value.reversed().forEachIndexed { index, item ->
            // turn your data into Entry objects
            entries.add(
                Entry(
                    index.toFloat(),
                    if (cbMonthSpent.isChecked) item.outcome_total.toFloat() else item.income_total.toFloat()
                )
            )
        }


        val dataSet = LineDataSet(entries, "") // add entries to dataset
//        dataSet.color = ContextCompat.getColor(this, R.color.colorAccent)
//        dataSet.setValueTextColor()

        dataSet.mode = LineDataSet.Mode.HORIZONTAL_BEZIER
        dataSet.cubicIntensity = 0.3F
        dataSet.setDrawCircleHole(false)
        dataSet.setDrawCircles(false)
        dataSet.setDrawHighlightIndicators(false)
        dataSet.setDrawHorizontalHighlightIndicator(false)
        dataSet.setDrawIcons(false)
        dataSet.setDrawVerticalHighlightIndicator(false)
        dataSet.setDrawValues(false)
        dataSet.disableDashedLine()
        dataSet.disableDashedHighlightLine()

        dataSet.fillDrawable = ContextCompat.getDrawable(this, R.drawable.gradient_line_chart)
        dataSet.setDrawFilled(true)
        dataSet.label = ""
        dataSet.lineWidth = 0F
//        dataSet.fillDrawable = ContextCompat.getDrawable(this, R.drawable.gradient_peach)

        val lineData = LineData(dataSet)


        chart.data = lineData
        chart.setOnChartValueSelectedListener(this)

        val xAxis = chart.xAxis
        xAxis.disableGridDashedLine()
        xAxis.disableAxisLineDashedLine()
        xAxis.setDrawAxisLine(false)
        xAxis.setDrawGridLines(false)
        xAxis.setDrawGridLinesBehindData(false)
        xAxis.setDrawLabels(false)
        xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE
        xAxis.setDrawLimitLinesBehindData(false)


        val yAxisLeft = chart.axisLeft
        yAxisLeft.disableGridDashedLine()
        yAxisLeft.disableAxisLineDashedLine()
        yAxisLeft.setDrawAxisLine(false)
        yAxisLeft.setDrawGridLines(false)
        yAxisLeft.setDrawGridLinesBehindData(false)
        yAxisLeft.setDrawLabels(false)
        yAxisLeft.setDrawLimitLinesBehindData(false)
        yAxisLeft.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)

        val yAxisRight = chart.axisRight
        yAxisRight.disableGridDashedLine()
        yAxisRight.disableAxisLineDashedLine()
        yAxisRight.setDrawAxisLine(false)
        yAxisRight.setDrawGridLines(false)
        yAxisRight.setDrawGridLinesBehindData(false)
        yAxisRight.setDrawLabels(false)
        yAxisRight.setDrawLimitLinesBehindData(false)
        yAxisRight.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART)

        chart.description = null
        chart.setDrawGridBackground(true)
        chart.setGridBackgroundColor(ContextCompat.getColor(this, R.color.peach))
        chart.legend.isEnabled = false
        chart.invalidate() // refresh


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}