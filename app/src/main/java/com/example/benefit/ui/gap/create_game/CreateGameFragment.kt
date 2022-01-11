package com.example.benefit.ui.gap.create_game

/**
 * Created by jahon on 03-Sep-20
 */
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.benefit.R
import com.example.benefit.ui.base.BaseFragment
import com.example.benefit.ui.gap.gap_chart.GapChartActivity
import com.example.benefit.ui.viewgroups.ItemContactSquare
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_create_game.*
import splitties.fragments.start

class CreateGameFragment : BaseFragment(R.layout.fragment_create_game) {

    val args: CreateGameFragmentArgs by navArgs()
    private val viewModel: CreateGameViewModel by viewModels()
    val adapter = GroupAdapter<GroupieViewHolder>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupViews()

        attachListeners()
        subscribeObservers()
    }

    private fun setupViews() {
        rvContacts.adapter = adapter
        adapter.clear()

        args.selectedFriends?.forEach {
            adapter.add(ItemContactSquare(it))
        }

        adapter.add(ItemContactSquare {
            findNavController().navigate(
                CreateGameFragmentDirections.actionCreateGameFragmentToFindFriendsFragment(
                    args.selectedFriends
                )
            )
        })


    }

    private fun subscribeObservers() {


    }

    private fun attachListeners() {
//        llAdd.setOnClickListener {
//            findNavController().navigate(CreateGameFragmentDirections.actionCreateGameFragmentToFindFriendsFragment())
//        }

        tvCreate.setOnClickListener {
            start<GapChartActivity> {}
        }

    }

}