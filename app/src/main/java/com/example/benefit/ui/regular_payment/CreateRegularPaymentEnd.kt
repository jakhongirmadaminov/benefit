package com.example.benefit.ui.regular_payment

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.benefit.R
import com.example.benefit.remote.models.BalanceInfo
import com.example.benefit.remote.models.CardDTO
import com.example.benefit.remote.models.FieldType
import com.example.benefit.remote.models.PaynetService
import com.example.benefit.ui.base.BaseFragment
import com.example.benefit.ui.main.home.HomeFragment
import com.example.benefit.util.*
import kotlinx.android.synthetic.main.fragment_create_regular_payment_end.*
import kotlinx.android.synthetic.main.item_card_small.view.*
import kotlinx.android.synthetic.main.item_paynet_service_field_phone.view.*
import kotlinx.android.synthetic.main.item_paynet_service_field_spinner.view.*
import kotlinx.android.synthetic.main.item_paynet_service_field_string.view.*
import kotlinx.android.synthetic.main.transaction_loading.*
import java.text.DecimalFormat
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

        viewModel.getPaynetServicesForProviderId(
            args.paymentDTO?.serviceInfo?.ownId ?: args.merchantDTO?.own_id!!
        )
    }


    private fun attachListeners() {

        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        switchSetup.setOnCheckedChangeListener { buttonView, isChecked ->
            clSetup.visibility = if (isChecked) View.VISIBLE else View.GONE
        }

        tvCreateRegPayment.setOnClickListener {

            val fields = StringBuilder()
            viewModel.fields.forEach {
                fields.append("\"${it.name}\":")
                fields.append("\"${it.userSelection}\",")
            }

            fields.append("\"${viewModel.transferAmountKey}\":")
            fields.append("\"${edtSum.text.toString().toInt()}\"")

            viewModel.saveAutoPayment(
                type = 2,
                near_date = 0L,
                title = edtDesignation.text.toString(),
                card_id = if (cardsPager.currentItem == 0) null else viewModel.bftAndMyCardsPair.value!!.second.getProperly()[cardsPager.currentItem - 1].id,
                provider_id = args.paymentDTO?.providerInfo?.own_id
                    ?: args.merchantDTO!!.category_id!!,
                service_id = args.paymentDTO?.serviceInfo?.ownId ?: args.merchantDTO!!.own_id!!,
                from_cashback = cardsPager.currentItem == 0,
                is_notify = switchNotification.isChecked,
                amount = edtSum.text.toString().toInt(),
                fields = fields.toString()
            )

        }

    }

    private fun setupViews() {

        args.paymentDTO?.let { autoPaymentDto ->
            autoPaymentDto.providerInfo?.image?.let { ivBrandLogo.loadImageUrl(it) }
            autoPaymentDto.providerInfo?.titleShort?.let { tvBrandName.text = it }
        }

        args.merchantDTO?.let { merchant ->
            merchant.image?.let { ivBrandLogo.loadImageUrl(it) }
            merchant.titleShort?.let { tvBrandName.text = it }
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


    private fun populateFields(service: PaynetService) {
        paymentServiceFields.removeAllViews()
        viewModel.fields.clear()

        service.service_fields?.forEachIndexed { index, serviceField ->
            if (serviceField.fieldType == FieldType.MONEY) {
                viewModel.setTransferringAmount(serviceField.name!!)
                return
            }
            viewModel.fields.add(serviceField)
            serviceField.fieldValues is List<*>
            when (serviceField.fieldType) {
//                FieldType.STRING -> {
//                    val view =
//                            layoutInflater.inflate(R.layout.item_paynet_service_field_string, null)
//                                    .apply {
//                                        lblTitle.text =
//                                                if (AppPrefs.language == UZ) serviceField.titleUz else serviceField.titleRu
//                                        edtInput.doOnTextChanged { text, start, before, count ->
//                                            text?.let {
//                                                setFieldValue(service, index, it.toString())
//                                            }
//                                        }
//                                    }
//                    paymentServiceFields.addView(view)
//                }
//                FieldType.REGEXBOX -> {
//                    val view =
//                            layoutInflater.inflate(R.layout.item_paynet_service_field_string, null)
//                                    .apply {
//                                        lblTitle.text =
//                                                if (AppPrefs.language == UZ) serviceField.titleUz else serviceField.titleRu
//                                        edtInput.doOnTextChanged { text, start, before, count ->
//                                            text?.let {
//                                                setFieldValue(service, index, it.toString())
//                                            }
//                                        }
//                                    }
//                    paymentServiceFields.addView(view)
//                }
                FieldType.NUMBER -> {
                    val view =
                        layoutInflater.inflate(R.layout.item_paynet_service_field_number, null)
                            .apply {
                                lblTitle.text =
                                    if (AppPrefs.language == Constants.UZ) serviceField.titleUz else serviceField.titleRu
                                edtInput.doOnTextChanged { text, start, before, count ->
                                    text?.let {
                                        setFieldValue(service, index, it.toString())
                                    }
                                }
                            }
                    paymentServiceFields.addView(view)
                }
                FieldType.MONEY -> {
                    val view =
                        layoutInflater.inflate(R.layout.item_paynet_service_field_money, null)
                            .apply {
                                lblTitle.text =
                                    if (AppPrefs.language == Constants.UZ) serviceField.titleUz else serviceField.titleRu
//                                viewModel.fields.add(edtInput)
                                edtInput.doOnTextChanged { text, start, before, count ->
                                    text?.let {
                                        setFieldValue(service, index, it.toString())
                                    }
                                }
                            }
                    paymentServiceFields.addView(view)
                }
                FieldType.PHONE -> {
                    val view =
                        layoutInflater.inflate(R.layout.item_paynet_service_field_phone, null)
                            .apply {
                                lblEnterPhone.text =
                                    if (AppPrefs.language == Constants.UZ) serviceField.titleUz else serviceField.titleRu
//                                viewModel.fields.add(edtInput)
                                edtPhone.doOnTextChanged { text, start, before, count ->
                                    text?.let {
                                        setFieldValue(service, index, it.toString())
                                    }
                                }
                            }
                    paymentServiceFields.addView(view)
                }
                null -> {
                    if (serviceField.fieldValues is List<*>) {
                        val view =
                            layoutInflater.inflate(
                                R.layout.item_paynet_service_field_spinner,
                                paymentServiceFields,
                                false
                            ).apply {
                                val selections: List<Map<String, Any>> =
                                    serviceField.fieldValues.filterIsInstance<Map<String, Any>>()
                                selectionsSpinner.adapter = ArrayAdapter(
                                    requireContext(),
                                    android.R.layout.simple_spinner_item,
                                    selections.map { if (AppPrefs.language == Constants.UZ) it["titleUz"] as String else it["titleRu"] as String }
                                )
                                selectionsSpinner.onItemSelectedListener = object :
                                    AdapterView.OnItemSelectedListener {
                                    override fun onItemSelected(
                                        p0: AdapterView<*>?,
                                        p1: View?,
                                        p2: Int,
                                        p3: Long
                                    ) {
                                        setFieldValue(
                                            service,
                                            index,
                                            selections[p2]["field_id"]!!.toString()
                                        )

                                    }

                                    override fun onNothingSelected(p0: AdapterView<*>?) {
                                    }

                                }
                                tvTitle.text =
                                    if (AppPrefs.language == Constants.UZ) serviceField.titleUz else serviceField.titleRu
                            }
                        paymentServiceFields.addView(view)
                    }
                }
                else -> {
                    val view =
                        layoutInflater.inflate(R.layout.item_paynet_service_field_string, null)
                            .apply {
                                lblTitle.text =
                                    if (AppPrefs.language == Constants.UZ) serviceField.titleUz else serviceField.titleRu
                                edtInput.doOnTextChanged { text, start, before, count ->
                                    text?.let {
                                        setFieldValue(service, index, it.toString())
                                    }
                                }
                            }
                    paymentServiceFields.addView(view)
                }

            }


        }

//        putFieldCheckCallbacks(service)

    }

    private fun setFieldValue(service: PaynetService, index: Int, text: String) {
        viewModel.fields[index].userSelection = text
        checkFields(service)

    }

    private fun checkFields(service: PaynetService) {
        tvCreateRegPayment.isEnabled = viewModel.areAllFieldsSelected(service)
    }

    private fun subscribeObservers() {


        viewModel.bftAndMyCardsPair.observe(viewLifecycleOwner) { requestState ->
            progressCards.isVisible = false
            setupCardsPager(requestState.first, requestState.second.getProperly())
        }



        viewModel.paynetServices.observe(viewLifecycleOwner) { response ->
            when (response) {
                is RequestState.Error -> {
                    clTopUpLoading.isVisible = false
                    Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()
                }
                RequestState.Loading -> {
                    clTopUpLoading.isVisible = true
                }
                is RequestState.Success -> {
                    clTopUpLoading.isVisible = false
                    populateFields(response.value.filter {
                        it.titleRu!!.lowercase().contains("оплата")
                    }[0])

                }
            }
        }
    }


}
