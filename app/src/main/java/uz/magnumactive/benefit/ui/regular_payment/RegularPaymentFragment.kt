package uz.magnumactive.benefit.ui.regular_payment

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_regular_payment.*
import kotlinx.android.synthetic.main.item_card_small.view.*
import kotlinx.android.synthetic.main.transaction_loading.*
import kotlinx.android.synthetic.main.transaction_success.*
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.remote.models.AutoPaymentDTO
import uz.magnumactive.benefit.remote.models.BalanceInfo
import uz.magnumactive.benefit.remote.models.CardDTO
import uz.magnumactive.benefit.ui.base.BaseFragment
import uz.magnumactive.benefit.ui.main.home.HomeFragment
import uz.magnumactive.benefit.ui.payments.SUMMA_SERVICE_FIELD
import uz.magnumactive.benefit.util.RequestState
import uz.magnumactive.benefit.util.SizeUtils
import uz.magnumactive.benefit.util.loadImageUrl
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
        regularPaymentDTO =
            requireArguments().getParcelable(RegularPaymentBSD.ARG_REGULAR_PAYMENT_DTO)!!

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

        tvMakePayment.setOnClickListener {
            viewModel.bftAndMyCardsPair.value?.second?.getProperly()?.let {
                viewModel.makePayment(args.autoPaymentDTO, it[cardsPager.currentItem].id!!)
            }
        }
        btnClose.setOnClickListener {
            ((parentFragment as NavHostFragment).requireParentFragment() as RegularPaymentBSD).dismiss()
        }
    }

    private fun setupViews() {
        layoutCalculator.edtSum = edtSum
        tvServiceName.text = args.autoPaymentDTO.providerInfo!!.titleShort
        tvServiceFullName.text = args.autoPaymentDTO.providerInfo!!.title
        tvCategoryName.text = args.autoPaymentDTO.providerInfo!!.getLocalizedCatgName()
        ivTargetService.loadImageUrl(args.autoPaymentDTO.providerInfo!!.image!!)

        edtSum.setText(args.autoPaymentDTO.amount.toString())

    }

    private fun subscribeObservers() {

        viewModel.bftAndMyCardsPair.observe(viewLifecycleOwner) { requestState ->
            progressCards.isVisible = false
            setupCardsPager(requestState.first, requestState.second.getProperly())
        }

        viewModel.isGettingsCards.observe(viewLifecycleOwner) {
            progressCards.isVisible = it
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
                    lblTopUpSuccess.text = getString(R.string.payment_succeeded)
                    tvTransferAmount.text =
                        getString(
                            R.string.transfer_amount,
                            transactionState.value.response!!.filter { it.key == SUMMA_SERVICE_FIELD }[0].value
                        )
                    tvCommissions.text = getString(R.string.commissions_amount, "0")
                }
            }
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
