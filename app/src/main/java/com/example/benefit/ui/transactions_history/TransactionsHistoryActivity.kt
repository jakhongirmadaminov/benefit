package com.example.benefit.ui.transactions_history

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.benefit.R
import com.example.benefit.ui.viewgroups.CardTagItem
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_transactions_history.*
import kotlin.random.Random


class TransactionsHistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transactions_history)

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.elevation = 0F


        setupViews()
        attachListeners()
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

        cbMonthSpent.performClick()
        setupCardTags()
        makeChart()
    }


    val adapter = GroupAdapter<GroupieViewHolder>()
    private fun setupCardTags() {

        rvCardTags.adapter = adapter

        val tempVals = arrayListOf("Benefit", "Zoom", "Cashback", "Детям", "Общее", "...")

        tempVals.forEach {
            adapter.add(CardTagItem(it) { cardItem ->
                for (i in 0 until adapter.itemCount) {
                    (adapter.getItem(i) as CardTagItem).selected = false
                }
                cardItem.selected = true
                adapter.notifyDataSetChanged()
            })
        }


        adapter.notifyDataSetChanged()
    }

    private fun makeChart() {
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

        dataSet.fillDrawable = ContextCompat.getDrawable(this, R.drawable.gradient_turquoise)
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
        chart.setBackgroundResource(R.drawable.gradient_fuchsia)
        chart.invalidate() // refresh


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}