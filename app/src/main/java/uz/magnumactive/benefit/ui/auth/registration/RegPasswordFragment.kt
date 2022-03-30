package uz.magnumactive.benefit.ui.auth.registration

/**
 * Created by jahon on 03-Sep-20
 */
import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_reg_password.*
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.ui.base.BaseFragment

class RegPasswordFragment : BaseFragment(R.layout.fragment_reg_password) {


    private val viewModel: RegistrationViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        attachListeners()
        subscribeObservers()
    }

    private fun subscribeObservers() {

        viewModel.setPasswordResp.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_regPasswordFragment_to_regProfileSetupFragment)
        }


        viewModel.isLoading.observe(viewLifecycleOwner) {
            when (it ?: return@observe) {
                true -> {
                    tvError.visibility = View.GONE
                    progress.visibility = View.VISIBLE
                }
                else -> {
                    progress.visibility = View.GONE
                }
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            tvError.text = it ?: return@observe
            tvError.visibility = View.VISIBLE
        }

    }

    private fun attachListeners() {

        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        edtCode.doOnTextChanged { text, start, before, count ->
            if (!text.isNullOrBlank() && text.length == 4) {
                btnConfirm.myEnabled(true)
            } else {
                btnConfirm.myEnabled(false)
            }

        }

        btnConfirm.setOnClickListener {
            viewModel.setPassword(edtCode.text.toString())
        }
    }

}