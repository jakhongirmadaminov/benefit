package com.example.benefit.ui.loans

import android.os.Bundle
import com.example.benefit.R
import com.example.benefit.ui.base.BaseActionbarActivity
import com.example.benefit.ui.loans.loans_chart.LoansChartActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_loan.*
import splitties.activities.start


class LoanActivity : BaseActionbarActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_loan)
        setSupportActionBar(tool_bar)
        super.onCreate(savedInstanceState)


        attachListeners()
    }

    private fun attachListeners() {
        btnDetails.setOnClickListener {
            start<LoansChartActivity>{}
        }
    }


}