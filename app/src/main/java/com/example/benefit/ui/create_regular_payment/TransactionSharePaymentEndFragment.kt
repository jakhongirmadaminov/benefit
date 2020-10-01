//package com.example.benefit.ui.create_regular_payment
//
//import android.os.Bundle
//import android.view.View
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.viewModels
//import androidx.navigation.fragment.findNavController
//import androidx.navigation.fragment.navArgs
//import com.example.benefit.R
//import com.example.benefit.ui.viewgroups.FriendItemWithAmount
//import com.example.benefit.util.SizeUtils
//import com.xwray.groupie.GroupAdapter
//import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
//import dagger.hilt.android.AndroidEntryPoint
//import kotlinx.android.synthetic.main.fragment_transaction_share_payment_end.*
//import kotlinx.android.synthetic.main.fragment_transaction_share_payment_end.clParent
//import kotlinx.android.synthetic.main.fragment_transaction_share_payment_end.ivBack
//import javax.inject.Inject
//
//
///**
// * Created by jahon on 03-Sep-20
// */
//@AndroidEntryPoint
//class TransactionSharePaymentEndFragment @Inject constructor() :
//    Fragment(R.layout.fragment_transaction_share_payment_end) {
//
//    private val adapter = GroupAdapter<GroupieViewHolder>()
//    val args: TransactionSharePaymentEndFragmentArgs by navArgs()
//    private val viewModel: CreateRegularPaymentViewModel by viewModels()
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//
//        setupViews()
//
//        attachListeners()
//        subscribeObservers()
//    }
//
//    private fun setupViews() {
//        clParent.layoutParams = clParent.layoutParams.apply {
//            height = SizeUtils.getScreenHeight(requireActivity()) - SizeUtils.getActionBarHeight(
//                requireActivity()
//            )
//
//        }
//        setupContacts()
//
//    }
//
//    private fun setupContacts() {
//
//        rvPaymentSharingContacts.adapter = adapter
//        adapter.clear()
//        args.contacts.forEach {
//            adapter.add(FriendItemWithAmount(it))
//        }
//
//    }
//
//
//    private fun subscribeObservers() {
//
//
//    }
//
//    private fun attachListeners() {
//        ivBack.setOnClickListener {
//            findNavController().popBackStack()
//        }
//
//
//        tvShare.setOnClickListener {
//
//
//        }
//        ivEqualize.setOnClickListener {
//
//
//
//        }
//
//
//    }
//
//
//}
