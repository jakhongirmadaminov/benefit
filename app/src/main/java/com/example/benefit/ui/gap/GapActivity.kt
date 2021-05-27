package com.example.benefit.ui.gap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.benefit.R
import com.example.benefit.ui.base.BaseActionbarActivity
import com.example.benefit.ui.gap.create_game.CreateGameBSD
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_gap.*



class GapActivity : BaseActionbarActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_gap)
        setSupportActionBar(tool_bar)
        super.onCreate(savedInstanceState)


        icMore.setOnClickListener {
            GameRulesBSD().show(supportFragmentManager, "")
        }

        btnNewLimit.setOnClickListener {
            CreateGameBSD().show(supportFragmentManager, "")
        }

    }


}