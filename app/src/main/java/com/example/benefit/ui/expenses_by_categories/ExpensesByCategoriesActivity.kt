package com.example.benefit.ui.expenses_by_categories

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.lifecycle.Observer
import com.example.benefit.R
import com.example.benefit.remote.models.CardDTO
import com.example.benefit.remote.models.PartnerCategoryDTO
import com.example.benefit.remote.models.TransactionAnalyticsDTO
import com.example.benefit.remote.models.TransactionInOutDTO
import com.example.benefit.ui.base.BaseActionbarActivity
import com.example.benefit.ui.main.home.HomeFragment
import com.example.benefit.ui.viewgroups.CardTagItem
import com.example.benefit.ui.viewgroups.ItemCategorySmall
import com.example.benefit.util.*
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_expenses_by_categories.*
import kotlinx.android.synthetic.main.item_bar_chart.view.*
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import java.text.DecimalFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

const val ARG_CARDS = "CARDS"

class ExpensesByCategoriesActivity : BaseActionbarActivity(), OnChartValueSelectedListener {

    private val viewModel: ExpensesByCategoriesViewModel by viewModels()

    @Inject
    lateinit var datetimeManager: SystemDateTimeManager

    lateinit var myCards: List<CardDTO>
    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_expenses_by_categories)
        setSupportActionBar(tool_bar)
        super.onCreate(savedInstanceState)
        myCards = intent.getParcelableArrayListExtra(ARG_CARDS)

        setupViews()
        attachListeners()
        subscribeObservers()
        viewModel.getPartnerCategories()

        viewModel.getExpensesAndAnalytics(
            myCards[0].id!!
        )
    }

    private fun setupViews() {
        rvCategories.adapter = categoriesAdapter
        rvCardTags.adapter = tagsAdapter

        setupCardTags()

    }


    private fun attachListeners() {


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
                    tvExpenses.text = ""
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

        viewModel.partnerCategoriesResp.observe(this, Observer {
            val response = it ?: return@Observer
            when (response) {
                is ResultError -> {
                }
                is ResultError -> {
                }
                is ResultSuccess -> {
                    loadData(response.value)
                }
            }.exhaustive
        })

    }

    val categoriesAdapter = GroupAdapter<GroupieViewHolder>()
    private fun loadData(value: List<PartnerCategoryDTO>) {
        categoriesAdapter.clear()
        value.forEach {
            categoriesAdapter.add(ItemCategorySmall(it))
        }
        categoriesAdapter.notifyDataSetChanged()
    }

    val tagsAdapter = GroupAdapter<GroupieViewHolder>()
    private fun setupCardTags() {

        myCards.forEachIndexed { index, cardDTO ->
            val cardItem = CardTagItem(cardDTO) { cardItem, card ->
                for (i in 0 until tagsAdapter.itemCount) {
                    (tagsAdapter.getItem(i) as CardTagItem).selected = false
                }
                cardItem.selected = true
                tagsAdapter.notifyDataSetChanged()
                viewModel.getExpensesAndAnalytics(card.id!!)
            }
            cardItem.selected = index == 0
            tagsAdapter.add(cardItem)
        }

    }


    private fun setupChartPager(value: List<TransactionInOutDTO>) {
        chartPager.adapter = null
        chartPager.removeAllViews()

        val chartView = layoutInflater.inflate(R.layout.item_bar_chart, null)
        val chartView2 = layoutInflater.inflate(R.layout.item_bar_chart, null)

        chartView.llMonths.children.forEachIndexed { index, view ->
            (view as? TextView)?.text =
                DateTimeFormat.forPattern("MMM").print(DateTime.now().minusMonths(11 - index))
        }

        chartView2.llMonths.children.forEachIndexed { index, view ->
            (view as? TextView)?.text =
                DateTimeFormat.forPattern("MMM").print(DateTime.now().minusMonths(5 - index))
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
    }

    private fun makeChart(chart: BarChart, value: List<TransactionInOutDTO>) {
        val entries = ArrayList<BarEntry>()
        value.reversed().forEachIndexed { index, item ->
            // turn your data into Entry objects
            entries.add(BarEntry(index.toFloat(), item.outcome_total.toFloat()))
        }

        val dataSet = BarDataSet(entries, "") // add entries to dataset
        dataSet.setDrawIcons(false)
        dataSet.setDrawValues(false)
        dataSet.label = ""
        dataSet.highLightColor = Color.parseColor("#f47964")
        dataSet.setGradientColor(Color.parseColor("#b9b2b2"), Color.parseColor("#d4cfcf"))
        dataSet.highLightAlpha = 255

        val lineData = BarData(dataSet)

        chart.setOnChartValueSelectedListener(this)

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
        chart.setGridBackgroundColor(ContextCompat.getColor(this, R.color.dark_purple))
        chart.isAutoScaleMinMaxEnabled = false
        chart.setDrawBarShadow(false)

//                chart.minOffset = SizeUtils.dpToPx(this, 10)
        chart.renderer = MyBarChartRenderer(this, chart, chart.animator, chart.viewPortHandler)
        chart.isDoubleTapToZoomEnabled = false
        chart.legend.isEnabled = false
        chart.invalidate() // refresh

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {

        e?.let {
            showExpenses(it.x.toInt())
        }


    }

    private fun showExpenses(barIndex: Int) {
        if (chartPager.currentItem == 0) {
            loadCategorizedExpenses(
                (viewModel.transactionsAnalyticsResp.value as ResultSuccess).value[11 - barIndex],
                11 - barIndex
            )
        } else {
            loadCategorizedExpenses(
                (viewModel.transactionsAnalyticsResp.value as ResultSuccess).value[5 - barIndex],
                5 - barIndex
            )
        }
    }

    private fun loadCategorizedExpenses(list: List<TransactionAnalyticsDTO>, monthIndex: Int) {
        val expensesByCategoryMap = hashMapOf<Int, Long>()

        list.forEach { transaction ->
            transaction.partner?.let { partner ->
                expensesByCategoryMap[partner.category_id!!]?.plus(transaction.reqamt!!) ?: run {
                    expensesByCategoryMap[partner.category_id] = transaction.reqamt!!.toLong()
                }
            }
        }

        var totalExpense = 0L
        expensesByCategoryMap.values.forEach {
            totalExpense += it
        }

        tvExpenses.text = getString(R.string.sums, DecimalFormat("#,###").format(totalExpense))
        tvSpentExpensesForMonth.text =
            getString(R.string.expense_for_month) + " " + Constants.MONTHS[AppPrefs.language]!![DateTime.now().monthOfYear - monthIndex - 1]


        for (i in 0 until categoriesAdapter.itemCount) {
            val categoryItem = (categoriesAdapter.getItem(i) as ItemCategorySmall)
            categoryItem.expense = 0
            categoryItem.percentage = 0f
            expensesByCategoryMap[categoryItem.obj.id]?.let {
                categoryItem.expense = it
                categoryItem.percentage = (it / totalExpense).toFloat()
            }
        }

        categoriesAdapter.notifyDataSetChanged()
    }

    override fun onNothingSelected() {


    }


}
