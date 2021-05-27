package com.example.benefit.ui.gap.create_game

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.benefit.R
import com.example.benefit.remote.models.FriendDTO
import com.example.benefit.remote.models.FriendsDTOs
import com.example.benefit.ui.viewgroups.FriendItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_transaction_share_payment.*
import javax.inject.Inject


/**
 * Created by jahon on 03-Sep-20
 */

class FindFriendsFragment @Inject constructor() :
    Fragment(R.layout.fragment_find_friends) {

    private val adapter = GroupAdapter<GroupieViewHolder>()

    //    val args: FindFriendsFragmentArgs by navArgs()
    private val viewModel: CreateGameViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupViews()

        attachListeners()
        subscribeObservers()
    }

    private fun setupViews() {

        setupContacts()

    }

    private fun setupContacts() {

        rvContacts.adapter = adapter

        val data = listOf(
            FriendDTO("Алексей", "Иванов", "", ""),
            FriendDTO("Константин", "Игнатьев", "", ""),
            FriendDTO("Марина", "Авазова", "", ""),
            FriendDTO("Екатерина", "Петрова", "", ""),
            FriendDTO("Марат", "Измайлов", "", ""),
            FriendDTO("Валентин", "Автухов", "", ""),
        )

        data.forEach {
            adapter.add(FriendItem(it) {
            })
        }


    }

    private fun subscribeObservers() {


    }

    private fun attachListeners() {
        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
        ivLocation.setOnClickListener {
//            findNavController().navigate(
//                TransactionSharePaymentFragmentDirections.actionTransactionSharePaymentFragmentToTransactionSearchFriendNearbyFragment()
//            )
        }

        tvSelect.setOnClickListener {
            val contacts = FriendsDTOs()

            for (i in 0 until adapter.itemCount) {
                contacts.add((adapter.getItem(i) as FriendItem).friend)
            }

//            findNavController().navigate(
//                TransactionSharePaymentFragmentDirections.actionTransactionSharePaymentFragmentToTransactionSharePaymentSelectedFragment(
//                    contacts
//                )
//            )
        }
    }


}
