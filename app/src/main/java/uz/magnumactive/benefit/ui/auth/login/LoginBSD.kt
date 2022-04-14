package uz.magnumactive.benefit.ui.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.ui.main.BenefitBSD
import androidx.fragment.app.viewModels
import uz.magnumactive.benefit.ui.main.BenefitFixedHeightBSD

class LoginBSD : BenefitFixedHeightBSD() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bsd_login, null)

        return view
    }


}