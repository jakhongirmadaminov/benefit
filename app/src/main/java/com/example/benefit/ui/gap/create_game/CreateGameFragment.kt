package com.example.benefit.ui.gap.create_game

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.benefit.R
import com.example.benefit.ui.branches_atms.BranchesAtmsActivity
import com.example.benefit.ui.gap.gap_chart.GapChartActivity
import com.example.benefit.ui.main.home.HomeFragment
import com.example.benefit.ui.main.home.card_options.CardOptionsBSD
import com.example.benefit.util.SizeUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_create_game.*
import splitties.fragments.start
import javax.inject.Inject

/**
 * Created by jahon on 03-Sep-20
 */
import com.example.benefit.ui.base.BaseFragment

class CreateGameFragment : BaseFragment(R.layout.fragment_create_game) {


    private val viewModel: CreateGameViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupViews()

        attachListeners()
        subscribeObservers()
    }

    private fun setupViews() {


    }

    private fun subscribeObservers() {


    }

    private fun attachListeners() {
        llAdd.setOnClickListener {
            findNavController().navigate(CreateGameFragmentDirections.actionCreateGameFragmentToFindFriendsFragment())
        }

        tvCreate.setOnClickListener {
           start<GapChartActivity>{}
        }

    }

}