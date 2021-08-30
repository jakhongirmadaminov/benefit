package com.example.benefit.ui.main.fill_card

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
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
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_fill_from_any_card_transfer.*
import kotlinx.android.synthetic.main.item_card_small.view.*
import kotlinx.android.synthetic.main.transaction_loading.*
import kotlinx.android.synthetic.main.transaction_success.*
import java.text.DecimalFormat
import javax.inject.Inject


/**
 * Created by jahon on 03-Sep-20
 */

class FillCardAnyCardTransferFragment @Inject constructor() :
    BaseFragment(R.layout.fragment_fill_from_any_card_transfer) {

    private var cardToIndex: Int = 0
    private val viewModel: FillCardViewModel by viewModels()
    val navArgs: FillCardAnyCardTransferFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        attachListeners()
        subscribeObservers()

//        viewModel.getCardP2PInfo(navArgs.targetPan)
    }

    private fun subscribeObservers() {
        viewModel.p2pPan2IdLoading.observe(viewLifecycleOwner) {
            clTopUpLoading.isVisible = it
        }
        viewModel.p2pPan2IdResp.observe(viewLifecycleOwner) {
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ((parentFragment as NavHostFragment).parentFragment as FillCardBSD).dialog!!.window!!.setSoftInputMode(
            WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        )
        requireActivity().window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun attachListeners() {
        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
        edtSum.setOnFocusChangeListener { v, hasFocus ->

        }

        tvFill.setOnClickListener {
            viewModel.p2pPan2Id(
                edtSum.text.toString().toInt(),
                navArgs.cards!![cardToIndex].id!!,
                navArgs.targetCard!!.pan!!,
                navArgs.targetCard!!.expiry!!.substring(2)
            )
        }

        btnClose.setOnClickListener {
            ((parentFragment as NavHostFragment).requireParentFragment() as FillCardBSD).dismiss()
        }

        edtSum.doOnTextChanged { text, start, before, count ->
            tvFill.isEnabled = text != null && text.isNotBlank() && !text.contains(" ")
        }

    }


    private fun setupViews() {
        tvCardEndNum.text =
            "*" + navArgs.targetCard!!.pan!!.substring(navArgs.targetCard!!.pan!!.length - 4)
        tvBankName.text = navArgs.targetCard!!.fullName

        layoutCalculator.edtSum = edtSum
        val cardViews = navArgs.cards?.map {
            val cardView = layoutInflater.inflate(R.layout.item_card_small, null)
            cardView.cardName.text = it.card_title
            cardView.tvAmount.text =
                DecimalFormat("#,###").format(it.balance?.dropLast(2)?.toInt()) + " UZS"
            cardView.tvCardEndNum.text = "*" + it.pan!!.substring(it.pan!!.length - 4)
            it.setMiniBackgroundInto(cardView.ivCardBg)
            cardsPagerSmall.addView(cardView)
            cardView
        }

        cardsPagerSmall.adapter = HomeFragment.WizardPagerAdapter(cardViews!!)
        cardsPagerSmall.offscreenPageLimit = 10
        cardsPagerSmall.clipToPadding = false
        cardsPagerSmall.setPadding(
            SizeUtils.dpToPx(requireContext(), 26).toInt(),
            0,
            SizeUtils.dpToPx(requireContext(), 26).toInt(),
            0
        )
        cardsPagerSmall.pageMargin = SizeUtils.dpToPx(requireContext(), 15).toInt()
        cardToIndex = navArgs.cards!!.indexOf(navArgs.card!!)
        cardsPagerSmall.currentItem = cardToIndex

        cardsPagerSmall.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                cardToIndex = position
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })

    }

}