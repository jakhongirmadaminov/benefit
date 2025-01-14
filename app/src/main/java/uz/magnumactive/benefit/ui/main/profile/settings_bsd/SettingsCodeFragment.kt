package uz.magnumactive.benefit.ui.main.profile.settings_bsd

/**
 * Created by jahon on 03-Sep-20
 */
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Html
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_settings_code.*
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.ui.auth.registration.ResponseState
import uz.magnumactive.benefit.ui.base.BaseFragment
import java.sql.Time

class SettingsCodeFragment : BaseFragment(R.layout.fragment_settings_code) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        attachListeners()
        subscribeObservers()
    }

    private fun setupViews() {

        timer = object : CountDownTimer(59, 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                if (respState == ResponseState.ERROR) return
                val time = Time(millisUntilFinished)
                val format = "%1$02d"
                setTimerText(
                    String.format(format, time.minutes) + ":" + String.format(
                        format,
                        time.seconds
                    )
                )
            }

            override fun onFinish() {
                timerFinished = true
                if (respState == ResponseState.ERROR) return
                setupRequestSmsText()
            }

        }.start()
    }

    private fun subscribeObservers() {
    }

    private fun attachListeners() {
        edtCode.doOnTextChanged { text, start, before, count ->
            respState = ResponseState.NONE
            if (timerFinished) setupRequestSmsText()
            if (!text.isNullOrBlank() && text.length == 4) {
                btnConfirm.myEnabled(true)
            } else {
                btnConfirm.myEnabled(false)
            }
        }


        btnConfirm.setOnClickListener {
            findNavController().navigate(R.id.action_settingsCodeFragment_to_settingsPhoneFragment)
        }

        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    var respState = ResponseState.NONE
    private var timerFinished: Boolean = false
    lateinit var timer: CountDownTimer

    private fun setupRequestSmsText() {
//        underline.visibility = View.VISIBLE
        tvResend.isClickable = true
        tvResend.text = Html.fromHtml("<u>" + getString(R.string.resend_sms) + "</u>")
//        tvResend.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_grey))
    }


    private fun setTimerText(s: String) {
//        underline.visibility = View.VISIBLE
//        tvResend.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_grey))
        tvResend.isClickable = false
        tvResend.text =
            Html.fromHtml("<u>" + getString(R.string.resend_timer_sms) + " " + s + "</u>")
    }


}