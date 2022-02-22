package com.example.benefit.ui.main.home

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.benefit.R
import kotlinx.android.synthetic.main.dialog_cashback.*

/**
 * Created by jahon on 06-Sep-20
 */

const val KEY_GO_TO_LIST = "GO_TO_LIST"

class DialogCashBack : DialogFragment() {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        setStyle(STYLE_NO_TITLE, R.style.AppTheme)
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_cashback, container)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ivClose.setOnClickListener {
            dismiss()
        }
        btnGoToList.setOnClickListener {
            childFragmentManager.setFragmentResult(KEY_GO_TO_LIST, Bundle())
            dismiss()
        }
    }
}