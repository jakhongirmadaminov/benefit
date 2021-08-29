package com.example.benefit.ui.main.transfer_to_card


/**
 * Created by jahon on 03-Sep-20
 */
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import cards.pay.paycardsrecognizer.sdk.ScanCardIntent
import com.example.benefit.R
import com.example.benefit.remote.models.CardP2PDTO
import com.example.benefit.ui.base.BaseFragment
import com.example.benefit.ui.main.fill_card.REQUEST_CODE_SCAN_CARD
import com.example.benefit.util.ResultError
import com.example.benefit.util.ResultSuccess
import com.redmadrobot.inputmask.MaskedTextChangedListener
import kotlinx.android.synthetic.main.fragment_transfer_to_card.*

class TransferToCardFragment : BaseFragment(R.layout.fragment_transfer_to_card) {

    private val viewModel: TransferToCardViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()

        attachListeners()
        subscribeObservers()
    }

    private fun setupViews() {

        MaskedTextChangedListener.installOn(edtCardNumber, "[0000] [0000] [0000] [0000]")
        val listener = MaskedTextChangedListener("[0000] [0000] [0000] [0000]",
            edtCardNumber,
            object : MaskedTextChangedListener.ValueListener {
                override fun onTextChanged(
                    maskFilled: Boolean,
                    extractedValue: String,
                    formattedValue: String
                ) {

                    ivClearCardNumber.visibility =
                        if (extractedValue.isNotBlank()) View.VISIBLE else View.GONE
                }

            }
        )
        edtCardNumber.hint = getString(R.string.card_number)
        edtCardNumber.onFocusChangeListener = listener
        edtCardNumber.addTextChangedListener(listener)

    }

    private fun subscribeObservers() {
        viewModel.isLoadingCards.observe(viewLifecycleOwner) {
            edtCardNumber.setCompoundDrawables(null, null, null, null)
            cardInfoProgress.isVisible = it
            lblEnterPan.isVisible = !it
        }

        viewModel.panResp.observe(viewLifecycleOwner) {
            when (it) {
                is ResultError -> {
                    edtCardNumber.setCompoundDrawables(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.ic_round_error_outline_24
                        ),
                        null,
                        null,
                        null
                    )

                }
                is ResultSuccess -> {
                    tvNext.isEnabled = true
                }
            }
        }
    }

    private fun attachListeners() {

        edtCardNumber.doOnTextChanged { text, start, before, count ->
            text?.let {
                tvNext.isEnabled = false
                val pan = it.replace("\\s".toRegex(), "")
                if (pan.length == 16) {
                    viewModel.getCardP2PInfo(pan)
                }
            }
        }

        ivBack.setOnClickListener {
            ((parentFragment as NavHostFragment).parentFragment as TransferToCardBSD).dismiss()
        }

        llTakeCardPhoto.setOnClickListener {
            val intent = ScanCardIntent.Builder(requireContext()).build()
            startActivityForResult(intent, REQUEST_CODE_SCAN_CARD)
        }

        tvNext.setOnClickListener {
            (viewModel.panResp.value as? ResultSuccess<CardP2PDTO>)?.let {
                val dir =
                    TransferToCardFragmentDirections.actionTransferToCardFragmentToTransferToCardTransactionFragment(
                        it.value
                    )
                findNavController().navigate(dir)
            }
        }


    }

}