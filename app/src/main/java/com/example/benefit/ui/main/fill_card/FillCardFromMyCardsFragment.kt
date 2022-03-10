package com.example.benefit.ui.main.fill_card

/**
 * Created by jahon on 03-Sep-20
 */
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager.widget.ViewPager
import com.example.benefit.R
import com.example.benefit.ui.base.BaseFragment
import com.example.benefit.ui.main.home.HomeFragment
import com.example.benefit.util.ResultError
import com.example.benefit.util.ResultSuccess
import com.example.benefit.util.SizeUtils
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_fill_card_ask_friends_transfer.*
import kotlinx.android.synthetic.main.fragment_fill_from_any_card_transfer.*
import kotlinx.android.synthetic.main.fragment_fill_from_my_cards.*
import kotlinx.android.synthetic.main.fragment_fill_from_my_cards.cardsToPagerSmall
import kotlinx.android.synthetic.main.fragment_fill_from_my_cards.clParent
import kotlinx.android.synthetic.main.fragment_fill_from_my_cards.edtSum
import kotlinx.android.synthetic.main.fragment_fill_from_my_cards.ivBack
import kotlinx.android.synthetic.main.fragment_fill_from_my_cards.layoutCalculator
import kotlinx.android.synthetic.main.fragment_fill_from_my_cards.tvFill
import kotlinx.android.synthetic.main.fragment_fill_from_my_cards.tvMinAmount
import kotlinx.android.synthetic.main.item_card_small.view.*
import kotlinx.android.synthetic.main.layout_calculator.*
import kotlinx.android.synthetic.main.layout_calculator.view.*
import kotlinx.android.synthetic.main.transaction_loading.*
import kotlinx.android.synthetic.main.transaction_success.*
import java.text.DecimalFormat
import java.util.*

class FillCardFromMyCardsFragment : BaseFragment(R.layout.fragment_fill_from_my_cards) {


    private val viewModel: FillCardViewModel by viewModels()

    val navArgs: FillCardFromMyCardsFragmentArgs by navArgs()

    var cardFromIndex = 0
    var cardToIndex = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val vHeight =
            SizeUtils.getScreenHeight(requireActivity()) - SizeUtils.getActionBarHeight(
                requireActivity()
            )

        setupViews()
        attachListeners()
        subscribeObservers()
        clParent.layoutParams = clParent.layoutParams.apply { height = vHeight }
//        (((parentFragment as NavHostFragment).parentFragment as FillCardBSD).dialog!! as BottomSheetDialog).behavior.isHideable =
//            false
//        (((parentFragment as NavHostFragment).parentFragment as FillCardBSD).dialog!! as BottomSheetDialog).behavior.isDraggable =
//            false

    }

    private fun setupViews() {
//        setupCalculatorView()
        layoutCalculator.edtSum = edtSum

        val cardViews = navArgs.cards?.map {
            val cardView = layoutInflater.inflate(R.layout.item_card_small, null)
            cardView.cardName.text = it.card_title
            cardView.tvAmount.text =
                DecimalFormat("#,###").format(it.balance?.dropLast(2)?.toInt()) + " UZS"
            cardView.tvCardEndNum.text = "*" + it.panHidden!!.substring(it.panHidden!!.length - 4)
            cardsToPagerSmall.addView(cardView)
            cardView
        }

        cardsToPagerSmall.adapter = HomeFragment.WizardPagerAdapter(cardViews!!)
        cardsToPagerSmall.offscreenPageLimit = 10
        cardsToPagerSmall.clipToPadding = false
        cardsToPagerSmall.setPadding(
            SizeUtils.dpToPx(requireContext(), 26).toInt(),
            0,
            SizeUtils.dpToPx(requireContext(), 26).toInt(),
            0
        )
        cardsToPagerSmall.pageMargin = SizeUtils.dpToPx(requireContext(), 15).toInt()
        cardToIndex = navArgs.cards!!.indexOf(navArgs.card!!)
        cardsToPagerSmall.currentItem = cardToIndex

        cardsToPagerSmall.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                if (cardFromIndex == position) {
                    cardFromIndex = if (position + 1 == navArgs.cards!!.size) 0 else position + 1
                    cardsFromPagerSmall.currentItem = cardFromIndex
                }
                cardToIndex = position
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })

        val cardViews2 = navArgs.cards?.map {
            val cardView = layoutInflater.inflate(R.layout.item_card_small, null)
            cardView.cardName.text = it.card_title
            cardView.tvAmount.text =
                DecimalFormat("#,###").format(it.balance?.dropLast(2)?.toInt()) + " UZS"
            cardView.tvCardEndNum.text = "*" + it.panHidden!!.substring(it.panHidden!!.length - 4)
            cardsFromPagerSmall.addView(cardView)
            cardView
        }

        cardsFromPagerSmall.adapter = HomeFragment.WizardPagerAdapter(cardViews2!!)
        cardsFromPagerSmall.offscreenPageLimit = 10
        cardsFromPagerSmall.clipToPadding = false
        cardsFromPagerSmall.setPadding(
            SizeUtils.dpToPx(requireContext(), 26).toInt(),
            0,
            SizeUtils.dpToPx(requireContext(), 26).toInt(),
            0
        )
        cardsFromPagerSmall.pageMargin = SizeUtils.dpToPx(requireContext(), 15).toInt()


        for (i in 0 until navArgs.cards!!.size) {
            if (cardToIndex != i) {
                cardFromIndex = i
                break
            }
        }

        cardsFromPagerSmall.currentItem = cardFromIndex

        cardsFromPagerSmall.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                if (cardToIndex == position) {
                    cardToIndex = if (position + 1 == navArgs.cards!!.size) 0 else position + 1
                    cardsToPagerSmall.currentItem = cardToIndex
                }
                cardFromIndex = position
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })

    }
