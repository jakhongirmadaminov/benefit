package com.example.benefit.ui.main.fill_card

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.benefit.R
import com.example.benefit.ui.main.home.card_options.CardOptionsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_fill_from_any_card.*
import javax.inject.Inject

/**
 * Created by jahon on 03-Sep-20
 */
@AndroidEntryPoint
class FillCardFromAnyCardFragment @Inject constructor() : Fragment(R.layout.fragment_fill_from_any_card) {


    private val viewModel: CardOptionsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        attachListeners()
        subscribeObservers()
    }

    private fun subscribeObservers() {


    }

    private fun attachListeners() {
        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
        tvNext.setOnClickListener{
            findNavController().navigate(R.id.action_cardMakeDepositFromAnyCardFragment_to_cardDepositAnyCardTransferFragment)
        }
    }

}