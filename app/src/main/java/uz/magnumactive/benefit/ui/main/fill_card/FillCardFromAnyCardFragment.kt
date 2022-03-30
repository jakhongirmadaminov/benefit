package uz.magnumactive.benefit.ui.main.fill_card

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import cards.pay.paycardsrecognizer.sdk.Card
import cards.pay.paycardsrecognizer.sdk.ScanCardIntent
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.ui.base.BaseFragment
import uz.magnumactive.benefit.util.ResultError
import uz.magnumactive.benefit.util.ResultSuccess
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

    }

    private fun subscribeObservers() {

        viewModel.panInfoResp.observe(viewLifecycleOwner) {
            when (it) {
                is ResultError -> {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.card_not_found),
                        Toast.LENGTH_SHORT
                    ).show()
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

        viewModel.panInfoLoading.observe(viewLifecycleOwner) {
            if (it) tvNext.isEnabled = false
        }

    }

    private fun attachListeners() {

        edtCardNumber.doOnTextChanged { text, start, before, count ->
            if (text?.length == 19) viewModel.getCardP2PInfo(text.toString().replace(" ", ""))
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
                    (viewModel.panInfoResp.value as ResultSuccess).value
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
            when (resultCode) {
                Activity.RESULT_OK -> {
                    val card: Card = data!!.getParcelableExtra(ScanCardIntent.RESULT_PAYCARDS_CARD)!!
                    val cardData = """
                        Card number: ${card.cardNumberRedacted}
                        Card holder: ${card.cardHolderName.toString()}
                        Card expiration date: ${card.expirationDate}
                        """.trimIndent()

                    edtCardNumber.setText(card.cardNumber)
                    Log.i("TAAAAG", "Card info: $cardData")
                }
                Activity.RESULT_CANCELED -> {
                    Log.i("TAAAAG", "Scan canceled")
                }
                else -> {
                    Log.i("TAAAAG", "Scan failed")
                }
            }
        }
    }


}