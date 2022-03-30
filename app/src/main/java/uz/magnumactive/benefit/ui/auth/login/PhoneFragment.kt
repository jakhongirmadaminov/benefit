package uz.magnumactive.benefit.ui.auth.login

/**
 * Created by jahon on 03-Sep-20
 */
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_phone.*
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.ui.base.BaseFragment

class PhoneFragment : BaseFragment(R.layout.fragment_phone) {

    private val viewModel: LoginViewModel by activityViewModels()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        attachListeners()
        subscribeObservers()
    }

    private fun subscribeObservers() {

        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            when (it ?: return@Observer) {
                true -> {
                    progress.visibility = View.VISIBLE
                    lblYoullReceiveCode.visibility = View.INVISIBLE
                }
                else -> {
                    progress.visibility = View.INVISIBLE
                    lblYoullReceiveCode.visibility = View.VISIBLE
                }
            }
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            lblYoullReceiveCode.text = it ?: return@Observer
            lblYoullReceiveCode.setTextColor(Color.RED)
        })

        viewModel.loginResp.observe(viewLifecycleOwner, Observer {
            it ?: return@Observer

            val action = PhoneFragmentDirections.actionPhoneFragmentToCodeFragment(
                edtPhone.text.toString()
            )
            findNavController().navigate(action)
        })


    }

    private fun attachListeners() {
        edtPhone.doOnTextChanged { text, start, before, count ->
            lblYoullReceiveCode.setTextColor(
                ContextCompat.getColor(requireContext(), R.color.textlightGrey)
            )

            if (!text.isNullOrBlank() && text.length == 9) {
                btnGetCode.myEnabled(true)
                lblYoullReceiveCode.visibility = View.VISIBLE
                lblYoullReceiveCode.text =
                    getString(R.string.you_will_receive_code, tvPhoneStart.text.toString() + text)
            } else {
                btnGetCode.myEnabled(false)
                lblYoullReceiveCode.visibility = View.GONE
            }

        }

        btnGetCode.setOnClickListener {
            viewModel.login(edtPhone.text.toString())

        }


    }

}