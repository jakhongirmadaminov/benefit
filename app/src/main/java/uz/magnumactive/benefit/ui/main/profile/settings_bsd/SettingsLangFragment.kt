package uz.magnumactive.benefit.ui.main.profile.settings_bsd

/**
 * Created by jahon on 03-Sep-20
 */
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_settings_lang.*
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.ui.auth.login.LoginViewModel
import uz.magnumactive.benefit.ui.base.BaseFragment
import uz.magnumactive.benefit.ui.main.MainActivity
import uz.magnumactive.benefit.util.AppPrefs
import uz.magnumactive.benefit.util.Constants
import uz.magnumactive.benefit.util.ContextUtils.setLocale

class SettingsLangFragment : BaseFragment(R.layout.fragment_settings_lang) {


    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rbRu.isChecked = false
        rbUz.isChecked = false
        rbEn.isChecked = false

        if (AppPrefs.language == Constants.UZ) {
            rbUz.isChecked = true
        } else if (AppPrefs.language == Constants.EN) {
            rbEn.isChecked = true
        } else {
            rbRu.isChecked = true
        }

        attachListeners()
        subscribeObservers()
    }

    private fun subscribeObservers() {
    }

    private fun attachListeners() {
        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
        tvSelect.setOnClickListener {
            if (rbUz.isChecked) {
                setLocale(Constants.UZ, requireActivity())
            } else if (rbEn.isChecked) {
                setLocale(Constants.EN, requireActivity())
            } else {
                setLocale(Constants.RU, requireActivity())
            }
            findNavController().popBackStack()
            restart()
        }

        llRussian.setOnClickListener {
            rbRu.isChecked = true
            rbUz.isChecked = false
            rbEn.isChecked = false
        }
//        llUzbek.setOnClickListener {
//            rbUz.isChecked = true
//            rbRu.isChecked = false
//            rbEn.isChecked = false
//        }
//        llEnglish.setOnClickListener {
//            rbEn.isChecked = true
//            rbRu.isChecked = false
//            rbUz.isChecked = false
//        }

    }

    fun restart() {
        context?.startActivities(
            arrayOf(
                Intent(context, MainActivity::class.java).apply {
                    putExtra(MainActivity.IS_JUST_LOGGED_IN, true)
                },
            )
        )
        requireActivity().finish()
    }


}