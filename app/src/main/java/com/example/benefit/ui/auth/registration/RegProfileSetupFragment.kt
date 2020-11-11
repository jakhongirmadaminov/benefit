package com.example.benefit.ui.auth.registration

import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.asksira.bsimagepicker.BSImagePicker
import com.bumptech.glide.Glide
import com.example.benefit.R
import com.example.benefit.util.AppPrefs
import com.example.benefit.util.Constants.MONTHS
import com.example.benefit.util.loadBitmap
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_reg_profile_setup.*
import org.joda.time.DateTime
import javax.inject.Inject


/**
 * Created by jahon on 03-Sep-20
 */
@AndroidEntryPoint
class RegProfileSetupFragment @Inject constructor() :
    Fragment(R.layout.fragment_reg_profile_setup), BSImagePicker.OnSingleImageSelectedListener,
    BSImagePicker.ImageLoaderDelegate, IOnMonthSelected {

    private val viewModel: RegistrationViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        attachListeners()
        subscribeObservers()
    }

    private fun subscribeObservers() {

        viewModel.uploadUserInfoResp.observe(viewLifecycleOwner, {
            findNavController().navigate(R.id.action_regProfileSetupFragment_to_regCardActivationFragment)
        })

        viewModel.uploadAvatarResp.observe(viewLifecycleOwner, {

        })

        viewModel.isLoading.observe(viewLifecycleOwner, {
            when (it ?: return@observe) {
                true -> {
                    tvError.visibility = View.GONE
                    progress.visibility = View.VISIBLE
                }
                else -> {
                    progress.visibility = View.GONE
                }
            }
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, {
            tvError.visibility = View.VISIBLE
            tvError.text = it ?: return@observe
        })


    }

    private fun setupViews() {

        cardPhoto.setBackgroundResource(R.drawable.shape_round_window_bg_color)
        cardPhotoIcon.setBackgroundResource(R.drawable.shape_round_window_bg_color)

    }

    private fun attachListeners() {

        edtDay.doOnTextChanged { text, start, before, count ->
            validateFields()
        }

        edtName.doOnTextChanged { text, start, before, count ->
            validateFields()
        }

        edtLastName.doOnTextChanged { text, start, before, count ->
            validateFields()
        }

        btnReady.setOnClickListener {
            viewModel.uploadProfileInfo(
                edtName.text.toString(),
                edtLastName.text.toString(),
                if (rbMale.isChecked) 0 else 1,
                dob!!.millis
            )

        }


        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }


        edtMonth.setOnClickListener {
            DialogDOBSelector.newInstance(this).show(parentFragmentManager, "")
        }
        edtYear.setOnClickListener {
            DialogDOBSelector.newInstance(this).show(parentFragmentManager, "")
        }
        edtDay.setOnClickListener {
            DialogDOBSelector.newInstance(this).show(parentFragmentManager, "")
        }

        cardPhoto.setOnClickListener {
            BSImagePicker.Builder("com.example.benefit.fileprovider").build()
                .show(childFragmentManager, "picker")
        }
    }

    private fun validateFields() {

        btnReady.isEnabled = viewModel.uploadAvatarResp.value != null &&
                dob != null &&
                !edtName.text.isNullOrBlank() &&
                !edtLastName.text.isNullOrBlank()

    }

    override fun onSingleImageSelected(uri: Uri, tag: String?) {
        val bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, uri)
        ivPhoto.loadBitmap(bitmap)
        viewModel.uploadAvatar(bitmap)
    }

    override fun loadImage(imageUri: Uri, ivImage: ImageView) {
        Glide.with(this).load(imageUri).into(ivImage)
    }

    var dob: DateTime? = null

    override fun onDOBSelected(day: Int, month: Int, year: Int) {
        dob = DateTime(year, month, day, 0, 0)
        edtDay.text = day.toString()
        edtMonth.text = MONTHS[AppPrefs.language]!![month]
        edtYear.text = year.toString()
    }


}