package com.example.benefit.ui.payments

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.benefit.R
import com.example.benefit.remote.models.FieldType
import com.example.benefit.remote.models.PaynetService
import com.example.benefit.remote.models.ServiceFields
import com.example.benefit.ui.base.BaseFragment
import com.example.benefit.util.AppPrefs
import com.example.benefit.util.Constants.UZ
import com.example.benefit.util.RequestState
import com.example.benefit.util.SizeUtils
import com.example.benefit.util.loadImageUrl
import kotlinx.android.synthetic.main.fragment_fill_merchant_fields.*
import kotlinx.android.synthetic.main.item_paynet_service_field_phone.view.*
import kotlinx.android.synthetic.main.item_paynet_service_field_spinner.view.*
import kotlinx.android.synthetic.main.item_paynet_service_field_string.view.*
import javax.inject.Inject


/**
 * Created by jahon on 03-Sep-20
 */

const val SUMMA_SERVICE_FIELD = "summa"
const val AMOUNT_SERVICE_FIELD = "amount"

class FillMerchantsFieldsFragment @Inject constructor() :
        BaseFragment(R.layout.fragment_fill_merchant_fields) {

    val args by navArgs<FillMerchantsFieldsFragmentArgs>()
    private val viewModel: FillMerchantFieldsViewModel by viewModels()
    private val sharedViewModel: PaymentsViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        attachListeners()
        subscribeObservers()
        viewModel.getPaynetServicesForProviderId(args.paynetMerchant.own_id!!)
    }

    private fun attachListeners() {

        btnPay.setOnClickListener {
//            viewModel.payCheck(
//                args.paynetMerchant.category_id!!,
//                providerId = args.paynetMerchant.own_id!!
//            )
            findNavController().navigate(
                    FillMerchantsFieldsFragmentDirections.actionFillMerchantFieldsToPaynetTransaction(
                            args.paynetMerchant,
                            ServiceFields(viewModel.fields)
                    )
            )
        }

    }

    private fun setupViews() {
        args.paynetMerchant.image?.let { ivLogo.loadImageUrl(it) }
        title.text = args.paynetMerchant.titleShort
        clParent.layoutParams = clParent.layoutParams.apply {
            height = SizeUtils.getScreenHeight(requireActivity()) - SizeUtils.getActionBarHeight(
                    requireActivity()
            )
        }
    }


    private fun populateFields(service: PaynetService) {
        paymentServiceFields.removeAllViews()
        viewModel.fields.clear()

        service.service_fields?.forEachIndexed { index, serviceField ->
            if (serviceField.fieldType == FieldType.MONEY) {
                sharedViewModel.setTransferringAmount(serviceField.name!!)
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
                                                if (AppPrefs.language == UZ) serviceField.titleUz else serviceField.titleRu
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
                                                if (AppPrefs.language == UZ) serviceField.titleUz else serviceField.titleRu
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
                                                if (AppPrefs.language == UZ) serviceField.titleUz else serviceField.titleRu
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
                                            selections.map { if (AppPrefs.language == UZ) it["titleUz"] as String else it["titleRu"] as String }
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
                                            if (AppPrefs.language == UZ) serviceField.titleUz else serviceField.titleRu
                                }
                        paymentServiceFields.addView(view)
                    }
                }
                else -> {
                    val view =
                            layoutInflater.inflate(R.layout.item_paynet_service_field_string, null)
                                    .apply {
                                        lblTitle.text =
                                                if (AppPrefs.language == UZ) serviceField.titleUz else serviceField.titleRu
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
        btnPay.myEnabled(viewModel.areAllFieldsSelected(service))
    }

    private fun subscribeObservers() {
        viewModel.paynetServices.observe(viewLifecycleOwner) { response ->
            when (response) {
                is RequestState.Error -> {
                    progress.isVisible = false
                    Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()
                }
                RequestState.Loading -> progress.isVisible = true
                is RequestState.Success -> {
                    progress.isVisible = false
                    populateFields(response.value.filter { it.titleRu!!.lowercase().contains("оплата") }[0])

                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.fields.clear()
    }
}
