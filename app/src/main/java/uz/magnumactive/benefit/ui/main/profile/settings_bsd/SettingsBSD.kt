package uz.magnumactive.benefit.ui.main.profile.settings_bsd

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.ui.main.BenefitFixedHeightBSD


class SettingsBSD : BenefitFixedHeightBSD() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bsd_settings, container)

        return view
    }


}