package com.example.benefit.ui.auth.registration

import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.asksira.bsimagepicker.BSImagePicker
import com.bumptech.glide.Glide
import com.example.benefit.R
import com.example.benefit.ui.auth.login.LoginViewModel
import com.example.benefit.util.getFileName
import com.example.benefit.util.loadBitmap
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_reg_profile_setup.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import javax.inject.Inject


/**
 * Created by jahon on 03-Sep-20
 */
@AndroidEntryPoint
class RegProfileSetupFragment @Inject constructor() :
    Fragment(R.layout.fragment_reg_profile_setup), BSImagePicker.OnSingleImageSelectedListener,
    BSImagePicker.ImageLoaderDelegate, IOnMonthSelected {

    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        attachListeners()
    }

    private fun setupViews() {

        cardPhoto.setBackgroundResource(R.drawable.shape_round_window_bg_color)
        cardPhotoIcon.setBackgroundResource(R.drawable.shape_round_window_bg_color)

    }

    private fun attachListeners() {
//        edtPhone.doOnTextChanged { text, start, before, count ->
//            if (!text.isNullOrBlank() && text.length == 9) {
//                btnGetCode.myEnabled(true)
//                lblYoullReceiveCode.visibility = View.VISIBLE
//                lblYoullReceiveCode.text =
//                    getString(R.string.you_will_receive_code, tvPhoneStart.text.toString() + text)
//            } else {
//                btnGetCode.myEnabled(false)
//                lblYoullReceiveCode.visibility = View.GONE
//            }
//
//        }
//
        btnReady.setOnClickListener {
//            loginViewModel.login("998" + edtPhone.text.toString())

            findNavController().navigate(R.id.action_regProfileSetupFragment_to_regPasswordFragment)
        }


        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }


        edtMonth.setOnClickListener {
            DialogMonthSelector.newInstance(this).show(parentFragmentManager, "")
        }

        cardPhoto.setOnClickListener {
            BSImagePicker.Builder("com.example.benefit.fileprovider").build()
                .show(childFragmentManager, "picker")
        }
    }

    override fun onSingleImageSelected(uri: Uri, tag: String?) {
        val bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, uri)
        ivPhoto.loadBitmap(bitmap)
//        viewModel.uploadAvatar(bitmap)
    }

    override fun loadImage(imageUri: Uri, ivImage: ImageView) {
        Glide.with(this).load(imageUri).into(ivImage)
    }

    override fun onMonthSelected(month: Int) {


    }


}