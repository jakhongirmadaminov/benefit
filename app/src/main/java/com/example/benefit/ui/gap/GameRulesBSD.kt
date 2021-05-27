package com.example.benefit.ui.gap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.benefit.R
import com.example.benefit.util.MyBSDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.bsd_fill_card.*



class GameRulesBSD : MyBSDialog() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bsd_game_rules, null)

        return view
    }




}