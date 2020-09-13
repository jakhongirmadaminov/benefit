package com.example.benefit.ui.main.home.card_options

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.benefit.R
import kotlinx.android.synthetic.main.dialog_block_card.*

/**
 * Created by jahon on 14-Sep-20
 */
class DialogBlockCard : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        setStyle(STYLE_NO_TITLE, android.R.style.Theme)
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_block_card, container)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnCancel.setOnClickListener {
            dismiss()
        }
        btnBlock.setOnClickListener {

        }
    }

}