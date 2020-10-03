package com.example.benefit.ui.regular_payment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.benefit.R
import com.example.benefit.remote.models.RegularPaymentDTO
import com.example.benefit.ui.main.home.HomeFragment
import com.example.benefit.ui.main.home.card_options.CardOptionsBSD
import com.example.benefit.ui.viewgroups.ItemPayment
import com.example.benefit.util.SizeUtils
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_regular_payment.*
import kotlinx.android.synthetic.main.fragment_regular_payment.cardsPager
import javax.inject.Inject


/**
 * Created by jahon on 03-Sep-20
 */
@AndroidEntryPoint
class RegularPaymentFragment @Inject constructor() :
    Fragment(R.layout.fragment_regular_payment) {


//    val productId = args.productId

    private val adapter = GroupAdapter<GroupieViewHolder>()

    //    lateinit var transactionDTO: TransactionDTO
    private val viewModel: RegularPaymentViewModel by viewModels()
    lateinit var regularPaymentDTO: RegularPaymentDTO

    override fun onAttach(context: Context) {
        super.onAttach(context)

        regularPaymentDTO = requireArguments().getParcelable(RegularPaymentBSD.ARG_REGULAR_PAYMENT_DTO)!!

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupViews()

        attachListeners()
        subscribeObservers()
    }


    private fun attachListeners() {
        ivEdit.setOnClickListener {
            findNavController().navigate(
                RegularPaymentFragmentDirections.actionRegularPaymentFragmentToCreateRegPaymentEndFragment(
                    regularPaymentDTO
                )
            )
        }

    }

    private fun setupViews() {
        val cardView = layoutInflater.inflate(R.layout.item_card_small, null)

        cardView.setOnClickListener {
            CardOptionsBSD().show(childFragmentManager, "")
        }

        cardsPager.addView(cardView)
        val cardView2 = layoutInflater.inflate(R.layout.item_card_small, null)
        cardsPager.addView(cardView2)


        cardsPager.adapter = HomeFragment.WizardPagerAdapter(listOf(cardView, cardView2))
        cardsPager.offscreenPageLimit = 2
        cardsPager.clipToPadding = false
        cardsPager.setPadding(
            SizeUtils.dpToPx(requireContext(), 26).toInt(),
            0,
            SizeUtils.dpToPx(requireContext(), 26).toInt(),
            0
        )
        cardsPager.pageMargin = SizeUtils.dpToPx(requireContext(), 15).toInt()

        clParent.layoutParams = clParent.layoutParams.apply {
            height = SizeUtils.getScreenHeight(requireActivity()) - SizeUtils.getActionBarHeight(
                requireActivity()
            )

        }
//
//        rvPayments.adapter = adapter
//
//        val data = listOf(
//            RegularPaymentDTO("Телефон"),
//            RegularPaymentDTO("Интернет"),
//            RegularPaymentDTO("Электр.."),
//            RegularPaymentDTO("Электр..")
//        )
//        loadData(data)

    }

//    private fun loadData(data: List<RegularPaymentDTO>) {
//        adapter.clear()
//
//        data.forEach {
//            adapter.add(ItemPayment(it) {
//                findNavController().navigate(
//                    CreateRegularPaymentFragmentDirections.actionCreateRegularPaymentFragmentToCreateRegPaymentEndFragment(
//                        it
//                    )
//                )
//            })
//        }
//
//
//    }

    private fun subscribeObservers() {


    }


}
