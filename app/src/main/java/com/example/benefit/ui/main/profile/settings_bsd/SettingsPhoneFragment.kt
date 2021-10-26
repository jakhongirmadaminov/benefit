package com.example.benefit.ui.main.profile.settings_bsd

/**
 * Created by jahon on 03-Sep-20
 */
import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.benefit.R
import com.example.benefit.ui.base.BaseFragment
import com.example.benefit.ui.main.profile.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_settings_phone.*
import kotlinx.android.synthetic.main.layout_success.*

class SettingsPhoneFragment : BaseFragment(R.layout.fragment_settings_phone) {


    private val viewModel: ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        attachListeners()
        subscribeObservers()
    }

    private fun subscribeObservers() {


    }

    private fun attachListeners() {
        edtPhone.doOnTextChanged { text, start, before, count ->
            if (!text.isNullOrBlank() && text.length == 9) {
                btnGetCode.myEnabled(true)
//                lblYoullReceiveCode.visibility = View.VISIBLE
//                lblYoullReceiveCode.text =
//                    getString(R.string.you_will_receive_code, tvPhoneStart.text.toString() + text)
            } else {
                btnGetCode.myEnabled(false)
//                lblYoullReceiveCode.visibility = View.GONE
            }

        }

        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        btnGetCode.setOnClickListener {
        }

        btnClose.setOnClickListener {
            ((parentFragment as NavHostFragment).requireParentFragment() as SettingsBSD).dismiss()
        }

    }

}