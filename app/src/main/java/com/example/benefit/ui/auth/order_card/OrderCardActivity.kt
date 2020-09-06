package com.example.benefit.ui.auth.order_card

import android.animation.LayoutTransition
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import com.example.benefit.R
import com.example.benefit.util.SizeUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_order_card.*


@AndroidEntryPoint
class OrderCardActivity : AppCompatActivity() {


    companion object {
        const val EXTRA_CARD_TYPE = "EXTRA_CARD_TYPE"
    }

    private var cardType: String? = null
    private val viewModel: OrderCardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_card)

        cardType = intent.getStringExtra(EXTRA_CARD_TYPE)

        setupViews()
        subscribeObservers()

//        Handler().postDelayed({
//
//            userTermsContent.visibility = View.GONE
//
//        }, 5000)
//
//        Handler().postDelayed({
//
//            userTermsContent.visibility = View.VISIBLE
//
//        }, 6000)
//
//        Handler().postDelayed({
//
//            userTermsContent.visibility = View.GONE
//
//        }, 9000)
        viewModel.setCurrentStep(1)
    }

    private fun subscribeObservers() {


        viewModel.currentStep.observe(this, { step ->

            when (step) {
                1 -> {
                    scrollView.post {
                        scrollView.scrollTo(0, userTermsContent.top)
                    }
                    userTermsContent.visibility = View.VISIBLE
                    photoPassportContent.visibility = View.GONE
                    photoWithPassportContent.visibility = View.GONE
                    workProveContent.visibility = View.GONE
                    addressContent.visibility = View.GONE
                    iNNContent.visibility = View.GONE
                    selectBranchContent.visibility = View.GONE
                    limitContent.visibility = View.GONE
                    sendReqContent.visibility = View.GONE

                    ivTermsChecked.visibility = View.INVISIBLE
                    ivPhotoPassportChecked.visibility = View.INVISIBLE
                    ivPhotoWithPassportChecked.visibility = View.INVISIBLE
                    ivWorkProveChecked.visibility = View.INVISIBLE
                    ivAddressChecked.visibility = View.INVISIBLE
                    ivINNChecked.visibility = View.INVISIBLE
                    ivSelectBranchChecked.visibility = View.INVISIBLE
                    ivLimitChecked.visibility = View.INVISIBLE
                    ivSendRequestChecked.visibility = View.INVISIBLE

                    icOvalUserTerms.visibility = View.VISIBLE
                    icOvalPhotoPassport.visibility = View.INVISIBLE
                    icOvalPhotoWithPassport.visibility = View.INVISIBLE
                    icOvalWorkProve.visibility = View.INVISIBLE
                    icOvalAddress.visibility = View.INVISIBLE
                    icOvalINN.visibility = View.INVISIBLE
                    icOvalSelectBranch.visibility = View.INVISIBLE
                    icOvalLimit.visibility = View.INVISIBLE
                    icOvalSendReq.visibility = View.INVISIBLE


                    val constraintSet = ConstraintSet()
                    constraintSet.clone(contentParent)
                    constraintSet.connect(
                        R.id.lineProgress,
                        ConstraintSet.BOTTOM,
                        R.id.lineProgressBg,
                        ConstraintSet.TOP,
                        0
                    )

                    constraintSet.applyTo(contentParent)
                }
                2 -> {
                    scrollView.post {
                        scrollView.scrollTo(0, photoPassportContent.top)
                    }
                    userTermsContent.visibility = View.GONE
                    photoPassportContent.visibility = View.VISIBLE
                    photoWithPassportContent.visibility = View.GONE
                    workProveContent.visibility = View.GONE
                    addressContent.visibility = View.GONE
                    iNNContent.visibility = View.GONE
                    selectBranchContent.visibility = View.GONE
                    limitContent.visibility = View.GONE
                    sendReqContent.visibility = View.GONE

                    ivTermsChecked.visibility = View.VISIBLE
                    ivPhotoPassportChecked.visibility = View.INVISIBLE
                    ivPhotoWithPassportChecked.visibility = View.INVISIBLE
                    ivWorkProveChecked.visibility = View.INVISIBLE
                    ivAddressChecked.visibility = View.INVISIBLE
                    ivINNChecked.visibility = View.INVISIBLE
                    ivSelectBranchChecked.visibility = View.INVISIBLE
                    ivLimitChecked.visibility = View.INVISIBLE
                    ivSendRequestChecked.visibility = View.INVISIBLE

                    icOvalUserTerms.visibility = View.INVISIBLE
                    icOvalPhotoPassport.visibility = View.VISIBLE
                    icOvalPhotoWithPassport.visibility = View.INVISIBLE
                    icOvalWorkProve.visibility = View.INVISIBLE
                    icOvalAddress.visibility = View.INVISIBLE
                    icOvalINN.visibility = View.INVISIBLE
                    icOvalSelectBranch.visibility = View.INVISIBLE
                    icOvalLimit.visibility = View.INVISIBLE
                    icOvalSendReq.visibility = View.INVISIBLE


                    val constraintSet = ConstraintSet()
                    constraintSet.clone(contentParent)
                    constraintSet.connect(
                        R.id.lineProgress,
                        ConstraintSet.BOTTOM,
                        R.id.tvNum2,
                        ConstraintSet.TOP,
                        0
                    )

                    constraintSet.applyTo(contentParent)


                }
                3 -> {
                    scrollView.post {
                        scrollView.scrollTo(0, photoWithPassportContent.top)
                    }
                    userTermsContent.visibility = View.GONE
                    photoPassportContent.visibility = View.GONE
                    photoWithPassportContent.visibility = View.VISIBLE
                    workProveContent.visibility = View.GONE
                    addressContent.visibility = View.GONE
                    iNNContent.visibility = View.GONE
                    selectBranchContent.visibility = View.GONE
                    limitContent.visibility = View.GONE
                    sendReqContent.visibility = View.GONE

                    icOvalUserTerms.visibility = View.INVISIBLE
                    icOvalPhotoPassport.visibility = View.INVISIBLE
                    icOvalPhotoWithPassport.visibility = View.VISIBLE
                    icOvalWorkProve.visibility = View.INVISIBLE
                    icOvalAddress.visibility = View.INVISIBLE
                    icOvalINN.visibility = View.INVISIBLE
                    icOvalSelectBranch.visibility = View.INVISIBLE
                    icOvalLimit.visibility = View.INVISIBLE
                    icOvalSendReq.visibility = View.INVISIBLE

                    ivTermsChecked.visibility = View.VISIBLE
                    ivPhotoPassportChecked.visibility = View.VISIBLE
                    ivPhotoWithPassportChecked.visibility = View.INVISIBLE
                    ivWorkProveChecked.visibility = View.INVISIBLE
                    ivAddressChecked.visibility = View.INVISIBLE
                    ivINNChecked.visibility = View.INVISIBLE
                    ivSelectBranchChecked.visibility = View.INVISIBLE
                    ivLimitChecked.visibility = View.INVISIBLE
                    ivSendRequestChecked.visibility = View.INVISIBLE

                    val constraintSet = ConstraintSet()
                    constraintSet.clone(contentParent)
                    constraintSet.connect(
                        R.id.lineProgress,
                        ConstraintSet.BOTTOM,
                        R.id.tvNum3,
                        ConstraintSet.TOP,
                        0
                    )

                    constraintSet.applyTo(contentParent)
                }
                4 -> {
                    scrollView.post {
                        if (workProveContent.top + SizeUtils.getScreenHeight(this) < llParent.height - SizeUtils.dpToPx(
                                this,
                                100
                            )
                        )     scrollView.scrollTo(0, workProveContent.top)
                    }
                    userTermsContent.visibility = View.GONE
                    photoPassportContent.visibility = View.GONE
                    photoWithPassportContent.visibility = View.GONE
                    workProveContent.visibility = View.VISIBLE
                    addressContent.visibility = View.GONE
                    iNNContent.visibility = View.GONE
                    selectBranchContent.visibility = View.GONE
                    limitContent.visibility = View.GONE
                    sendReqContent.visibility = View.GONE

                    icOvalUserTerms.visibility = View.INVISIBLE
                    icOvalPhotoPassport.visibility = View.INVISIBLE
                    icOvalPhotoWithPassport.visibility = View.INVISIBLE
                    icOvalWorkProve.visibility = View.VISIBLE
                    icOvalAddress.visibility = View.INVISIBLE
                    icOvalINN.visibility = View.INVISIBLE
                    icOvalSelectBranch.visibility = View.INVISIBLE
                    icOvalLimit.visibility = View.INVISIBLE
                    icOvalSendReq.visibility = View.INVISIBLE

                    ivTermsChecked.visibility = View.VISIBLE
                    ivPhotoPassportChecked.visibility = View.VISIBLE
                    ivPhotoWithPassportChecked.visibility = View.VISIBLE
                    ivWorkProveChecked.visibility = View.INVISIBLE
                    ivAddressChecked.visibility = View.INVISIBLE
                    ivINNChecked.visibility = View.INVISIBLE
                    ivSelectBranchChecked.visibility = View.INVISIBLE
                    ivLimitChecked.visibility = View.INVISIBLE
                    ivSendRequestChecked.visibility = View.INVISIBLE


                    val constraintSet = ConstraintSet()
                    constraintSet.clone(contentParent)
                    constraintSet.connect(
                        R.id.lineProgress,
                        ConstraintSet.BOTTOM,
                        R.id.tvNum4,
                        ConstraintSet.TOP,
                        0
                    )

                    constraintSet.applyTo(contentParent)
                }
                5 -> {
                    scrollView.post {
                        if (addressContent.top + SizeUtils.getScreenHeight(this) < llParent.height - SizeUtils.dpToPx(
                                this,
                                100
                            )
                        )    scrollView.scrollTo(0, addressContent.top)
                    }
                    userTermsContent.visibility = View.GONE
                    photoPassportContent.visibility = View.GONE
                    photoWithPassportContent.visibility = View.GONE
                    workProveContent.visibility = View.GONE
                    addressContent.visibility = View.VISIBLE
                    iNNContent.visibility = View.GONE
                    selectBranchContent.visibility = View.GONE
                    limitContent.visibility = View.GONE
                    sendReqContent.visibility = View.GONE

                    icOvalUserTerms.visibility = View.INVISIBLE
                    icOvalPhotoPassport.visibility = View.INVISIBLE
                    icOvalPhotoWithPassport.visibility = View.INVISIBLE
                    icOvalWorkProve.visibility = View.INVISIBLE
                    icOvalAddress.visibility = View.VISIBLE
                    icOvalINN.visibility = View.INVISIBLE
                    icOvalSelectBranch.visibility = View.INVISIBLE
                    icOvalLimit.visibility = View.INVISIBLE
                    icOvalSendReq.visibility = View.INVISIBLE

                    ivTermsChecked.visibility = View.VISIBLE
                    ivPhotoPassportChecked.visibility = View.VISIBLE
                    ivPhotoWithPassportChecked.visibility = View.VISIBLE
                    ivWorkProveChecked.visibility = View.VISIBLE
                    ivAddressChecked.visibility = View.INVISIBLE
                    ivINNChecked.visibility = View.INVISIBLE
                    ivSelectBranchChecked.visibility = View.INVISIBLE
                    ivLimitChecked.visibility = View.INVISIBLE
                    ivSendRequestChecked.visibility = View.INVISIBLE

                    val constraintSet = ConstraintSet()
                    constraintSet.clone(contentParent)
                    constraintSet.connect(
                        R.id.lineProgress,
                        ConstraintSet.BOTTOM,
                        R.id.tvNum5,
                        ConstraintSet.TOP,
                        0
                    )

                    constraintSet.applyTo(contentParent)
                }
                6 -> {
                    scrollView.post {
                        if (iNNContent.top + SizeUtils.getScreenHeight(this) < llParent.height - SizeUtils.dpToPx(
                                this,
                                100
                            )
                        )     scrollView.scrollTo(0, iNNContent.top)
                    }
                    userTermsContent.visibility = View.GONE
                    photoPassportContent.visibility = View.GONE
                    photoWithPassportContent.visibility = View.GONE
                    workProveContent.visibility = View.GONE
                    addressContent.visibility = View.GONE
                    iNNContent.visibility = View.VISIBLE
                    selectBranchContent.visibility = View.GONE
                    limitContent.visibility = View.GONE
                    sendReqContent.visibility = View.GONE

                    icOvalUserTerms.visibility = View.INVISIBLE
                    icOvalPhotoPassport.visibility = View.INVISIBLE
                    icOvalPhotoWithPassport.visibility = View.INVISIBLE
                    icOvalWorkProve.visibility = View.INVISIBLE
                    icOvalAddress.visibility = View.INVISIBLE
                    icOvalINN.visibility = View.VISIBLE
                    icOvalSelectBranch.visibility = View.INVISIBLE
                    icOvalLimit.visibility = View.INVISIBLE
                    icOvalSendReq.visibility = View.INVISIBLE

                    ivTermsChecked.visibility = View.VISIBLE
                    ivPhotoPassportChecked.visibility = View.VISIBLE
                    ivPhotoWithPassportChecked.visibility = View.VISIBLE
                    ivWorkProveChecked.visibility = View.VISIBLE
                    ivAddressChecked.visibility = View.VISIBLE
                    ivINNChecked.visibility = View.INVISIBLE
                    ivSelectBranchChecked.visibility = View.INVISIBLE
                    ivLimitChecked.visibility = View.INVISIBLE
                    ivSendRequestChecked.visibility = View.INVISIBLE

                    val constraintSet = ConstraintSet()
                    constraintSet.clone(contentParent)
                    constraintSet.connect(
                        R.id.lineProgress,
                        ConstraintSet.BOTTOM,
                        R.id.tvNum6,
                        ConstraintSet.TOP,
                        0
                    )

                    constraintSet.applyTo(contentParent)
                }
                7 -> {
                    scrollView.post {
                        if (selectBranchContent.top + SizeUtils.getScreenHeight(this) < llParent.height - SizeUtils.dpToPx(
                                this,
                                100
                            )
                        )
                            scrollView.scrollTo(0, selectBranchContent.top)
                    }
                    userTermsContent.visibility = View.GONE
                    photoPassportContent.visibility = View.GONE
                    photoWithPassportContent.visibility = View.GONE
                    workProveContent.visibility = View.GONE
                    addressContent.visibility = View.GONE
                    iNNContent.visibility = View.GONE
                    selectBranchContent.visibility = View.VISIBLE
                    limitContent.visibility = View.GONE
                    sendReqContent.visibility = View.GONE

                    icOvalUserTerms.visibility = View.INVISIBLE
                    icOvalPhotoPassport.visibility = View.INVISIBLE
                    icOvalPhotoWithPassport.visibility = View.INVISIBLE
                    icOvalWorkProve.visibility = View.INVISIBLE
                    icOvalAddress.visibility = View.INVISIBLE
                    icOvalINN.visibility = View.INVISIBLE
                    icOvalSelectBranch.visibility = View.VISIBLE
                    icOvalLimit.visibility = View.INVISIBLE
                    icOvalSendReq.visibility = View.INVISIBLE

                    ivTermsChecked.visibility = View.VISIBLE
                    ivPhotoPassportChecked.visibility = View.VISIBLE
                    ivPhotoWithPassportChecked.visibility = View.VISIBLE
                    ivWorkProveChecked.visibility = View.VISIBLE
                    ivAddressChecked.visibility = View.VISIBLE
                    ivINNChecked.visibility = View.VISIBLE
                    ivSelectBranchChecked.visibility = View.INVISIBLE
                    ivLimitChecked.visibility = View.INVISIBLE
                    ivSendRequestChecked.visibility = View.INVISIBLE

                    val constraintSet = ConstraintSet()
                    constraintSet.clone(contentParent)
                    constraintSet.connect(
                        R.id.lineProgress,
                        ConstraintSet.BOTTOM,
                        R.id.tvNum7,
                        ConstraintSet.TOP,
                        0
                    )

                    constraintSet.applyTo(contentParent)
                }
                8 -> {
                    scrollView.post {
                        if (limitContent.top + SizeUtils.getScreenHeight(this) < llParent.height - SizeUtils.dpToPx(
                                this,
                                100
                            )
                        ) scrollView.scrollTo(0, limitContent.top)
                    }
                    userTermsContent.visibility = View.GONE
                    photoPassportContent.visibility = View.GONE
                    photoWithPassportContent.visibility = View.GONE
                    workProveContent.visibility = View.GONE
                    addressContent.visibility = View.GONE
                    iNNContent.visibility = View.GONE
                    selectBranchContent.visibility = View.GONE
                    limitContent.visibility = View.VISIBLE
                    sendReqContent.visibility = View.GONE

                    icOvalUserTerms.visibility = View.INVISIBLE
                    icOvalPhotoPassport.visibility = View.INVISIBLE
                    icOvalPhotoWithPassport.visibility = View.INVISIBLE
                    icOvalWorkProve.visibility = View.INVISIBLE
                    icOvalAddress.visibility = View.INVISIBLE
                    icOvalINN.visibility = View.INVISIBLE
                    icOvalSelectBranch.visibility = View.INVISIBLE
                    icOvalLimit.visibility = View.VISIBLE
                    icOvalSendReq.visibility = View.INVISIBLE

                    ivTermsChecked.visibility = View.VISIBLE
                    ivPhotoPassportChecked.visibility = View.VISIBLE
                    ivPhotoWithPassportChecked.visibility = View.VISIBLE
                    ivWorkProveChecked.visibility = View.VISIBLE
                    ivAddressChecked.visibility = View.VISIBLE
                    ivINNChecked.visibility = View.VISIBLE
                    ivSelectBranchChecked.visibility = View.VISIBLE
                    ivLimitChecked.visibility = View.INVISIBLE
                    ivSendRequestChecked.visibility = View.INVISIBLE

                    val constraintSet = ConstraintSet()
                    constraintSet.clone(contentParent)
                    constraintSet.connect(
                        R.id.lineProgress,
                        ConstraintSet.BOTTOM,
                        R.id.tvNum8,
                        ConstraintSet.TOP,
                        0
                    )

                    constraintSet.applyTo(contentParent)
                }
                9 -> {
                    scrollView.post {
                        if (sendReqContent.top + SizeUtils.getScreenHeight(this) < llParent.height - SizeUtils.dpToPx(
                                this,
                                100
                            )
                        )  scrollView.scrollTo(0, sendReqContent.top)
                    }
                    userTermsContent.visibility = View.GONE
                    photoPassportContent.visibility = View.GONE
                    photoWithPassportContent.visibility = View.GONE
                    workProveContent.visibility = View.GONE
                    addressContent.visibility = View.GONE
                    iNNContent.visibility = View.GONE
                    selectBranchContent.visibility = View.GONE
                    limitContent.visibility = View.GONE
                    sendReqContent.visibility = View.VISIBLE

                    icOvalUserTerms.visibility = View.INVISIBLE
                    icOvalPhotoPassport.visibility = View.INVISIBLE
                    icOvalPhotoWithPassport.visibility = View.INVISIBLE
                    icOvalWorkProve.visibility = View.INVISIBLE
                    icOvalAddress.visibility = View.INVISIBLE
                    icOvalINN.visibility = View.INVISIBLE
                    icOvalSelectBranch.visibility = View.INVISIBLE
                    icOvalLimit.visibility = View.INVISIBLE
                    icOvalSendReq.visibility = View.VISIBLE

                    ivTermsChecked.visibility = View.VISIBLE
                    ivPhotoPassportChecked.visibility = View.VISIBLE
                    ivPhotoWithPassportChecked.visibility = View.VISIBLE
                    ivWorkProveChecked.visibility = View.VISIBLE
                    ivAddressChecked.visibility = View.VISIBLE
                    ivINNChecked.visibility = View.VISIBLE
                    ivSelectBranchChecked.visibility = View.VISIBLE
                    ivLimitChecked.visibility = View.VISIBLE
                    ivSendRequestChecked.visibility = View.INVISIBLE

                    val constraintSet = ConstraintSet()
                    constraintSet.clone(contentParent)
                    constraintSet.connect(
                        R.id.lineProgress,
                        ConstraintSet.BOTTOM,
                        R.id.tvNum9,
                        ConstraintSet.TOP,
                        0
                    )

                    constraintSet.applyTo(contentParent)

                    subTitleSendReq.text = getString(R.string.send_req_pre_end_text, cardType)

                }
                10 -> {

                    scrollView.post {
                        if (sendReqContent.top + SizeUtils.getScreenHeight(this) < llParent.height - SizeUtils.dpToPx(
                                this,
                                100
                            )
                        )  scrollView.scrollTo(0, sendReqContent.top)
                    }

                    userTermsContent.visibility = View.GONE
                    photoPassportContent.visibility = View.GONE
                    photoWithPassportContent.visibility = View.GONE
                    workProveContent.visibility = View.GONE
                    addressContent.visibility = View.GONE
                    iNNContent.visibility = View.GONE
                    selectBranchContent.visibility = View.GONE
                    limitContent.visibility = View.GONE
                    sendReqContent.visibility = View.VISIBLE

                    icOvalUserTerms.visibility = View.INVISIBLE
                    icOvalPhotoPassport.visibility = View.INVISIBLE
                    icOvalPhotoWithPassport.visibility = View.INVISIBLE
                    icOvalWorkProve.visibility = View.INVISIBLE
                    icOvalAddress.visibility = View.INVISIBLE
                    icOvalINN.visibility = View.INVISIBLE
                    icOvalSelectBranch.visibility = View.INVISIBLE
                    icOvalLimit.visibility = View.INVISIBLE
                    icOvalSendReq.visibility = View.INVISIBLE

                    ivTermsChecked.visibility = View.VISIBLE
                    ivPhotoPassportChecked.visibility = View.VISIBLE
                    ivPhotoWithPassportChecked.visibility = View.VISIBLE
                    ivWorkProveChecked.visibility = View.VISIBLE
                    ivAddressChecked.visibility = View.VISIBLE
                    ivINNChecked.visibility = View.VISIBLE
                    ivSelectBranchChecked.visibility = View.VISIBLE
                    ivLimitChecked.visibility = View.VISIBLE
                    ivSendRequestChecked.visibility = View.VISIBLE

                    val constraintSet = ConstraintSet()
                    constraintSet.clone(contentParent)
                    constraintSet.connect(
                        R.id.lineProgress,
                        ConstraintSet.BOTTOM,
                        R.id.tvNum9,
                        ConstraintSet.TOP,
                        0
                    )

                    constraintSet.applyTo(contentParent)

                    subTitleSendReq.text = getString(R.string.send_req_end_text)
                    btnClose.visibility = View.VISIBLE
                    btnNextSendReq.visibility = View.INVISIBLE
                    btnBackSendReq.visibility = View.INVISIBLE
                }

            }

        })

    }

    private fun setupViews() {
        llParent.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        userTermsContent.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        photoPassportContent.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        photoWithPassportContent.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        workProveContent.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        addressContent.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        iNNContent.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        limitContent.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        selectBranchContent.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        sendReqContent.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)

        btnNextTerms.setOnClickListener {
            viewModel.nextStep()
        }

        btnClose.setOnClickListener {
            setResult(RESULT_OK)
            finish()
        }


        btnNextPhotoPassport.setOnClickListener {
            viewModel.nextStep()
        }


        btnNextPhotoWithPassport.setOnClickListener {
            viewModel.nextStep()
        }


        btnNextWorkProve.setOnClickListener {
            viewModel.nextStep()
        }


        btnNextAddress.setOnClickListener {
            viewModel.nextStep()
        }


        btnNextINN.setOnClickListener {
            viewModel.nextStep()
        }


        btnNextSelectBranch.setOnClickListener {
            viewModel.nextStep()
        }


        btnNextLimit.setOnClickListener {
            viewModel.nextStep()
        }

        btnNextSendReq.setOnClickListener {
            viewModel.nextStep()
        }

        btnBackLimit.setOnClickListener {
            viewModel.previousStep()
        }
        btnBackAddress.setOnClickListener {
            viewModel.previousStep()
        }
        btnBackINN.setOnClickListener {
            viewModel.previousStep()
        }
        btnBackPhotoPassport.setOnClickListener {
            viewModel.previousStep()
        }
        btnBackPhotoWithPassport.setOnClickListener {
            viewModel.previousStep()
        }
        btnBackSelectBranch.setOnClickListener {
            viewModel.previousStep()
        }
        btnBackWorkProve.setOnClickListener {
            viewModel.previousStep()
        }
        btnBackSendReq.setOnClickListener {
            viewModel.previousStep()
        }


    }


}
