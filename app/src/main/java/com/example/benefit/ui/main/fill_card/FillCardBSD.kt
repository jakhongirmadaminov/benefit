package com.example.benefit.ui.main.fill_card

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.benefit.R
import com.example.benefit.util.MyBSDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.bsd_fill_card.*


@AndroidEntryPoint
class FillCardBSD : MyBSDialog() {

    private val viewModel: FillCardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bsd_fill_card, null)

        return view
    }


    fun navigateToCardTransfer() {
        nav_host_fragment.findNavController()
            .navigate(R.id.action_fillCardFragment_to_cardMakeDepositFromAnyCardFragment)
    }

}