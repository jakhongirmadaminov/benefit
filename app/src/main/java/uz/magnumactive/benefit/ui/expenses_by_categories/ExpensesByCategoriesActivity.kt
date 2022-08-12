package uz.magnumactive.benefit.ui.expenses_by_categories

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.lifecycle.Observer
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_expenses_by_categories.*
import kotlinx.android.synthetic.main.item_bar_chart.view.*
import org.joda.time.DateTime
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.remote.models.CardDTO
import uz.magnumactive.benefit.remote.models.PartnerCategoryDTO
import uz.magnumactive.benefit.remote.models.TransactionAnalyticsDTO
import uz.magnumactive.benefit.remote.models.TransactionInOutDTO
import uz.magnumactive.benefit.ui.base.BaseActionbarActivity
import uz.magnumactive.benefit.ui.main.home.HomeFragment
import uz.magnumactive.benefit.ui.viewgroups.CardTagItem
import uz.magnumactive.benefit.ui.viewgroups.ItemCategorySmall
import uz.magnumactive.benefit.util.*
import java.text.DecimalFormat
import javax.inject.Inject
import kotlin.collections.set

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
        myCards = intent.getParcelableArrayListExtra(ARG_CARDS)!!

        setupViews()
        attachListeners()
        subscribeObservers()
        viewModel.getPartnerCategories()

        viewModel.getExpensesAndAnalytics(myCards[0].id!!)
    }

    private fun setupViews() {
        rvCategories.adapter = categoriesAdapter
        rvCardTags.adapter = tagsAdapter

        setupCardTags()

    }


    private fun attachListeners() {
        swipeRefresh.setOnRefreshListener {
            selectTag(tagsAdapter.getItem(0) as CardTagItem, myCards[0])
        }
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
            swipeRefresh.isRefreshing = it
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
                selectTag(cardItem, card)
            }
            cardItem.selected = index == 0
            tagsAdapter.add(cardItem)
        }

    }

    private fun selectTag(cardItem: CardTagItem, card: CardDTO) {
        for (i in 0 until tagsAdapter.itemCount) {
            (tagsAdapter.getItem(i) as CardTagItem).selected = false
        }
        cardItem.selected = true
        tagsAdapter.notifyDataSetChanged()
        viewModel.getExpensesAndAnalytics(card.id!!)
    }


    private fun setupChartPager(value: List<TransactionInOutDTO>) {
        chartPager.adapter = null
        chartPager.removeAllViews()

        val chartView = layoutInflater.inflate(R.layout.item_bar_chart, null)
        val chartView2 = layoutInflater.inflate(R.layout.item_bar_chart, null)

        chartView.llMonths.children.forEachIndexed { index, view ->
            (view as? TextView)?.text =
                Constants.MONTHS[AppPrefs.language!!]!![index].substring(0, 3).uppercase()
//                DateTimeFormat.forPattern("MMM")
//                    .print(DateTime(DateTime.now().year, index + 1, 1, 0, 0))
        }

        chartView2.llMonths.children.forEachIndexed { index, view ->
            (view as? TextView)?.text =
                Constants.MONTHS[AppPrefs.language!!]!![index + 6].substring(0, 3).uppercase()
//                DateTimeFormat.forPattern("MMM")
//                    .print(DateTime(DateTime.now().year, 6 + index + 1, 1, 0, 0))
        }

        makeChart(chartView.chart, value.dropLast(6))
        makeChart(chartView2.chart, value.drop(6))

        chartPager.addView(chartView)
        chartPager.addView(chartView2)


        chartPager.adapter = HomeFragment.WizardPagerAdapter(listOf(chartView, chartView2))
        chartPager.offscreenPageLimit = 10
        chartPager.clipToPadding = false
        chartPager.setPadding(
            SizeUtils.dpToPx(this, 26).toInt(),
            0,
            SizeUtils.dpToPx(this, 26).toInt(),
            0
        )
        chartPager.pageMargin = SizeUtils.dpToPx(this, 26).toInt()
        chartPager.currentItem = if (DateTime.now().monthOfYear < 7) 0 else 1
        val todayBarIndex = DateTime.now().monthOfYear - 1

        if (todayBarIndex < 6) {
            chartView2.chart.highlightValue(todayBarIndex.toFloat(), 0, true)
        } else {
            chartView2.chart.highlightValue(todayBarIndex - 6f, 0, true)
        }

    }

    private fun makeChart(chart: BarChart, value: List<TransactionInOutDTO>) {
        val entries = ArrayList<BarEntry>()
        if (value.any { it.outcome_total.toFloat() > 0 }) {
            value.forEachIndexed { index, item ->
                entries.add(BarEntry(index.toFloat(), item.outcome_total.toFloat()))
            }
        }

        val dataSet = BarDataSet(entries, "")
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
                (viewModel.transactionsAnalyticsResp.value as ResultSuccess).value[barIndex],
                barIndex
            )
        } else {
            loadCategorizedExpenses(
                (viewModel.transactionsAnalyticsResp.value as ResultSuccess).value[barIndex + 6],
                barIndex + 6
            )
        }
    }

    private fun loadCategorizedExpenses(list: List<TransactionAnalyticsDTO>, monthIndex: Int) {
        val expensesByCategoryMap = hashMapOf<Long, Long>()

        list.forEach { transaction ->
            transaction.partner?.let { partner ->
                if (expensesByCategoryMap[partner.category_id!!] == null) {
                    expensesByCategoryMap[partner.category_id] = transaction.amountWithoutTiyin
                } else {
                    expensesByCategoryMap[partner.category_id] =
                        expensesByCategoryMap[partner.category_id]!! + transaction.amountWithoutTiyin
                }
            }
        }

        val totalExpense =
            (viewModel.transactionsReportResp.value as ResultSuccess).value[monthIndex].outcome_total / 100

        tvExpenses.text = getString(R.string.sums, DecimalFormat("#,###").format(totalExpense))
        tvSpentExpensesForMonth.text =
            getString(R.string.expense_for_month) + " " + Constants.MONTHS[AppPrefs.language]!![monthIndex]


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
