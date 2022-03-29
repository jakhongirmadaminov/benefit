package com.example.benefit.ui.transactions_history.transaction_bsd


/**
 * Created by jahon on 03-Sep-20
 */
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.benefit.R
import com.example.benefit.remote.models.TransactionAnalyticsDTO
import com.example.benefit.ui.base.BaseFragment
import com.example.benefit.ui.main.home.HomeFragment
import com.example.benefit.ui.transactions_history.TransactionsHistoryViewModel
import com.example.benefit.ui.viewgroups.ItemTransaction
import com.example.benefit.ui.viewgroups.ItemTransactionDate
import com.example.benefit.util.ResultSuccess
import com.example.benefit.util.SizeUtils
import com.example.benefit.util.loadImageUrl
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
import kotlinx.android.synthetic.main.fragment_transaction.*
import kotlinx.android.synthetic.main.item_line_chart.view.*
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import java.text.DecimalFormat

class TransactionFragment : BaseFragment(R.layout.fragment_transaction),
    OnChartValueSelectedListener {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        transactionDTO = requireArguments().getParcelable(TransactionBSD.ARG_TRANSACTION_DTO)!!
    }

    private var selectedMonthOffset = 0
    lateinit var transactionDTO: TransactionAnalyticsDTO
    private val activityViewModel: TransactionsHistoryViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()

        attachListeners()
        subscribeObservers()


    }

    private fun setupViews() {

        rvAllTransactions.adapter = transactionsAdapter
        setupTransactionInfo()

        setupChartPager((activityViewModel.transactionsAnalyticsResp.value as ResultSuccess).value)
    }

    private fun setupTransactionInfo() {
        if (transactionDTO.partner?.image.isNullOrBlank()) {
            ivBrandLogo.isVisible = false
        } else {
            ivBrandLogo.isVisible = true
            ivBrandLogo.loadImageUrl(transactionDTO.partner!!.image!!)
        }

        tvCardNumber.text = transactionDTO.hpan
        tvDate.text = transactionDTO.dateFormatted + " " + transactionDTO.timeFormatted
        tvSum.text =
            (if (transactionDTO.isCredit != true) "- " else "") + DecimalFormat("#,###").format(
                transactionDTO.amountWithoutTiyin
            ) + " UZS"

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

        ivSharePayment.setOnClickListener {


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


    private fun setupChartPager(value: ArrayList<ArrayList<TransactionAnalyticsDTO>>) {
        chartPager.adapter = null
        chartPager.removeAllViews()
        val chartView = layoutInflater.inflate(R.layout.item_line_chart, null)
        val chartView2 = layoutInflater.inflate(R.layout.item_line_chart, null)

        chartView.llMonths.children.forEachIndexed { index, view ->
            (view as? TextView)?.text =
                DateTimeFormat.forPattern("MMM")
                    .print(DateTime(DateTime.now().year, index + 1, 1, 0, 0))
        }

        chartView2.llMonths.children.forEachIndexed { index, view ->
            (view as? TextView)?.text =
                DateTimeFormat.forPattern("MMM")
                    .print(DateTime(DateTime.now().year, 6 + index + 1, 1, 0, 0))
        }

        chartView.radioGroup.children.forEachIndexed { index, view ->
            (view as RadioButton).setOnCheckedChangeListener { compoundButton, b ->
                if (b) {
                    selectedMonthOffset = index
                    loadTransactions(
                        (activityViewModel.transactionsAnalyticsResp.value as ResultSuccess).value[selectedMonthOffset]
                    )
                }
            }
        }
        chartView2.radioGroup.children.forEachIndexed { index, view ->
            (view as RadioButton).setOnCheckedChangeListener { compoundButton, b ->
                if (b) {
                    selectedMonthOffset = index + 5
                    loadTransactions(
                        (activityViewModel.transactionsAnalyticsResp.value as ResultSuccess).value[selectedMonthOffset]
                    )
                }
            }
        }



        makeChart(chartView.chart, value.dropLast(6))
        makeChart(chartView2.chart, value.drop(6))

        chartPager.addView(chartView)
        chartPager.addView(chartView2)


        chartPager.adapter = HomeFragment.WizardPagerAdapter(listOf(chartView, chartView2))
        chartPager.offscreenPageLimit = 2
        chartPager.clipToPadding = false
        chartPager.setPadding(
            SizeUtils.dpToPx(requireActivity(), 26).toInt(),
            0,
            SizeUtils.dpToPx(requireActivity(), 26).toInt(),
            0
        )
        chartPager.pageMargin = SizeUtils.dpToPx(requireActivity(), 26).toInt()
        chartPager.currentItem = if (DateTime.now().monthOfYear < 7) 0 else 1
        loadTransactions((activityViewModel.transactionsAnalyticsResp.value as ResultSuccess).value[0])
        showExpenses(DateTime.now().monthOfYear - 1)

    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        e?.let {
            showExpenses(it.x.toInt())
        }
    }

    private fun showExpenses(barIndex: Int) {
        if (chartPager.currentItem == 0) {
            chartPager[0].radioGroup.children.forEachIndexed { index, view ->
                (view as RadioButton).isChecked = index == barIndex
            }
            selectedMonthOffset = barIndex
        } else {
            chartPager[1].radioGroup.children.forEachIndexed { index, view ->
                (view as RadioButton).isChecked = index == barIndex
            }
            selectedMonthOffset = barIndex + 5
        }
        loadTransactions((activityViewModel.transactionsAnalyticsResp.value as ResultSuccess).value[selectedMonthOffset])
    }


    override fun onNothingSelected() {

    }

    val transactionsAdapter = GroupAdapter<GroupieViewHolder>()
    private fun loadTransactions(value: List<TransactionAnalyticsDTO>) {
        transactionsAdapter.clear()
        var dateString: Int? = null
        value.forEach {
            if (transactionDTO.merchant == it.merchant) {
                if (it.udate != dateString) {
                    transactionsAdapter.add(ItemTransactionDate(it.udate!!))
                    dateString = it.udate
                }
                transactionsAdapter.add(ItemTransaction(it))
            }
        }

        var totalExpense = 0L
        var totalIncome = 0L
        value.forEach {
            if (!it.isCredit!!) {
                totalExpense += it.amountWithoutTiyin
            } else if (it.isCredit) {
                totalIncome += it.amountWithoutTiyin
            }
        }
    }


    private fun makeChart(chart: LineChart, value: List<ArrayList<TransactionAnalyticsDTO>>) {
        val entries = ArrayList<Entry>()
        if (value.any { transactions ->
                transactions.sumOf { it.amountWithoutTiyin }.toFloat() > 0
            }) {

            value.forEachIndexed { index, item ->
                var total = 0L
                value[index].forEach {
                    if (transactionDTO.merchant == it.merchant) total += it.amountWithoutTiyin!!
                }
                entries.add(Entry(index.toFloat(), total.toFloat()))
            }
        }

        val dataSet = LineDataSet(entries, "")

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

        dataSet.fillDrawable =
            ContextCompat.getDrawable(requireActivity(), R.drawable.gradient_orange)
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
        chart.setGridBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.peach))
        chart.legend.isEnabled = false
        chart.invalidate() // refresh

    }


}

enum class TransactionType {
    NONE, TRANSFER_TO_CARD, COMMERCIAL_PAYMENT
}