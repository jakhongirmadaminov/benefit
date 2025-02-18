package uz.magnumactive.benefit.ui.main.profile.settings_bsd

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.ui.base.BaseFragment
import uz.magnumactive.benefit.util.AppPrefs
import kotlinx.android.synthetic.main.fragment_settings_change_code.*
import javax.inject.Inject

/**
 * Created by jahon on 03-Sep-20
 */

class SettingsChangeCodeFragment @Inject constructor() :
    BaseFragment(R.layout.fragment_settings_change_code) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        attachListeners()
        subscribeObservers()
    }

    private fun setupViews() {

    }

    private fun subscribeObservers() {
    }

    private fun attachListeners() {
        edtCode.doOnTextChanged { text, start, before, count ->
            lblEnterCode.text = getString(R.string.enter_old_entrance_code)
            lblEnterCode.setTextColor(
                ContextCompat.getColor(requireContext(), R.color.black)
            )
            if (!text.isNullOrBlank() && text.length == 4) {
                btnConfirm.myEnabled(true)
            } else {
                btnConfirm.myEnabled(false)
            }

        }


        btnConfirm.setOnClickListener {

            if (edtCode.text.toString() == AppPrefs.pin) {
                findNavController().navigate(R.id.action_settingsChangeCodeFragment_to_settingsNewCodeFragment)
            } else {
                lblEnterCode.setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.error_red)
                )

                lblEnterCode.text = getString(R.string.wrong_code)
            }


        }

        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }


}