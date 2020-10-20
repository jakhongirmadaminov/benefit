package com.example.benefit.ui.main.payments

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.benefit.R
import com.example.benefit.remote.models.RegularPaymentDTO
import com.example.benefit.ui.gap.GapActivity
import com.example.benefit.ui.regular_payment.CreateRegularPaymentBSD
import com.example.benefit.ui.main.fill_card.FillCardBSD
import com.example.benefit.ui.main.transfer_to_card.TransferToCardBSD
import com.example.benefit.ui.regular_payment.RegularPaymentBSD
import com.example.benefit.ui.viewgroups.ItemRegularPayment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_payments.*
import splitties.fragments.start

@AndroidEntryPoint
class PaymentsFragment : Fragment(R.layout.fragment_payments) {

    private val adapter = GroupAdapter<GroupieViewHolder>()
    private lateinit var paymentsViewModel: PaymentsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        attachListeners()
    }


    private fun setupViews() {
        rvRegularPayments.adapter = adapter
        adapter.clear()

        val data = listOf(
            RegularPaymentDTO("Телефон"),
            RegularPaymentDTO("Интернет"),
            RegularPaymentDTO("Электр.."),
            RegularPaymentDTO("Электр..")
        )
        loadData(data)


    }

    private fun loadData(data: List<RegularPaymentDTO>) {

        data.forEach {
            adapter.add(ItemRegularPayment(it, {
                val dialog = RegularPaymentBSD()
                dialog.arguments = Bundle().apply {
                    putParcelable(RegularPaymentBSD.ARG_REGULAR_PAYMENT_DTO, it)
                }
                dialog.show(childFragmentManager, "")
            }) { })
        }
        adapter.add(ItemRegularPayment(null, {}) {
            CreateRegularPaymentBSD().show(childFragmentManager, "")
        })

    }

    private fun attachListeners() {

        clMakeDepo.setOnClickListener {
         FillCardBSD() .show(childFragmentManager, "")
        }

        clTransferToCard.setOnClickListener {
            TransferToCardBSD().show(childFragmentManager, "")
        }
        clGap.setOnClickListener {
            start<GapActivity> {}
        }
    }
}