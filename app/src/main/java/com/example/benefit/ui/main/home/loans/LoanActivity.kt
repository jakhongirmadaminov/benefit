package com.example.benefit.ui.main.home.loans

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.benefit.R
import com.example.benefit.ui.base.BaseActionbarActivity
import kotlinx.android.synthetic.main.activity_loan.*

class LoanActivity : BaseActionbarActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_loan)
        setSupportActionBar(tool_bar)
        super.onCreate(savedInstanceState)
    }



}