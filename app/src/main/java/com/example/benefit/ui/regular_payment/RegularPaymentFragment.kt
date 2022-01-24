package com.example.benefit.ui.regular_payment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.benefit.R
import com.example.benefit.remote.models.AutoPaymentDTO
import com.example.benefit.remote.models.BalanceInfo
import com.example.benefit.remote.models.CardDTO
import com.example.benefit.ui.base.BaseFragment
import com.example.benefit.ui.main.home.HomeFragment
import com.example.benefit.util.SizeUtils
import com.example.benefit.util.loadImageUrl
import kotlinx.android.synthetic.main.fragment_regular_payment.*
import kotlinx.android.synthetic.main.item_card_small.view.*
import java.text.DecimalFormat
import javax.inject.Inject


/**
 * Created by jahon on 03-Sep-20
 */

class RegularPaymentFragment @Inject constructor() :
        BaseFragment(R.layout.fragment_regular_payment) {

    val args: RegularPaymentFragmentArgs by navArgs()
    private val viewModel: RegularPaymentViewModel by viewModels()
    lateinit var regularPaymentDTO: AutoPaymentDTO

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
        layoutCalculator.edtSum = edtSum
        tvServiceName.text = args.autoPaymentDTO.providerInfo!!.titleShort
        tvServiceFullName.text = args.autoPaymentDTO.providerInfo!!.title
        tvCategoryName.text = args.autoPaymentDTO.providerInfo!!.getLocalizedCatgName()
        ivTargetService.loadImageUrl(args.autoPaymentDTO.providerInfo!!.image!!)
    }

    private fun subscribeObservers() {

        viewModel.bftAndMyCardsPair.observe(viewLifecycleOwner) { requestState ->
            progressCards.isVisible = false
            setupCardsPager(requestState.first, requestState.second.getProperly())
        }

        viewModel.isGettingsCards.observe(viewLifecycleOwner) {
            progressCards.isVisible = it
        }

    }

    private fun setupCardsPager(bftInfo: BalanceInfo, cardsDTO: List<CardDTO>) {

        val cards = arrayListOf<View>().apply {
            val cardView = layoutInflater.inflate(R.layout.item_card_small, null)
            cardView.cardName.text = getString(R.string.cashback_points)
            cardView.tvAmount.text = DecimalFormat("#,###").format(bftInfo.summa) + " BFT"
            cardView.tvCardEndNum.text = ""
            cardView.ivCardBg.setImageResource(R.drawable.gradient_orange)
            cardsPager.addView(cardView)
            add(cardView)
        }

        cardsDTO.forEach {
            val cardView = layoutInflater.inflate(R.layout.item_card_small, null)
            cardView.cardName.text = it.card_title
            cardView.tvAmount.text =
                    DecimalFormat("#,###").format(it.balance?.dropLast(2)?.toInt()) + " UZS"
            cardView.tvCardEndNum.text = "*" + it.panHidden!!.substring(it.panHidden!!.length - 4)
            it.setMiniBackgroundInto(cardView.ivCardBg)
            cardsPager.addView(cardView)
            cards.add(cardView)
        }

        cardsPager.adapter = HomeFragment.WizardPagerAdapter(cards)
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


}
