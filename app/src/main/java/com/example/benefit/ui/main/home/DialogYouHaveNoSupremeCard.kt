package com.example.benefit.ui.main.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.example.benefit.R
import kotlinx.android.synthetic.main.dialog_please_add_card.*


//const val KEY_ADD_CARD = "ADD_CARD"

class DialogYouHaveNoSupremeCard : DialogFragment(R.layout.dialog_you_have_no_supreme_card) {

    override fun getTheme() = R.style.Theme_Dialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnOk.setOnClickListener {
//            childFragmentManager.setFragmentResult(KEY_ADD_CARD, Bundle())
            dismiss()
        }

//        btnCancel.setOnClickListener {
//            dismiss()
//        }
    }


}
