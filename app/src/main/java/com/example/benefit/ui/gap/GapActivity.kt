package com.example.benefit.ui.gap

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.example.benefit.R
import com.example.benefit.remote.models.GapGameDTO
import com.example.benefit.ui.base.BaseActionbarActivity
import com.example.benefit.ui.gap.create_game.CreateGameBSD
import com.example.benefit.ui.gap.gap_chart.EXTRA_GAP_GAME
import com.example.benefit.ui.gap.gap_chart.GapChartActivity
import com.example.benefit.ui.viewgroups.ItemGapGame
import com.example.benefit.ui.viewgroups.ItemLoading
import com.example.benefit.util.RequestState
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_gap.*
import splitties.activities.start


class GapActivity : BaseActionbarActivity() {


    private val adapter = GroupAdapter<GroupieViewHolder>()
    val viewModel: GapViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_gap)
        setSupportActionBar(tool_bar)
        super.onCreate(savedInstanceState)


        setupViews()
        attachListeners()
        subscribeObservers()
    }

    private fun subscribeObservers() {
        viewModel.games.observe(this) {
            when (val resp = it) {
                is RequestState.Error -> {
                    adapter.clear()
                    Toast.makeText(this, resp.message, Toast.LENGTH_SHORT).show()
                }
                RequestState.Loading -> {
                    adapter.clear()
                    adapter.add(ItemLoading())
                }
                is RequestState.Success -> {
                    loadData(resp.value)
                }
            }
        }
    }

    private fun loadData(resp: List<GapGameDTO>) {
        adapter.clear()
        resp.forEach { game ->
            adapter.add(ItemGapGame(game) {
                start<GapChartActivity> {
                    putExtra(EXTRA_GAP_GAME, game)
                }
            })
        }
    }

    private fun setupViews() {
        rvGames.adapter = adapter

    }

    private fun attachListeners() {
        icMore.setOnClickListener {
            GameRulesBSD().show(supportFragmentManager, "")
        }

        btnNewLimit.setOnClickListener {
            CreateGameBSD().show(supportFragmentManager, "")
        }
    }


}