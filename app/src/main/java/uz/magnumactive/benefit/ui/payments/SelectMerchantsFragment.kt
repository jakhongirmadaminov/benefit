package uz.magnumactive.benefit.ui.payments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_payment_merchants.*
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.remote.models.PaynetMerchant
import uz.magnumactive.benefit.ui.base.BaseFragment
import uz.magnumactive.benefit.ui.viewgroups.ItemPaynetMerchant
import uz.magnumactive.benefit.util.AppPrefs
import uz.magnumactive.benefit.util.Constants.UZ
import uz.magnumactive.benefit.util.RequestState
import uz.magnumactive.benefit.util.SizeUtils
import uz.magnumactive.benefit.util.setLoadingSpinner
import javax.inject.Inject


/**
 * Created by jahon on 03-Sep-20
 */

class SelectMerchantsFragment @Inject constructor() :
    BaseFragment(R.layout.fragment_payment_merchants) {

    val args by navArgs<SelectMerchantsFragmentArgs>()
    private val adapter = GroupAdapter<GroupieViewHolder>()
    private val viewModel: SelectMerchantsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        attachListeners()
        subscribeObservers()
        viewModel.getProvidersForPaynetCategoryId(args.paynetCategory.own_id!!)
    }

    private fun attachListeners() {
        edtSearch.doOnTextChanged { text, _, _, _ ->

            when (val result = viewModel.paynetMerchants.value) {
                is RequestState.Success -> {
                    result.value.let { paynetMerchants ->
                        if (text!!.isBlank()) {
                            adapter.clear()
                            loadData(paynetMerchants)
                        } else {
                            val filtered = paynetMerchants.filter {
                                it.titleShort?.lowercase()
                                    ?.contains(text.toString().lowercase()) == true
                                /* || it.titleShort?.contains(text) == true*/
                            }
                            if (filtered.isNotEmpty()) {
                                adapter.clear()
                                loadData(filtered)
                            }
                        }
                    }
                }
                else -> {

                }
            }
        }
    }

    private fun setupViews() {

        title.text =
            if (AppPrefs.language == UZ) args.paynetCategory.titleUz else args.paynetCategory.titleRu

        clParent.layoutParams = clParent.layoutParams.apply {
            height = SizeUtils.getScreenHeight(requireActivity()) - SizeUtils.getActionBarHeight(
                requireActivity()
            )
        }

        rvPayments.adapter = adapter
    }

    private fun loadData(data: List<PaynetMerchant>) {
        adapter.clear()
        data.forEach { paynetCategory ->
            adapter.add(ItemPaynetMerchant(paynetCategory) {
                findNavController().navigate(
                    SelectMerchantsFragmentDirections.actionSelectMerchantFragmentToFillMerchantFields(
                        it
                    )
                )
            })
        }
    }

    private fun subscribeObservers() {

        viewModel.paynetMerchants.observe(viewLifecycleOwner) {
            when (it) {
                is RequestState.Error -> {
                    adapter.clear()
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT)
                        .show()
                }
                RequestState.Loading -> adapter.setLoadingSpinner()
                is RequestState.Success -> loadData(it.value)
            }
        }

    }


}