//
//    private fun setupCalculatorView() {
//        tvOne.setOnClickListener { edtSum.append("1") }
//        tvTwo.setOnClickListener { edtSum.append("2") }
//        tvThree.setOnClickListener { edtSum.append("3") }
//        tvFour.setOnClickListener { edtSum.append("4") }
//        tvFive.setOnClickListener { edtSum.append("5") }
//        tvSix.setOnClickListener { edtSum.append("6") }
//        tvSeven.setOnClickListener { edtSum.append("7") }
//        tvEight.setOnClickListener { edtSum.append("8") }
//        tvNine.setOnClickListener { edtSum.append("9") }
//        tvZero.setOnClickListener { edtSum.append("0") }
//        tvMinus.setOnClickListener { edtSum.append(" - ") }
//        tvPlus.setOnClickListener { edtSum.append(" + ") }
//        tvMultiply.setOnClickListener { edtSum.append(" * ") }
//        tvDivide.setOnClickListener { edtSum.append(" / ") }
//        tvEquals.setOnClickListener {
//            if (edtSum.text.isNotBlank()) {
//                try {
//                    edtSum.setText(Keval.eval(edtSum.text.toString()).toInt().toString())
//                } catch (e: Exception) {
//
//                }
//            }
//        }
//
//        backspace.setOnClickListener {
//            if (edtSum.text.isNotBlank()) {
//                edtSum.setText(edtSum.text.dropLast(1))
//            }
//        }
//    }

    private fun subscribeObservers() {

        viewModel.p2pId2IdLoading.observe(viewLifecycleOwner) {
            clTopUpLoading.isVisible = it
        }
        viewModel.p2pId2IdResp.observe(viewLifecycleOwner) {
            val resp = it ?: return@observe
            when (resp) {
                is ResultError -> {
                    Snackbar.make(clParent, resp.message ?: "ERROR", Snackbar.LENGTH_SHORT).show()
                }
                is ResultSuccess -> {
                    clTopUpSuccess.isVisible = true

                    tvTransferAmount.text =
                        getString(
                            R.string.transfer_amount,
                            edtSum.text.toString()
                        )
                    tvCommissions.text =
                        getString(
                            R.string.commissions_amount,
                            (resp.value.amountWithoutTiyin!! - edtSum.text.toString()
                                .toInt()).toString()
                        )
                }
            }
        }
    }

    private fun attachListeners() {
        tvFill.setOnClickListener {
            viewModel.p2pId2Id(
                edtSum.text.toString().toInt(),
                navArgs.cards!![cardFromIndex].own_id!!,
                navArgs.cards!![cardToIndex].own_id!!
            )
        }

//        RUSTAM
//        C3C65508B1B7A7E9E053D30811ACA238

//        ASKAR
//        C58FF006FB320BF3E053D30811ACD142

        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        btnClose.setOnClickListener {
            ((parentFragment as NavHostFragment).requireParentFragment() as FillCardBSD).dismiss()
        }

        edtSum.doOnTextChanged { text, start, before, count ->
            tvFill.isEnabled = text != null && text.isNotBlank() && !text.contains(" ")
            tvMinAmount.isVisible =
                if (text.isNullOrBlank()) false else text.toString().toInt() < 1000
        }
    }

}