package uz.magnumactive.benefit.ui.gap.gap_chart

import android.os.Bundle
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.remote.models.GapGameDTO
import uz.magnumactive.benefit.remote.models.Member
import uz.magnumactive.benefit.ui.base.BaseActionbarActivity
import uz.magnumactive.benefit.ui.viewgroups.ItemGapPlayer
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_gap_chart.chart
import kotlinx.android.synthetic.main.activity_gap_chart.rvPlayers
import kotlinx.android.synthetic.main.activity_gap_chart.tool_bar
import kotlinx.android.synthetic.main.activity_gap_chart.tvSum
import kotlinx.android.synthetic.main.activity_gap_chart.tvTitle

const val EXTRA_GAP_GAME = "GAME"

class GapChartActivity : BaseActionbarActivity() {

    private val adapter = GroupAdapter<GroupieViewHolder>()
    lateinit var game: GapGameDTO

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_gap_chart)
        setSupportActionBar(tool_bar)
        super.onCreate(savedInstanceState)

        game = intent.getParcelableExtra(EXTRA_GAP_GAME)!!

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