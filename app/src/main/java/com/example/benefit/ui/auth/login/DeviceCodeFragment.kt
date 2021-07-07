package com.example.benefit.ui.auth.login

/**
 * Created by jahon on 03-Sep-20
 */
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.example.benefit.R
import com.example.benefit.ui.auth.registration.ResponseState
import com.example.benefit.ui.base.BaseFragment
import com.example.benefit.ui.main.MainActivity
import com.example.benefit.util.AppPrefs
import kotlinx.android.synthetic.main.fragment_code.*
import kotlinx.android.synthetic.main.fragment_device_code.*
import kotlinx.android.synthetic.main.fragment_device_code.btnConfirm
import kotlinx.android.synthetic.main.fragment_device_code.edtCode
import kotlinx.android.synthetic.main.fragment_device_code.progress
import kotlinx.android.synthetic.main.fragment_device_code.tvError
import splitties.experimental.ExperimentalSplittiesApi
import splitties.preferences.edit

class DeviceCodeFragment : BaseFragment(R.layout.fragment_device_code) {


    private val viewModel: LoginViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        attachListeners()
        subscribeObservers()
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

        viewModel.loginCodeResp.observe(viewLifecycleOwner, Observer {
            it ?: return@Observer
            AppPrefs.edit {
                token = it.access_token
                pin = edtCode.text.toString()
            }

            startActivity(Intent(requireActivity(), MainActivity::class.java))
            ((parentFragment as NavHostFragment).parentFragment as LoginBSD).dismiss()
        })

    }

    private fun attachListeners() {
        edtCode.doOnTextChanged { text, start, before, count ->
            respState = ResponseState.NONE
            if (!text.isNullOrBlank() && text.length == 4) {
                btnConfirm.myEnabled(true)
            } else {
                btnConfirm.myEnabled(false)
            }
        }

        btnConfirm.setOnClickListener {
            viewModel.loginCode(edtCode.text.toString())
        }
    }

    var respState = ResponseState.NONE
    private var timerFinished: Boolean = false
    lateinit var timer: CountDownTimer


    private fun setupViews() {

    }

}