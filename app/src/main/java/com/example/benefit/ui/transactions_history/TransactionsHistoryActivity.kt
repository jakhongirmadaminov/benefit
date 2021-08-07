package com.example.benefit.ui.transactions_history

import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import com.example.benefit.R
import com.example.benefit.remote.models.TransactionDTO
import com.example.benefit.ui.base.BaseActivity
import com.example.benefit.ui.main.home.HomeFragment
import com.example.benefit.ui.transactions_history.transaction_bsd.TransactionType
import com.example.benefit.ui.viewgroups.CardTagItem
import com.example.benefit.ui.viewgroups.ItemTransaction
import com.example.benefit.util.SizeUtils
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_transactions_history.*
import kotlinx.android.synthetic.main.item_line_chart.view.*
import kotlin.random.Random


class TransactionsHistoryActivity : BaseActivity() {

    companion object {
        const val EXTRA_CARD = "CARD"
        const val EXTRA_CARDS = "CARDS"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transactions_history)

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.elevation = 0F


        setupViews()
        attachListeners()
        subscribeObservers()

    }

    private fun subscribeObservers() {


    }

    private fun attachListeners() {

        cardMonthIncome.setOnClickListener {
            cbMonthIncome.isChecked = true
            cbMonthSpent.isChecked = false
        }
        cardMonthSpent.setOnClickListener {
            cbMonthSpent.isChecked = true
            cbMonthIncome.isChecked = false
        }
    }

    private fun setupViews() {
        rvCardTags.adapter = cardsAdapter
        cbMonthSpent.performClick()
        setupCardTags()
        setupChartPager()
    }

    private fun setupChartPager() {

        val chartView = layoutInflater.inflate(R.layout.item_line_chart, null)
        val chartView2 = layoutInflater.inflate(R.layout.item_line_chart, null)

        chartView.rbMonth1.isChecked = true



        makeChart(chartView.chart)
        makeChart(chartView2.chart)

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

    }


    val cardsAdapter = GroupAdapter<GroupieViewHolder>()
    val transactionsAdapter = GroupAdapter<GroupieViewHolder>()
    private fun setupCardTags() {

        rvTransactions.adapter = transactionsAdapter

        val data = arrayListOf(
            TransactionDTO(
                "Amphora",
                "#заинтернет",
                100000,
                "",
                1593863236,
                TransactionType.COMMERCIAL_PAYMENT
            ),
            TransactionDTO(
                "ЧП “Nuraliev”",
                "Категория не выбрана",
                250000,
                "",
                1593863236,
                TransactionType.NONE
            ),
            TransactionDTO(
                "Перевод на карту",
                "Kapital Bank",
                300000,
                "",
                1593863236,
                TransactionType.TRANSFER_TO_CARD
            ),
            TransactionDTO(
                "Infinity Roses",
                "Магазины Цветов",
                120000,
                "",
                1593863236,
                TransactionType.COMMERCIAL_PAYMENT
            )
        )
        transactionsAdapter.clear()

        data.forEach {
            transactionsAdapter.add(ItemTransaction(it))
        }
        transactionsAdapter.notifyDataSetChanged()

//        val tempVals = arrayListOf("Benefit", "Zoom", "Cashback", "Детям", "Общее", "...")
//
//        tempVals.forEach {
//            cardsAdapter.add(CardTagItem(it) { cardItem ->
//                for (i in 0 until cardsAdapter.itemCount) {
//                    (cardsAdapter.getItem(i) as CardTagItem).selected = false
//                }
//                cardItem.selected = true
//                cardsAdapter.notifyDataSetChanged()
//            })
//        }


//        cardsAdapter.notifyDataSetChanged()
    }

    private fun makeChart(chart: LineChart) {
        val entries = ArrayList<Entry>()
        for (i in 0..20) {
            // turn your data into Entry objects
            entries.add(Entry(i.toFloat(), Random.nextInt(0, 50).toFloat()))
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