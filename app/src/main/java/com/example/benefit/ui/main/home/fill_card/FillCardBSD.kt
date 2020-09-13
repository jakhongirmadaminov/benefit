package com.example.benefit.ui.main.home.fill_card

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.benefit.R
import com.example.benefit.util.MyBSDialog
import dagger.hilt.android.AndroidEntryPoint


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


}