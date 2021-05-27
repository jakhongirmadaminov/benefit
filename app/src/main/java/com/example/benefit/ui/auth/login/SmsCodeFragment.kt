package com.example.benefit.ui.auth.login

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Html
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.benefit.R
import com.example.benefit.ui.auth.registration.ResponseState
import com.example.benefit.util.AppPrefs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_code.*
import splitties.experimental.ExperimentalSplittiesApi
import splitties.preferences.edit
import java.sql.Time
import javax.inject.Inject

/**
 * Created by jahon on 03-Sep-20
 */
import com.example.benefit.ui.base.BaseFragment

class SmsCodeFragment : BaseFragment(R.layout.fragment_code) {


    private val viewModel: LoginViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        attachListeners()
        subscribeObservers()

        edtCode.setText(viewModel.code.toString())
    }


    @OptIn(ExperimentalSplittiesApi::class)
    private fun subscribeObservers() {

        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            when (it ?: return@Observer) {
                true -> {
                    progress.visibility = View.VISIBLE
                    tvError.visibility = View.INVISIBLE
                }
                else -> {
                    progress.visibility = View.GONE
                }
            }
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            tvError.text = it ?: return@Observer
            tvError.visibility = View.VISIBLE
        })

        viewModel.loginSmsResp.observe(viewLifecycleOwner, Observer {
            it ?: return@Observer
            findNavController().navigate(R.id.action_codeFragment_to_deviceCodeFragment)
        })


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
            viewModel.loginsms(edtCode.text.toString())
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

}