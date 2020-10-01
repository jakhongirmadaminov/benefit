package com.example.benefit.ui.transactions_history.transaction_bsd

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.benefit.R
import com.example.benefit.remote.models.FriendDTO
import com.example.benefit.remote.models.FriendDTOs
import com.example.benefit.remote.models.TransactionDTO
import com.example.benefit.ui.main.home.HomeFragment
import com.example.benefit.ui.viewgroups.FriendItem
import com.example.benefit.ui.viewgroups.FriendItemImgName
import com.example.benefit.ui.viewgroups.FriendItemWithAmount
import com.example.benefit.ui.viewgroups.ItemTransactionTxtOnly
import com.example.benefit.util.SizeUtils
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.android.material.snackbar.Snackbar
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_transaction_share_payment_selected.*
import javax.inject.Inject
import kotlin.random.Random


/**
 * Created by jahon on 03-Sep-20
 */
@AndroidEntryPoint
class TransactionSharePaymentSelectedFragment @Inject constructor() :
    Fragment(R.layout.fragment_transaction_share_payment_selected) {

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
                val contacts = FriendDTOs()
                for (i in 0 until horizontalAdapter.itemCount) {
                    contacts.add((horizontalAdapter.getItem(i) as FriendItemImgName).friend)
                }
                findNavController().navigate(TransactionSharePaymentSelectedFragmentDirections.actionTransactionSharePaymentSelectedFragmentToTransactionSharePaymentEndFragment(contacts))
            }


        }


    }


}
