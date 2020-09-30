package com.example.benefit.ui.expenses_by_categories

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.benefit.R
import com.example.benefit.remote.models.PartnerCategoryDTO
import com.example.benefit.ui.base.BaseActionbarActivity
import com.example.benefit.ui.main.home.HomeFragment
import com.example.benefit.ui.viewgroups.CardTagItem
import com.example.benefit.ui.viewgroups.ItemCategorySmall
import com.example.benefit.util.*
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_expenses_by_categories.*
import kotlinx.android.synthetic.main.item_bar_chart.view.*
import kotlin.random.Random

@AndroidEntryPoint
class ExpensesByCategoriesActivity : BaseActionbarActivity() {

    private val viewModel: ExpensesByCategoriesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_expenses_by_categories)
        setSupportActionBar(tool_bar)
        super.onCreate(savedInstanceState)


        setupViews()
        attachListeners()
        subscribeObservers()
        viewModel.getPartnerCategories()

    }

    private fun setupViews() {
        rvCategories.adapter = categoriesAdapter

        setupChartPager()
        setupCardTags()
    }


    private fun attachListeners() {


    }


    private fun subscribeObservers() {


        viewModel.partnerCategoriesResp.observe(this, Observer {

            val response = it ?: return@Observer
            when (response) {
                is ErrorWrapper.ResponseError -> {
                }
                is ErrorWrapper.SystemError -> {
                }
                is ResultWrapper.Success -> {
                    loadData(response.value)

                }
                ResultWrapper.InProgress -> {
                }
            }.exhaustive


        })

    }

    private fun loadData(value: List<PartnerCategoryDTO>) {

        categoriesAdapter.clear()
        value.forEach {
            categoriesAdapter.add(ItemCategorySmall(it))
        }
        categoriesAdapter.notifyDataSetChanged()
    }

    val tagsAdapter = GroupAdapter<GroupieViewHolder>()
    val categoriesAdapter = GroupAdapter<GroupieViewHolder>()
    private fun setupCardTags() {

        rvCardTags.adapter = tagsAdapter

        val tempVals = arrayListOf("Benefit", "Zoom", "Cashback", "Детям", "Общее", "...")

        tempVals.forEach {
            tagsAdapter.add(CardTagItem(it) { cardItem ->
                for (i in 0 until tagsAdapter.itemCount) {
                    (tagsAdapter.getItem(i) as CardTagItem).selected = false
                }
                cardItem.selected = true
                tagsAdapter.notifyDataSetChanged()
            })
        }


        tagsAdapter.notifyDataSetChanged()
    }


    private fun setupChartPager() {

        val chartView = layoutInflater.inflate(R.layout.item_bar_chart, null)
        val chartView2 = layoutInflater.inflate(R.layout.item_bar_chart, null)


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

    private fun makeChart(chart: BarChart) {
        val entries = ArrayList<BarEntry>()
        for (i in 0..5) {
            // turn your data into Entry objects
            entries.add(BarEntry(i.toFloat(), Random.nextInt(0, 50).toFloat()))
        }

        val dataSet = BarDataSet(entries, "") // add entries to dataset
        dataSet.setDrawIcons(false)
        dataSet.setDrawValues(false)
        dataSet.label = ""
        dataSet.highLightColor = Color.parseColor("#f47964")
        dataSet.setGradientColor(Color.parseColor("#b9b2b2"), Color.parseColor("#d4cfcf"))
        dataSet.highLightAlpha = 255

        val lineData = BarData(dataSet)


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
}
