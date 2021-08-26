package com.example.benefit.ui.main.transfer_to_card


/**
 * Created by jahon on 03-Sep-20
 */
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.benefit.R
import com.example.benefit.remote.models.CardDTO
import com.example.benefit.ui.base.BaseFragment
import com.example.benefit.ui.main.home.HomeFragment
import com.example.benefit.util.SizeUtils
import kotlinx.android.synthetic.main.fragment_transfer_to_card_transaction.*
import kotlinx.android.synthetic.main.item_card_small.view.*

class TransferToCardTransactionFragment :
    BaseFragment(R.layout.fragment_transfer_to_card_transaction) {


    val args: TransferToCardTransactionFragmentArgs by navArgs()

    private val viewModel: TransferToCardViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.getMyCards()
        setupViews()

        attachListeners()
        subscribeObservers()
    }

    private fun setupViews() {

        tvCardOwner.text = args.cardP2pTarget.fullName
        tvCardNumber.text = args.cardP2pTarget.pan
    }


    private fun subscribeObservers() {

        viewModel.isLoadingCards.observe(viewLifecycleOwner) {
            progressCards.isVisible = it
        }

        viewModel.errorMessage.observe(viewLifecycleOwner, {
//            lblYoullReceiveCode.text = it ?: return@observe
//            lblYoullReceiveCode.setTextColor(Color.RED)
        })


        viewModel.cardsResp.observe(viewLifecycleOwner, {
            setupCardsPager(it)
        })


    }


    private fun setupCardsPager(cardsDTO: List<CardDTO>) {

        val cards = cardsDTO.map {
            val cardView = layoutInflater.inflate(R.layout.item_card_small, null)
            cardView.cardName.text = it.card_title
            cardView.tvAmount.text = it.balance
            cardView.tvCardEndNum.text = "*" + it.pan!!.substring(it.pan!!.length - 4)
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
            viewModel.transferToCard(
                viewModel.cardsResp.value!![cardsPagerSmall.currentItem].id!!,
                args.cardP2pTarget,
                edtSum.text.toString().toInt()
            )
        }

        edtSum.doOnTextChanged { text, start, before, count ->
            tvFill.isEnabled =
                viewModel.cardsResp.value!![cardsPagerSmall.currentItem].balance?.dropLast(2)!!.toInt() > text.toString()
                    .toInt()
        }

//        llAskFriends.setOnClickListener {
//            findNavController().navigate(R.id.action_fillCardFragment_to_cardDepositAskFriendsFragment)
//        }
//        llWithCash.setOnClickListener {
//            startActivity(Intent(requireActivity(), BranchesAtmsActivity::class.java))
//        }
//        llFromAnyCard.setOnClickListener {
//            findNavController().navigate(R.id.action_fillCardFragment_to_cardMakeDepositFromAnyCardFragment)
//        }
//        llFromOwnCards.setOnClickListener {
//            findNavController().navigate(R.id.action_fillCardFragment_to_cardMakeDepositFromMyCardFragment)
//
//        }

    }

}