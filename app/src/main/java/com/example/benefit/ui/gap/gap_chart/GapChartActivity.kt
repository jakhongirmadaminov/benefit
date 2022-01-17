package com.example.benefit.ui.gap.gap_chart

import android.os.Bundle
import com.example.benefit.R
import com.example.benefit.remote.models.GapGameDTO
import com.example.benefit.ui.base.BaseActionbarActivity
import kotlinx.android.synthetic.main.activity_gap_chart.*

const val EXTRA_GAP_GAME = "GAME"

class GapChartActivity : BaseActionbarActivity() {

    lateinit var game: GapGameDTO

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_gap_chart)
        setSupportActionBar(tool_bar)
        super.onCreate(savedInstanceState)

        game = intent?.getParcelableExtra(EXTRA_GAP_GAME) as GapGameDTO

        setupViews()
    }

    private fun setupViews() {

        tvTitle.text = game.name
        tvSum.text = game.summa
    }
}