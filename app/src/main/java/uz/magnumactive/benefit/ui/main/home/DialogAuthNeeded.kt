package uz.magnumactive.benefit.ui.main.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import uz.magnumactive.benefit.R
import kotlinx.android.synthetic.main.dialog_please_add_card.*


const val KEY_NEED_AUTHORIZATION = "NEED_AUTHORIZATION"

class DialogAuthNeeded : DialogFragment(R.layout.dialog_need_authorization) {

    override fun getTheme() = R.style.Theme_Dialog


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isCancelable = false
        btnOk.setOnClickListener {
            parentFragmentManager.setFragmentResult(KEY_NEED_AUTHORIZATION, Bundle())
            dismiss()
        }
    }


}
