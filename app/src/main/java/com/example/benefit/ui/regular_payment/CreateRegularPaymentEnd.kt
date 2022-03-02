package com.example.benefit.ui.regular_payment

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.benefit.R
import com.example.benefit.remote.models.*
import com.example.benefit.ui.base.BaseFragment
import com.example.benefit.ui.main.home.HomeFragment
import com.example.benefit.util.*
import com.example.benefit.util.Constants.MONTHS
import kotlinx.android.synthetic.main.fragment_create_regular_payment_end.*
import kotlinx.android.synthetic.main.item_card_small.view.*
import kotlinx.android.synthetic.main.item_paynet_service_field_phone.view.*
import kotlinx.android.synthetic.main.item_paynet_service_field_spinner.view.*
import kotlinx.android.synthetic.main.item_paynet_service_field_string.view.*
import kotlinx.android.synthetic.main.transaction_loading.*
import kotlinx.android.synthetic.main.transaction_success.*
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import java.text.DecimalFormat
import javax.inject.Inject


/**
 * Created by jahon on 03-Sep-20
 */

class CreateRegularPaymentEnd @Inject constructor() :
    BaseFragment(R.layout.fragment_create_regular_payment_end) {

    val args by navArgs<CreateRegularPaymentEndArgs>()
    private val viewModel: CreateRegularPaymentViewModel by viewModels()

    var startDate: Long? = null
    var startTime: Long? = null

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

        edtDesignation.doOnTextChanged { text, start, before, count ->
            checkFields()
        }

        tvDate.setOnClickListener {
            val dialog = DatePickerFragment()
            childFragmentManager.setFragmentResultListener(
                KEY_DATE_SELECTED,
                viewLifecycleOwner
            ) { requestKey, result ->
                startDate = result.getLong(ARG_DATE)
                val date = DateTime(startDate)
                tvDate.text = "${date.dayOfMonth} ${MONTHS[AppPrefs.language]!![date.monthOfYear]}"
                checkFields()
            }
            dialog.show(childFragmentManager, "datePicker")
        }

        tvTime.setOnClickListener {
            val dialog = TimePickerFragment()
            childFragmentManager.setFragmentResultListener(
                KEY_TIME_SELECTED,
                viewLifecycleOwner
            ) { requestKey, result ->
                startTime = result.getLong(ARG_TIME)
                tvTime.text = DateTimeFormat.forPattern("HH:mm")
                    .print(DateTime().withTimeAtStartOfDay().plus(startTime!!))
                checkFields()
            }
            dialog.show(childFragmentManager, "timePicker")
        }

        switchSetup.setOnCheckedChangeListener { buttonView, isChecked ->
            checkFields()
            clSetup.visibility = if (isChecked) View.VISIBLE else View.GONE
        }

        tvCreateRegPayment.setOnClickListener {

            val fieldsBuilder = StringBuilder()
            for (field in viewModel.fields) {
                fieldsBuilder.append("\"${field.name}\":")
                fieldsBuilder.append("\"${field.userSelection}\",")
            }

            fieldsBuilder.append("\"${viewModel.transferAmountKey}\":")
            fieldsBuilder.append("\"${edtSum.text.toString().toInt()}\"")

            viewModel.saveAutoPayment(
                type = if (checkOnce.isChecked) 1 else if (checkOnceAWeek.isChecked) 2 else if (checkOnceAMonth.isChecked) 3 else 4,
                near_date = (startDate!! + startTime!!) / 1000,
                title = edtDesignation.text.toString(),
                card_id = if (cardsPager.currentItem == 0) 0 else viewModel.bftAndMyCardsPair.value!!.second.getProperly()[cardsPager.currentItem - 1].id,
                provider_id = args.paymentDTO?.providerInfo?.own_id ?: args.merchantDTO!!.own_id!!,
                service_id = args.paymentDTO?.serviceInfo?.ownId
                    ?: (viewModel.paynetServices.value as RequestState.Success).value.getPaymentService()!!.own_id!!,
                from_cashback = cardsPager.currentItem == 0,
                is_notify = switchNotification.isChecked,
                amount = edtSum.text.toString().toInt(),
                fields = fieldsBuilder.toString()
            )

        }

    }

    private fun setupViews() {

        args.paymentDTO?.let { autoPaymentDto ->
            autoPaymentDto.providerInfo?.image?.let { ivBrandLogo.loadImageUrl(it) }
            autoPaymentDto.providerInfo?.titleShort?.let { tvBrandName.text = it }
            edtDesignation.setText(autoPaymentDto.title)

            tvDate.text = autoPaymentDto.nearDate
            startDate = DateTime.parse(
                autoPaymentDto.nearDate,
                DateTimeFormat.forPattern("dd.MM.yyyy")
            ).millis
            edtSum.setText(autoPaymentDto.amount.toString())
        }

        args.merchantDTO?.let { merchant ->
            merchant.image?.let { ivBrandLogo.loadImageUrl(it) }
            merchant.titleShort?.let { tvBrandName.text = it }
        }

        tvCreateRegPayment.myEnabled(false)
    }

    private fun checkFields() {
        tvCreateRegPayment.myEnabled(
            startDate != null &&
                    startTime != null &&
                    viewModel.fields.allFilled() &&
                    !edtDesignation.text.isNullOrBlank() &&
                    switchSetup.isChecked
        )
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
                                findAndFillWithRegex(edtInput, serviceField)
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
                                findAndFillWithRegex(edtInput, serviceField)
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
                                findAndFillWithRegex(edtPhone, serviceField)
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

                                findAndFillWithRegex(edtInput, serviceField)
                            }
                    paymentServiceFields.addView(view)
                }

            }


        }

    }

    private fun findAndFillWithRegex(edtInput: EditText, serviceField: ServiceField) {
        args.paymentDTO?.let { autoPayment ->
            Regex("(?<=\"${serviceField.name}\":\").+(?=\",)").find(
                autoPayment.fields!!
            )?.value?.let {
                edtInput.setText(it)
            }
        }
    }

    private fun setFieldValue(service: PaynetService, index: Int, text: String) {
        viewModel.fields[index].userSelection = text
        checkFields(service)

    }

    private fun checkFields(service: PaynetService) {
        tvCreateRegPayment.isEnabled = viewModel.areAllFieldsSelected(service)
    }

    private fun subscribeObservers() {
        viewModel.savePaymentResp.observe(viewLifecycleOwner) {
            when (it) {
                is RequestState.Error -> {
                    clTopUpLoading.isVisible = false
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                RequestState.Loading -> {
                    lblSearching.text = getString(R.string.payment_creating)
                    clTopUpLoading.isVisible = true
                }
                is RequestState.Success -> {
                    lblTopUpSuccess.text = getString(R.string.payment_created)
                    clTopUpLoading.isVisible = false
                    clTopUpSuccess.isVisible = true
                }
            }
        }

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
                    populateFields(response.value.getPaymentService()!!)
                }
            }
        }
    }


}
