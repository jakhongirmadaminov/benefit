package com.example.benefit.ui.payments

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.benefit.R
import com.example.benefit.remote.models.FieldType
import com.example.benefit.remote.models.PaynetService
import com.example.benefit.ui.base.BaseFragment
import com.example.benefit.util.*
import kotlinx.android.synthetic.main.fragment_fill_merchant_fields.*
import kotlinx.android.synthetic.main.fragment_payments.clParent
import kotlinx.android.synthetic.main.fragment_payments.title
import kotlinx.android.synthetic.main.item_paynet_service_field_string.view.*
import javax.inject.Inject


/**
 * Created by jahon on 03-Sep-20
 */

class FillMerchantsFieldsFragment @Inject constructor() :
    BaseFragment(R.layout.fragment_fill_merchant_fields) {

    val args by navArgs<FillMerchantsFieldsFragmentArgs>()
    private val viewModel: FillMerchantFieldsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        attachListeners()
        subscribeObservers()
        viewModel.getPaynetServicesForProviderId(args.paynetMerchant.own_id!!)
    }

    private fun attachListeners() {


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

    val fields = arrayListOf<EditText>()

    private fun populateFields(service: PaynetService) {
        paymentServiceFields.removeAllViews()

        service.service_fields?.forEach { serviceField ->

            when (serviceField.fieldType) {
                FieldType.STRING -> {
                    val view =
                        layoutInflater.inflate(R.layout.item_paynet_service_field_string, null)
                            .apply {
                                lblTitle.text =
                                    if (AppPrefs.language == Constants.UZ) serviceField.titleUz else serviceField.titleRu
                                fields.add(edtInput)
                            }
                    paymentServiceFields.addView(view)
                }
                FieldType.MONEY -> {
                    val view =
                        layoutInflater.inflate(R.layout.item_paynet_service_field_money, null)
                            .apply {
                                lblTitle.text =
                                    if (AppPrefs.language == Constants.UZ) serviceField.titleUz else serviceField.titleRu
                                fields.add(edtInput)
                            }
                    paymentServiceFields.addView(view)
                }
                null -> {

                }
            }


        }

        putFieldCheckCallbacks(service)

    }

    private fun putFieldCheckCallbacks(service: PaynetService) {
        fields.forEach {
            it.doOnTextChanged { text, start, before, count ->
                checkFields(service)
            }
        }
    }

    private fun checkFields(service: PaynetService) {
        fields.forEachIndexed { index, editText ->
            if (service.service_fields!![index].required == 1 && editText.text?.isBlank() == true) {
                btnPay.myEnabled(false)
            }
        }
        btnPay.myEnabled(true)
    }

    private fun subscribeObservers() {

        viewModel.paynetServices.observe(viewLifecycleOwner) {
            when (it) {
                is RequestState.Error -> {
                    progress.isVisible = false
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
                RequestState.Loading -> progress.isVisible = true
                is RequestState.Success -> {
                    progress.isVisible = false
                    populateFields(it.value[0])
                }
            }
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        fields.clear()
    }
}
