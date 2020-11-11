package com.example.benefit.ui.order_card

import android.animation.LayoutTransition
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.widget.doOnTextChanged
import com.asksira.bsimagepicker.BSImagePicker
import com.bumptech.glide.Glide
import com.example.benefit.R
import com.example.benefit.ui.select_card_type.ECardType
import com.example.benefit.util.SizeUtils
import com.example.benefit.util.loadBitmap
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_order_card.*


@AndroidEntryPoint
class OrderCardActivity : AppCompatActivity(), BSImagePicker.OnSingleImageSelectedListener,
    BSImagePicker.ImageLoaderDelegate {


    companion object {
        const val EXTRA_CARD_TYPE = "EXTRA_CARD_TYPE"
        const val PASSPORT = "PASSPORT"
        const val PASSPORT_WITH_PHOTO = "PASSPORT_WITH_PHOTO"

        //        const val INN = "INN"
        const val WORK_PROOF = "WORK_PROOF"
        const val REQ_ORDER_CARD = 83
    }

    lateinit var cardType: ECardType
    private val viewModel: OrderCardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_card)

        cardType = intent.getSerializableExtra(EXTRA_CARD_TYPE) as ECardType

        setupViews()
        attachListeners()
        subscribeObservers()

        viewModel.setCurrentStep(1)
    }

    private fun attachListeners() {

        edtLimit.doOnTextChanged { text, _, _, _ ->
            btnNextLimit.isEnabled = !text.isNullOrBlank() && text.toString().toInt() > 0
        }

        btnNextTerms.setOnClickListener { viewModel.acceptTerms(if (cardType == ECardType.ZOOM) 2 else 1) }

        cbTermsAgree.setOnCheckedChangeListener { _, isChecked ->
            btnNextTerms.isEnabled = isChecked
        }

        btnClose.setOnClickListener {
            setResult(RESULT_OK)
            finish()
        }

        ivPassportPhoto.setOnClickListener {
            BSImagePicker.Builder("com.example.benefit.fileprovider")
                .setTag(PASSPORT).build()
                .show(supportFragmentManager, "")
        }

        ivPhotoWithPassport.setOnClickListener {
            BSImagePicker.Builder("com.example.benefit.fileprovider")
                .setTag(PASSPORT_WITH_PHOTO).build()
                .show(supportFragmentManager, "")
        }

        ivWorkProve.setOnClickListener {
            BSImagePicker.Builder("com.example.benefit.fileprovider")
                .setTag(WORK_PROOF).build()
                .show(supportFragmentManager, "")
        }

        btnNextPhotoPassport.setOnClickListener { viewModel.addPassportPhoto() }
        btnNextPhotoWithPassport.setOnClickListener { viewModel.addWithPassportPhoto() }
        btnNextWorkProve.setOnClickListener { viewModel.addWorkProof() }

        edtAddress.doOnTextChanged { text, _, _, _ ->
            btnNextAddress.isEnabled = !text.isNullOrBlank()
        }

        btnNextAddress.setOnClickListener { viewModel.addAddress(edtAddress.text.toString()) }
        btnNextSelectBranch.setOnClickListener {
            viewModel.nextStep()
            if (cardType.isZoom()) viewModel.nextStep()
        }
        btnNextLimit.setOnClickListener { viewModel.addLimitSum(edtLimit.text.toString()) }
        btnNextSendReq.setOnClickListener { viewModel.complete() }
        btnBackLimit.setOnClickListener { viewModel.previousStep() }
        btnBackAddress.setOnClickListener { viewModel.previousStep() }
        btnBackPhotoPassport.setOnClickListener { viewModel.previousStep() }
        btnBackPhotoWithPassport.setOnClickListener { viewModel.previousStep() }
        btnBackSelectBranch.setOnClickListener { viewModel.previousStep() }
        btnBackWorkProve.setOnClickListener { viewModel.previousStep() }
        btnBackSendReq.setOnClickListener {
            viewModel.previousStep()
            if (cardType.isZoom()) viewModel.previousStep()
        }

    }

    private fun subscribeObservers() {
        viewModel.isCompleted.observe(this, {
            val completed = it ?: return@observe
            btnClose.visibility = View.VISIBLE
            viewModel.nextStep()
        })

        viewModel.acceptTermsResp.observe(this, {
            val response = it ?: return@observe
            viewModel.nextStep()
        })

        viewModel.isLoading.observe(this, {
            when (it ?: return@observe) {
                true -> disableAllButtons()
                else -> enableAllButtons()
            }
        })

        viewModel.currentStep.observe(this, { step ->
            when (step) {
                1 -> {
                    hideAllTermsContent()
                    userTermsContent.visibility = View.VISIBLE
                    hideAllChecked()
                    hideAllIcOval()
                    icOvalUserTerms.visibility = View.VISIBLE
                    fixConstraints(R.id.lineProgressBg)
                    scrollToView(userTermsContent)
                }
                2 -> {
                    hideAllTermsContent()
                    photoPassportContent.visibility = View.VISIBLE
                    hideAllChecked()
                    ivTermsChecked.visibility = View.VISIBLE
                    hideAllIcOval()
                    icOvalPhotoPassport.visibility = View.VISIBLE
                    fixConstraints(R.id.tvNum2)
                    scrollToView(photoPassportContent)
                }
                3 -> {
                    hideAllTermsContent()
                    hideAllChecked()
                    hideAllIcOval()
                    photoWithPassportContent.visibility = View.VISIBLE
                    icOvalPhotoWithPassport.visibility = View.VISIBLE
                    ivTermsChecked.visibility = View.VISIBLE
                    ivPhotoPassportChecked.visibility = View.VISIBLE

                    fixConstraints(R.id.tvNum3)
                    scrollToView(photoWithPassportContent)
                }
                4 -> {
                    hideAllTermsContent()
                    hideAllChecked()
                    hideAllIcOval()

                    workProveContent.visibility = View.VISIBLE
                    icOvalWorkProve.visibility = View.VISIBLE
                    ivTermsChecked.visibility = View.VISIBLE
                    ivPhotoPassportChecked.visibility = View.VISIBLE
                    ivPhotoWithPassportChecked.visibility = View.VISIBLE

                    fixConstraints(R.id.tvNum4)
                    scrollToView(workProveContent)
                }
                5 -> {
                    hideAllTermsContent()
                    hideAllChecked()
                    hideAllIcOval()
                    addressContent.visibility = View.VISIBLE
                    icOvalAddress.visibility = View.VISIBLE
                    ivTermsChecked.visibility = View.VISIBLE
                    ivPhotoPassportChecked.visibility = View.VISIBLE
                    ivPhotoWithPassportChecked.visibility = View.VISIBLE
                    ivWorkProveChecked.visibility = View.VISIBLE
                    fixConstraints(R.id.tvNum5)
                    scrollToView(addressContent)
                }
                6 -> {
                    hideAllTermsContent()
                    showAllChecked()
                    hideAllIcOval()
                    selectBranchContent.visibility = View.VISIBLE
                    icOvalSelectBranch.visibility = View.VISIBLE
                    ivSelectBranchChecked.visibility = View.INVISIBLE
                    if (!cardType.isZoom()) ivLimitChecked.visibility = View.INVISIBLE
                    ivSendRequestChecked.visibility = View.INVISIBLE
                    fixConstraints(R.id.tvNum7)
                }
                7 -> {
                    if (cardType.isZoom()) return@observe
                    hideAllTermsContent()
                    showAllChecked()
                    hideAllIcOval()
                    limitContent.visibility = View.VISIBLE
                    icOvalLimit.visibility = View.VISIBLE
                    ivLimitChecked.visibility = View.INVISIBLE
                    ivSendRequestChecked.visibility = View.INVISIBLE
                    fixConstraints(R.id.tvNum8)
                }
                8 -> {
                    hideAllTermsContent()
                    hideAllChecked()
                    hideAllIcOval()
                    sendReqContent.visibility = View.VISIBLE
                    icOvalSendReq.visibility = View.VISIBLE
                    showAllChecked()
                    ivSendRequestChecked.visibility = View.INVISIBLE
                    fixConstraints(R.id.tvNum9)
                    subTitleSendReq.text = getString(R.string.send_req_pre_end_text, cardType)
                }
                9 -> {
                    hideAllTermsContent()
                    hideAllIcOval()
                    showAllChecked()
                    sendReqContent.visibility = View.VISIBLE
                    fixConstraints(R.id.tvNum9)
                    subTitleSendReq.text = getString(R.string.send_req_end_text)
                    btnClose.visibility = View.VISIBLE
                    btnNextSendReq.visibility = View.INVISIBLE
                    btnBackSendReq.visibility = View.INVISIBLE
                }

            }

        })

    }


    override fun onSingleImageSelected(uri: Uri, tag: String?) {
        val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)

        when (tag) {
            PASSPORT -> {
                viewModel.passportBitmap = bitmap
                ivPassportPhoto.loadBitmap(bitmap)
            }
            PASSPORT_WITH_PHOTO -> {
                viewModel.withPassportBitmap = bitmap
                ivPhotoWithPassport.loadBitmap(bitmap)
            }
            WORK_PROOF -> {
                viewModel.workProofBitmap = bitmap
                ivWorkProve.loadBitmap(bitmap)
            }
        }

    }

    override fun loadImage(imageUri: Uri, ivImage: ImageView) {
        Glide.with(this).load(imageUri).into(ivImage)
    }

    private fun enableAllButtons() {
        btnNextTerms.isEnabled = true
        btnNextPhotoPassport.isEnabled = true
        btnNextWorkProve.isEnabled = true
        btnNextPhotoWithPassport.isEnabled = true
        btnNextAddress.isEnabled = true
        btnNextSelectBranch.isEnabled = true
        btnBackSendReq.isEnabled = true
        if (!cardType.isZoom()) btnBackLimit.isEnabled = true
        btnBackAddress.isEnabled = true
        btnBackPhotoPassport.isEnabled = true
        btnBackPhotoWithPassport.isEnabled = true
        btnBackSelectBranch.isEnabled = true
        btnBackWorkProve.isEnabled = true
        btnBackSendReq.isEnabled = true
    }

    private fun disableAllButtons() {
        btnNextTerms.isEnabled = false
        btnNextPhotoPassport.isEnabled = false
        btnNextWorkProve.isEnabled = false
        btnNextPhotoWithPassport.isEnabled = false
        btnNextAddress.isEnabled = false
        btnNextSelectBranch.isEnabled = false
        btnBackSendReq.isEnabled = false
        if (!cardType.isZoom()) btnBackLimit.isEnabled = false
        btnBackAddress.isEnabled = false
        btnBackPhotoPassport.isEnabled = false
        btnBackPhotoWithPassport.isEnabled = false
        btnBackSelectBranch.isEnabled = false
        btnBackWorkProve.isEnabled = false
        btnBackSendReq.isEnabled = false
    }

    private fun showAllChecked() {
        ivTermsChecked.visibility = View.VISIBLE
        ivPhotoPassportChecked.visibility = View.VISIBLE
        ivPhotoWithPassportChecked.visibility = View.VISIBLE
        ivWorkProveChecked.visibility = View.VISIBLE
        ivAddressChecked.visibility = View.VISIBLE
        ivSelectBranchChecked.visibility = View.VISIBLE
        if (!cardType.isZoom()) ivLimitChecked.visibility = View.VISIBLE
        ivSendRequestChecked.visibility = View.VISIBLE
    }

    private fun fixConstraints(layoutId: Int) {
        ConstraintSet().apply {
            clone(contentParent)
            connect(R.id.lineProgress, ConstraintSet.BOTTOM, layoutId, ConstraintSet.TOP, 0)
            applyTo(contentParent)
        }
    }

    private fun hideAllIcOval() {
        icOvalUserTerms.visibility = View.INVISIBLE
        icOvalPhotoPassport.visibility = View.INVISIBLE
        icOvalPhotoWithPassport.visibility = View.INVISIBLE
        icOvalWorkProve.visibility = View.INVISIBLE
        icOvalAddress.visibility = View.INVISIBLE
        icOvalSelectBranch.visibility = View.INVISIBLE
        if (!cardType.isZoom()) icOvalLimit.visibility = View.INVISIBLE
        icOvalSendReq.visibility = View.INVISIBLE
    }

    private fun scrollToView(view: View) {
        scrollView.post {
            if (view.top < llParent.height - SizeUtils.getScreenHeight(this)) {
                scrollView.scrollTo(0, view.top)
            }
        }
    }

    private fun setupViews() {

        llParent.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        userTermsContent.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        photoPassportContent.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        photoWithPassportContent.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        workProveContent.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        addressContent.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        if (!cardType.isZoom()) {
            limitContent.visibility = View.VISIBLE
            tvNum8.visibility = View.VISIBLE
            ivLimitChecked.visibility = View.VISIBLE
            icOvalLimit.visibility = View.VISIBLE
            titleLimit.visibility = View.VISIBLE
            limitContent.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        }
        selectBranchContent.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        sendReqContent.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)


    }

    private fun hideAllChecked() {
        ivTermsChecked.visibility = View.INVISIBLE
        ivPhotoPassportChecked.visibility = View.INVISIBLE
        ivPhotoWithPassportChecked.visibility = View.INVISIBLE
        ivWorkProveChecked.visibility = View.INVISIBLE
        ivAddressChecked.visibility = View.INVISIBLE
        ivSelectBranchChecked.visibility = View.INVISIBLE
        if (!cardType.isZoom()) ivLimitChecked.visibility = View.INVISIBLE
        ivSendRequestChecked.visibility = View.INVISIBLE
    }

    private fun hideAllTermsContent() {
        userTermsContent.visibility = View.GONE
        photoPassportContent.visibility = View.GONE
        photoWithPassportContent.visibility = View.GONE
        workProveContent.visibility = View.GONE
        addressContent.visibility = View.GONE
        selectBranchContent.visibility = View.GONE
        if (!cardType.isZoom()) limitContent.visibility = View.GONE
        sendReqContent.visibility = View.GONE
    }
}
