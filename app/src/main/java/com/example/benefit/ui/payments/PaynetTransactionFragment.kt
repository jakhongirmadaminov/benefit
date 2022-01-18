package com.example.benefit.ui.payments


/**
 * Created by jahon on 03-Sep-20
 */
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.benefit.R
import com.example.benefit.remote.models.CardDTO
import com.example.benefit.ui.base.BaseFragment
import com.example.benefit.ui.main.home.HomeFragment
import com.example.benefit.util.RequestState
import com.example.benefit.util.SizeUtils
import com.example.benefit.util.loadImageUrl
import kotlinx.android.synthetic.main.fragment_paynet_transfer.*
import kotlinx.android.synthetic.main.item_card_small.view.*
import kotlinx.android.synthetic.main.transaction_loading.*
import kotlinx.android.synthetic.main.transaction_success.*
import java.text.DecimalFormat

class PaynetTransactionFragment : BaseFragment(R.layout.fragment_paynet_transfer) {

    val args: PaynetTransactionFragmentArgs by navArgs()
    private val viewModel: PaynetTransactionViewModel by viewModels()
    private val sharedViewModel: PaymentsViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getMyCards()
        setupViews()

        attachListeners()
        subscribeObservers()
    }

    private fun setupViews() {
        layoutCalculator.edtSum = edtSum
        tvServiceName.text = args.paynetMerchant.titleShort
        tvServiceFullName.text = args.paynetMerchant.title
        tvCategoryName.text = args.paynetMerchant.getLocalizedCatgName()
        ivTargetService.loadImageUrl(args.paynetMerchant.image!!)
    }


    private fun subscribeObservers() {

        viewModel.myCards.observe(viewLifecycleOwner) { requestState ->

            when (requestState) {
                is RequestState.Error -> {
                    progressCards.isVisible = false
                    Toast.makeText(context, requestState.message, Toast.LENGTH_SHORT).show()
                }
                RequestState.Loading -> {
                    progressCards.isVisible = true
                }
                is RequestState.Success -> {
                    progressCards.isVisible = false
                    setupCardsPager(requestState.value.getProperly())

                }
            }

        }


        viewModel.transactionState.observe(viewLifecycleOwner) { transactionState ->
            when (transactionState) {
                is RequestState.Error -> {
                    clTopUpLoading.isVisible = false
                    Toast.makeText(context, transactionState.message, Toast.LENGTH_SHORT).show()
                }
                RequestState.Loading -> {
                    clTopUpLoading.isVisible = true
                }
                is RequestState.Success -> {
                    clTopUpLoading.isVisible = false
                    clTopUpSuccess.isVisible = true

                    tvTransferAmount.text =
                        getString(
                            R.string.transfer_amount,
                            edtSum.text.toString()
                        )
                    tvCommissions.text =
                        getString(
                            R.string.commissions_amount,
                            transactionState.value.toString()
                        )
                }
            }
        }

    }


    private fun setupCardsPager(cardsDTO: List<CardDTO>) {

        val cards = cardsDTO.map {
            val cardView = layoutInflater.inflate(R.layout.item_card_small, null)
            cardView.cardName.text = it.card_title
            cardView.tvAmount.text =
                DecimalFormat("#,###").format(it.balance?.dropLast(2)?.toInt()) + " UZS"
            cardView.tvCardEndNum.text = "*" + it.panHidden!!.substring(it.panHidden!!.length - 4)
            it.setMiniBackgroundInto(cardView.ivCardBg)
            cardsPagerSmall.addView(cardView)
            cardView
        }

        cardsPagerSmall.adapter = HomeFragment.WizardPagerAdapter(cards)
        cardsPagerSmall.offscreenPageLimit = 10
        cardsPagerSmall.clipToPadding = false
        cardsPagerSmall.setPadding(
            SizeUtils.dpToPx(requireContext(), 26).toInt(),
            0,
            SizeUtils.dpToPx(requireContext(), 26).toInt(),
            0
        )
        cardsPagerSmall.pageMargin = SizeUtils.dpToPx(requireContext(), 15).toInt()

    }


    private fun attachListeners() {

        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        tvFill.setOnClickListener {
            val fields = StringBuilder()
            args.serviceFields.forEach {
                fields.append("\"${it.name}\":")
                fields.append("\"${it.userSelection}\",")
            }

            viewModel.pay(
                serviceId = args.paynetMerchant.category_id!!,
                providerId = args.paynetMerchant.own_id!!,
                fields = fields.removeSuffix(",").toString(),
                edtSum.text.toString().toInt(),
                (viewModel.myCards.value as RequestState.Success).value.getProperly()[cardsPagerSmall.currentItem].id!!,
                "token"
            )
        }

        edtSum.doOnTextChanged { text, start, before, count ->
            if (text.isNullOrBlank()) {
                tvFill.isEnabled = false
                return@doOnTextChanged
            }
            tvFill.isEnabled =
                (viewModel.myCards.value as RequestState.Success).value.getProperly()[cardsPagerSmall.currentItem].balance?.dropLast(
                    2
                )!!.toInt() > text.toString()
                    .toInt()
        }

    }

}