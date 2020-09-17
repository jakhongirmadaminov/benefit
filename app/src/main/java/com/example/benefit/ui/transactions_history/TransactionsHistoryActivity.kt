package com.example.benefit.ui.transactions_history

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.benefit.R
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.android.synthetic.main.activity_transactions_history.*
import kotlin.random.Random


class TransactionsHistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transactions_history)

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        setupViews()

    }

    private fun setupViews() {

        makeChart()
    }

    private fun makeChart() {


        val entries = ArrayList<Entry>()


        for (i in 0..20) {
            // turn your data into Entry objects
            entries.add(Entry(i.toFloat(), Random.nextInt(0, 50).toFloat()))
        }

        val dataSet = LineDataSet(entries, "Label") // add entries to dataset
        dataSet.color = ContextCompat.getColor(this, R.color.colorAccent)
//        dataSet.setValueTextColor()


        dataSet.cubicIntensity = 0.5F
        val lineData = LineData(dataSet)

        chart.data = lineData
        chart.invalidate() // refresh


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}