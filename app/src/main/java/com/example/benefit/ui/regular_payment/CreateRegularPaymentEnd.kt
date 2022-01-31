package com.example.benefit.ui.regular_payment

import android.animation.LayoutTransition.CHANGING
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.benefit.R
import com.example.benefit.ui.base.BaseFragment
import com.example.benefit.ui.main.home.HomeFragment
import com.example.benefit.ui.main.home.card_options.CardOptionsBSD
import com.example.benefit.util.SizeUtils
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_create_regular_payment_end.*
import javax.inject.Inject


/**
 * Created by jahon on 03-Sep-20
 */

class CreateRegularPaymentEnd @Inject constructor() :
    BaseFragment(R.layout.fragment_create_regular_payment_end) {

    val args by navArgs<CreateRegularPaymentEndArgs>()
    private val viewModel: CreateRegularPaymentViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupViews()

        attachListeners()
        subscribeObservers()
    }


    private fun attachListeners() {

        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        switchSetup.setOnCheckedChangeListener { buttonView, isChecked ->
            clSetup.visibility = if (isChecked) View.VISIBLE else View.GONE
        }

    }

    private fun setupViews() {

//        tvBrandName.text = args.paynetCategory.

        clParent.layoutTransition.enableTransitionType(CHANGING)
        clParent.minimumHeight =
            SizeUtils.getScreenHeight(requireActivity()) - SizeUtils.getActionBarHeight(
                requireActivity()
            )


        val cardView = layoutInflater.inflate(R.layout.item_card_small, null)

        cardView.setOnClickListener {
            CardOptionsBSD().show(childFragmentManager, "")
        }

        cardsPager.addView(cardView)
        val cardView2 = layoutInflater.inflate(R.layout.item_card_small, null)
        cardsPager.addView(cardView2)


        cardsPager.adapter = HomeFragment.WizardPagerAdapter(listOf(cardView, cardView2))
        cardsPager.offscreenPageLimit = 10
        cardsPager.clipToPadding = false
        cardsPager.setPadding(
            SizeUtils.dpToPx(requireContext(), 26).toInt(),
            0,
            SizeUtils.dpToPx(requireContext(), 26).toInt(),
            0
        )
        cardsPager.pageMargin = SizeUtils.dpToPx(requireContext(), 15).toInt()
    }


    private fun subscribeObservers() {


    }


}
