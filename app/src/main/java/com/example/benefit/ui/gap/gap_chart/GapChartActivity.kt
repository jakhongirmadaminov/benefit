package com.example.benefit.ui.gap.gap_chart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.benefit.R
import com.example.benefit.ui.base.BaseActionbarActivity
import kotlinx.android.synthetic.main.activity_gap_chart.*

class GapChartActivity : BaseActionbarActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_gap_chart)
        setSupportActionBar(tool_bar)
        super.onCreate(savedInstanceState)
    }
}