package com.example.benefit.ui.main.fill_card

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import cards.pay.paycardsrecognizer.sdk.Card
import cards.pay.paycardsrecognizer.sdk.ScanCardIntent
import com.example.benefit.R
import com.example.benefit.ui.base.BaseFragment
import com.redmadrobot.inputmask.MaskedTextChangedListener
import kotlinx.android.synthetic.main.fragment_fill_from_any_card.*
import javax.inject.Inject


/**
 * Created by jahon on 03-Sep-20
 */

const val REQUEST_CODE_SCAN_CARD = 13

class FillCardFromAnyCardFragment @Inject constructor() :
    BaseFragment(R.layout.fragment_fill_from_any_card) {

    private val viewModel: FillCardViewModel by viewModels()

    val navArgs: FillCardFromAnyCardFragmentArgs by navArgs()


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

        val listener2 = MaskedTextChangedListener("[00]/[00]", edtCardExpiry)
        edtCardExpiry.hint = getString(R.string.mm_slash_yy)
        edtCardExpiry.onFocusChangeListener = listener2
        edtCardExpiry.addTextChangedListener(listener2)
    }

    private fun subscribeObservers() {


    }

    private fun attachListeners() {

        edtCardNumber.doOnTextChanged { text, start, before, count ->
            tvNext.isEnabled =
                !text.isNullOrBlank() && text.length == 19 && !edtCardExpiry.text.isNullOrBlank() && edtCardExpiry.text.length == 5
        }
        edtCardExpiry.doOnTextChanged { text, start, before, count ->
            tvNext.isEnabled =
                !edtCardNumber.text.isNullOrBlank() && edtCardNumber.text.length == 19 && !text.isNullOrBlank() && text.length == 5
        }

        ivClearCardNumber.setOnClickListener {
            edtCardNumber.setText("")
        }

        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
        tvNext.setOnClickListener {
            val dir =
                FillCardFromAnyCardFragmentDirections.actionCardMakeDepositFromAnyCardFragmentToCardDepositAnyCardTransferFragment(
                    navArgs.cards,
                    navArgs.card,
                    edtCardNumber.text.toString().replace(" ", ""),
                    edtCardExpiry.text.toString()
                )
            findNavController().navigate(dir)
        }
        llTakeCardPhoto.setOnClickListener {
            val intent = ScanCardIntent.Builder(requireContext()).build()
            startActivityForResult(intent, REQUEST_CODE_SCAN_CARD)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SCAN_CARD) {
            if (resultCode == Activity.RESULT_OK) {
                val card: Card = data!!.getParcelableExtra(ScanCardIntent.RESULT_PAYCARDS_CARD)!!
                val cardData = """
                Card number: ${card.cardNumberRedacted}
                Card holder: ${card.cardHolderName.toString()}
                Card expiration date: ${card.expirationDate}
                """.trimIndent()

                edtCardNumber.setText(card.cardNumber)
                edtCardExpiry.setText(card.expirationDate)

                Log.i("TAAAAG", "Card info: $cardData")


            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("TAAAAG", "Scan canceled")
            } else {
                Log.i("TAAAAG", "Scan failed")
            }
        }
    }


}