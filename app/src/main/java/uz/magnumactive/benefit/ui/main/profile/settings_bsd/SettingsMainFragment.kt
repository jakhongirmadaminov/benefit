package uz.magnumactive.benefit.ui.main.profile.settings_bsd

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.asksira.bsimagepicker.BSImagePicker
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_settings_main.*
import splitties.experimental.ExperimentalSplittiesApi
import splitties.preferences.edit
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.ui.base.BaseFragment
import uz.magnumactive.benefit.util.*


/**
 * Created by jahon on 03-Sep-20
 */
class SettingsMainFragment : BaseFragment(R.layout.fragment_settings_main),
    BSImagePicker.OnSingleImageSelectedListener,
    BSImagePicker.ImageLoaderDelegate {

    private val viewModel: SetingsMainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        attachListeners()
        subscribeObservers()
    }

    private fun setupViews() {

        AppPrefs.avatar?.let {
            ivPhoto.loadImageUrl(it)
        } ?: run {
            ivPhoto.setImageResource(R.drawable.ic_avatar_sample)
        }

        edtSurname.setText(AppPrefs.lastName)
        edtName.setText(AppPrefs.firstName)
        lblPhoneNum.text = AppPrefs.phoneNumber
        cardPhoto.setBackgroundResource(R.drawable.shape_oval)
        cardPhotoIcon.setBackgroundResource(R.drawable.shape_oval)
    }

    @ExperimentalSplittiesApi
    private fun subscribeObservers() {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            progress.isVisible = it
        }

        viewModel.uploadUserInfoResp.observe(viewLifecycleOwner) { uploadResp ->
            ((parentFragment as NavHostFragment).parentFragment as SettingsBSD).dismiss()
        }
        viewModel.userInfoResp.observe(viewLifecycleOwner) {
            val result = it ?: return@observe
            when (result) {
                is ResultError -> {
                    Snackbar.make(
                        clParent,
                        result.message ?: "Something went wrong!",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                is ResultSuccess -> {
                    AppPrefs.edit {
                        firstName = result.value.first_name
                        this.lastName = result.value.last_name
                        result.value.gender?.let {
                            this.gender = it
                        }
                        this.dobMillis = result.value.birth_day!!.toLong()
                    }
                    edtName.setText(result.value.first_name)
                    edtSurname.setText(result.value.last_name)
                }
            }
        }
        viewModel.errorMessage.observe(viewLifecycleOwner) {
            Snackbar.make(clParent, it, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun attachListeners() {

        cardPhoto.setOnClickListener {
            BSImagePicker.Builder("uz.magnumactive.benefit.fileprovider").build()
                .show(childFragmentManager, "picker")
        }

        llChangePhoneNum.setOnClickListener {
            findNavController().navigate(SettingsMainFragmentDirections.actionSettingsMainFragmentToSettingsCodeFragment())
        }
        tvChangeCode.setOnClickListener {
            findNavController().navigate(R.id.action_settingsMainFragment_to_settingsChangeCodeFragment)
        }
        tvChangeLang.setOnClickListener {
            findNavController().navigate(R.id.action_settingsMainFragment_to_settingsLangFragment)
        }

        tvReady.setOnClickListener {
            viewModel.updateUserInfo(edtSurname.text.toString(), edtName.text.toString())
            selectedBitmap?.let { viewModel.uploadAvatar(it) }
        }

    }

    var selectedBitmap: Bitmap? = null
    override fun onSingleImageSelected(uri: Uri, tag: String?) {
        selectedBitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, uri)
        ivPhoto.loadBitmap(selectedBitmap!!)
    }

    override fun loadImage(imageUri: Uri, ivImage: ImageView) {
        Glide.with(this).load(imageUri).into(ivImage)
    }

}