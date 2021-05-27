package com.example.benefit.ui.transactions_history.transaction_bsd

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.benefit.R
import com.example.benefit.remote.models.TransactionDTO
import com.example.benefit.ui.main.home.HomeFragment
import com.example.benefit.ui.viewgroups.ItemTransactionTxtOnly
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
import kotlinx.android.synthetic.main.fragment_transaction.*
import kotlinx.android.synthetic.main.item_line_chart.view.*
import javax.inject.Inject
import kotlin.random.Random


/**
 * Created by jahon on 03-Sep-20
 */
import com.example.benefit.ui.base.BaseFragment

class TransactionFragment : BaseFragment(R.layout.fragment_transaction) {

//    val args by navArgs<TransactionFragmentArgs>()
//    val productId = args.productId

    override fun onAttach(context: Context) {
        super.onAttach(context)

        transactionDTO = requireArguments().getParcelable(TransactionBSD.ARG_TRANSACTION_DTO)!!
    }

    private val adapter = GroupAdapter<GroupieViewHolder>()
    lateinit var transactionDTO: TransactionDTO
    private val viewModel: TransactionViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupViews()

        attachListeners()
        subscribeObservers()
    }

    private fun setupViews() {

        rvAllTransactions.adapter = adapter

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

        data.forEach {
            adapter.add(ItemTransactionTxtOnly(it))
        }

        setupChartPager()

    }

    private fun subscribeObservers() {


    }

    private fun attachListeners() {

        ivMore.setOnClickListener {
            findNavController().navigate(
                TransactionFragmentDirections.actionTransactionFragmentToTransactionMoreInfoFragment(
                    transactionDTO
                )
            )
        }
        ivSelectCategories.setOnClickListener {
            findNavController().navigate(
                TransactionFragmentDirections.actionTransactionFragmentToTransactionSelectCategoryFragment(
                    transactionDTO
                )
            )
        }
        ivPeople.setOnClickListener {
            findNavController().navigate(
                TransactionFragmentDirections.actionTransactionFragmentToTransactionSharePaymentFragment(
                    transactionDTO
                )
            )
        }
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
            SizeUtils.dpToPx(requireContext(), 26).toInt(),
            0,
            SizeUtils.dpToPx(requireContext(), 26).toInt(),
            0
        )
        chartPager.pageMargin = SizeUtils.dpToPx(requireContext(), 26).toInt()

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


        val gradientColor =
            when (transactionDTO.transactionType) {
                TransactionType.TRANSFER_TO_CARD -> {
                    R.drawable.gradient_line_chart_orange
                }
                TransactionType.COMMERCIAL_PAYMENT -> {
                    R.drawable.gradient_line_chart_pink
                }
                else -> {
                    R.drawable.gradient_line_chart_pink
                }
            }

        dataSet.fillDrawable =
            ContextCompat.getDrawable(requireContext(), gradientColor)
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


        chart.setGridBackgroundColor(ContextCompat.getColor(requireContext(), R.color.light_grey))
        chart.legend.isEnabled = false
        chart.invalidate() // refresh


    }

}

enum class TransactionType {
    NONE, TRANSFER_TO_CARD, COMMERCIAL_PAYMENT
}