package com.example.benefit.ui.auth.registration

/**
 * Created by jahon on 03-Sep-20
 */
import android.content.Context
import android.content.Intent
import android.content.res.AssetManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.benefit.BuildConfig
import com.example.benefit.R
import com.example.benefit.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_reg_phone.*
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

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

        viewModel.signUpResp.observe(viewLifecycleOwner, {
            findNavController().navigate(R.id.action_regPhoneFragment_to_regCodeFragment)
        })


    }


    fun readFromAssets(fileName: String) {
        val assetManager: AssetManager = requireActivity().assets
        val input: InputStream?
        val out: OutputStream?
        val file = File(requireContext().filesDir, fileName)
        try {
            input = assetManager.open(fileName)
            out = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                requireContext().openFileOutput(file.name, Context.MODE_PRIVATE)
            } else {
                requireContext().openFileOutput(file.name, Context.MODE_WORLD_READABLE)
            }
            copyFile(input, out)
            input.close()
            out.flush()
            out.close()
        } catch (e: Exception) {
            Log.e("tag", e.message)
        }
        val pdfFileURI: Uri
        val intent = Intent(Intent.ACTION_VIEW)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            pdfFileURI = FileProvider.getUriForFile(
                requireContext(),
                BuildConfig.APPLICATION_ID + ".fileprovider",
                file
            )
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        } else {
            pdfFileURI = Uri.parse("file://" + requireContext().filesDir + "/" + fileName)
        }
        intent.setDataAndType(pdfFileURI, "application/pdf")
        requireActivity().startActivity(intent)
    }

    @Throws(IOException::class)
    private fun copyFile(input: InputStream?, out: OutputStream?) {
        val buffer = ByteArray(1024)
        var read = 0
        while (input!!.read(buffer).also { read = it } != -1) {
            out!!.write(buffer, 0, read)
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
            readFromAssets("agreement.pdf")
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