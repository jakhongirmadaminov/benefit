package com.example.benefit.ui.main.home.bsd_add_card

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.benefit.R
import com.example.benefit.ui.main.BenefitFixedHeightBSD


class AddCardBSD : BenefitFixedHeightBSD() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bsd_add_card, container)
        return view
    }
}