package com.example.benefit.ui.transactions_history.transaction_bsd

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.benefit.R
import com.example.benefit.remote.models.Friends
import com.example.benefit.ui.base.BaseFragment
import com.example.benefit.ui.viewgroups.FriendItem
import com.example.benefit.ui.viewgroups.FriendItemImgName
import com.google.android.material.snackbar.Snackbar
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_transaction_share_payment_selected.*
import javax.inject.Inject


/**
 * Created by jahon on 03-Sep-20
 */

class TransactionSharePaymentSelectedFragment @Inject constructor() :
    BaseFragment(R.layout.fragment_transaction_share_payment_selected) {

    private val adapter = GroupAdapter<GroupieViewHolder>()
    private val horizontalAdapter = GroupAdapter<GroupieViewHolder>()
    val args: TransactionSharePaymentSelectedFragmentArgs by navArgs()
    private val viewModel: TransactionViewModel by viewModels()

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
        rvSelected.adapter = horizontalAdapter
        adapter.clear()
        args.contacts.forEach {
            adapter.add(FriendItem(it) {
                populateSelectedList()
            })
        }
        populateSelectedList()

    }

    private fun populateSelectedList() {
        horizontalAdapter.clear()
        for (i in 0 until adapter.itemCount) {
            if ((adapter.getItem(i) as FriendItem).friend.isChecked) horizontalAdapter.add(
                FriendItemImgName((adapter.getItem(i) as FriendItem).friend)
            )
        }
        tvSelected.text = getString(R.string.selected) + " " + horizontalAdapter.itemCount
    }

    private fun subscribeObservers() {


    }

    private fun attachListeners() {
        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }


        tvSelect.setOnClickListener {

            if (horizontalAdapter.itemCount == 0) {
                Snackbar.make(
                    clParent,
                    getString(R.string.select_at_least_one_contact),
                    Snackbar.LENGTH_SHORT
                ).show()
            }else{
                val contacts = Friends()
                for (i in 0 until horizontalAdapter.itemCount) {
                    contacts.add((horizontalAdapter.getItem(i) as FriendItemImgName).friend)
                }
                findNavController().navigate(TransactionSharePaymentSelectedFragmentDirections.actionTransactionSharePaymentSelectedFragmentToTransactionSharePaymentEndFragment(contacts))
            }


        }


    }


}
