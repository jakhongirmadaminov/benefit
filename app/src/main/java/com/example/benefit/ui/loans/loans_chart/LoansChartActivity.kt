package com.example.benefit.ui.loans.loans_chart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.benefit.R
import com.example.benefit.ui.base.BaseActionbarActivity
import com.example.benefit.ui.main.fill_card.FillCardBSD
import com.example.benefit.ui.main.transfer_to_card.TransferToCardBSD
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_loans_chart.*
import splitties.activities.start
import kotlin.random.Random
import kotlin.random.nextUInt

@AndroidEntryPoint
class LoansChartActivity : BaseActionbarActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_loans_chart)
        setSupportActionBar(tool_bar)
        super.onCreate(savedInstanceState)

        circularProgress.setProgress(Random.nextDouble(0.1, 0.99).toFloat())
        attachListeners()
    }


    private fun attachListeners() {
        btnPay.setOnClickListener {
            FillCardBSD().show(supportFragmentManager, "")
        }
    }
}