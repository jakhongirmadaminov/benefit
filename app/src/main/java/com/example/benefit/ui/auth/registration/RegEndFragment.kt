package com.example.benefit.ui.auth.registration

/**
 * Created by jahon on 03-Sep-20
 */
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.benefit.R
import com.example.benefit.ui.base.BaseFragment
import com.example.benefit.ui.main.MainActivity
import com.example.benefit.ui.main.MainActivity.Companion.IS_GOING_DEPOSIT
import com.example.benefit.ui.main.home.bsd_add_card.AddCardBSD
import kotlinx.android.synthetic.main.fragment_reg_end.*

class RegEndFragment : BaseFragment(R.layout.fragment_reg_end) {

    private val viewModel: RegistrationViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        attachListeners()
    }

    private fun attachListeners() {
        tvSkip.setOnClickListener {
            startActivity(Intent(requireActivity(), MainActivity::class.java))
            ((parentFragment as NavHostFragment).parentFragment as RegistrationBSD).dismiss()
        }
        btnDepositCard.setOnClickListener {
            startActivity(Intent(requireActivity(), MainActivity::class.java).apply {
                this.putExtra(IS_GOING_DEPOSIT, true)
            })
            (parentFragment as? RegistrationBSD)?.dismiss()
            (parentFragment as? AddCardBSD)?.dismiss()
        }
        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

    }

}