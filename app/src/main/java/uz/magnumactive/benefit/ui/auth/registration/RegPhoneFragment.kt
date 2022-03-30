package uz.magnumactive.benefit.ui.auth.registration

/**
 * Created by jahon on 03-Sep-20
 */
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.ui.base.BaseFragment
import uz.magnumactive.benefit.util.PDFHelper
import kotlinx.android.synthetic.main.fragment_reg_phone.*

class RegPhoneFragment : BaseFragment(R.layout.fragment_reg_phone) {


    private val viewModel: RegistrationViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                    progress.visibility = View.GONE
                    lblYoullReceiveCode.visibility = View.VISIBLE
                }
            }
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            lblYoullReceiveCode.text = it ?: return@Observer
            lblYoullReceiveCode.visibility = View.VISIBLE
            lblYoullReceiveCode.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.error_red
                )
            )
        })

        viewModel.signUpResp.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_regPhoneFragment_to_regCodeFragment)
        }


    }


    private fun attachListeners() {
        edtPhone.doOnTextChanged { text, start, before, count ->
            if (!text.isNullOrBlank() && text.length == 9 && cbTermsAgree.isChecked) {
                btnGetCode.myEnabled(true)
                lblYoullReceiveCode.visibility = View.VISIBLE
                lblYoullReceiveCode.text =
                    getString(R.string.you_will_receive_code, tvPhoneStart.text.toString() + text)
                lblYoullReceiveCode.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.textlightGrey
                    )
                )
            } else {
                btnGetCode.myEnabled(false)
                lblYoullReceiveCode.visibility = View.GONE

            }

        }

        tvAgreement.setOnClickListener {
            requireActivity().startActivity(
                PDFHelper.makeIntentFromPdfAsset(
                    requireActivity(),
                    "agreement.pdf"
                )
            )
        }

        cbTermsAgree.setOnCheckedChangeListener { buttonView, isChecked ->
            if (!edtPhone.text.isNullOrBlank() && edtPhone.text.length == 9 && cbTermsAgree.isChecked) {
                btnGetCode.myEnabled(true)
                lblYoullReceiveCode.visibility = View.VISIBLE
                lblYoullReceiveCode.text =
                    getString(
                        R.string.you_will_receive_code,
                        tvPhoneStart.text.toString() + edtPhone.text
                    )
            } else {
                btnGetCode.myEnabled(false)
                lblYoullReceiveCode.visibility = View.GONE
            }
        }
        btnGetCode.setOnClickListener {
            viewModel.signup(
                edtPhone.text.toString(),
                null/*if (edtReferal.text.isNullOrBlank()) null else edtReferal.text.toString()*/
            )
        }
    }


}