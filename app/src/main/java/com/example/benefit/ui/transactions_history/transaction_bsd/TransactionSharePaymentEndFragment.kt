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
import kotlinx.android.synthetic.main.fragment_transaction_select_category.*
import kotlinx.android.synthetic.main.fragment_transaction_share_payment_end.*
import kotlinx.android.synthetic.main.fragment_transaction_share_payment_end.clParent
import kotlinx.android.synthetic.main.fragment_transaction_share_payment_end.ivBack
import javax.inject.Inject
import kotlin.random.Random


/**
 * Created by jahon on 03-Sep-20
 */
@AndroidEntryPoint
class TransactionSharePaymentEndFragment @Inject constructor() :
    Fragment(R.layout.fragment_transaction_share_payment_end) {

    private val adapter = GroupAdapter<GroupieViewHolder>()
    val args: TransactionSharePaymentEndFragmentArgs by navArgs()
    private val viewModel: TransactionViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupViews()

        attachListeners()
        subscribeObservers()
    }

    private fun setupViews() {
        clParent.layoutParams = clParent.layoutParams.apply {
            height = SizeUtils.getScreenHeight(requireActivity()) - SizeUtils.getActionBarHeight(
                requireActivity()
            )

        }
        setupContacts()

    }

    private fun setupContacts() {

        rvPaymentSharingContacts.adapter = adapter
        adapter.clear()
        args.contacts.forEach {
            adapter.add(FriendItemWithAmount(it))
        }

    }


    private fun subscribeObservers() {


    }

    private fun attachListeners() {
        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }


        tvShare.setOnClickListener {


        }
        ivEqualize.setOnClickListener {



        }


    }


}
