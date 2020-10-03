package com.example.benefit.ui.gap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.benefit.R
import com.example.benefit.ui.base.BaseActionbarActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class GapActivity : BaseActionbarActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_gap)
        super.onCreate(savedInstanceState)
    }
}