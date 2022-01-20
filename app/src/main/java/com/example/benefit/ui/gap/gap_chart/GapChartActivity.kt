package com.example.benefit.ui.gap.gap_chart

import android.os.Bundle
import com.example.benefit.R
import com.example.benefit.remote.models.GapGameDTO
import com.example.benefit.remote.models.Member
import com.example.benefit.ui.base.BaseActionbarActivity
import com.example.benefit.ui.viewgroups.ItemGapPlayer
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_gap_chart.*

const val EXTRA_GAP_GAME = "GAME"

class GapChartActivity : BaseActionbarActivity() {

    private val adapter = GroupAdapter<GroupieViewHolder>()
    lateinit var game: GapGameDTO

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_gap_chart)
        setSupportActionBar(tool_bar)
        super.onCreate(savedInstanceState)

        game = intent?.getParcelableExtra(EXTRA_GAP_GAME) as GapGameDTO

        setupViews()
    }

    private fun setupViews() {
        chart.game = game
        tvTitle.text = game.name
        tvSum.text = game.summa + " UZS"
        rvPlayers.adapter = adapter

        game.members?.let {
            loadMembers(it)
        }
    }

    private fun loadMembers(data: List<Member>) {
        adapter.clear()
        data.forEach {
            adapter.add(ItemGapPlayer(it))
        }


    }


}