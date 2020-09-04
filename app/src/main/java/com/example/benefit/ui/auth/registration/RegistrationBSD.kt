package com.example.benefit.ui.auth.registration

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.viewModels
import com.example.benefit.R
import com.example.benefit.util.SizeUtils
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RegistrationBSD : BottomSheetDialogFragment() {

    private val viewModel: RegistrationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bsd_registration, null)

        return view
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(requireContext(), theme)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.setOnShowListener {
            val d = dialog as BottomSheetDialog
            val bottomSheet = d.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout
            bottomSheet.setBackgroundResource(android.R.color.transparent)
            val coordinatorLayout =
                d.findViewById<View>(R.id.locUXCoordinatorLayout) as CoordinatorLayout?
            val bottomSheetInternal = d.findViewById<View>(R.id.locUXView)
            val bottomSheetBehavior = BottomSheetBehavior.from<View>(bottomSheetInternal!!)
            bottomSheetBehavior.isHideable = false

            val vHeight =
                SizeUtils.getScreenHeight(requireActivity()) - SizeUtils.getActionBarHeight(
                    requireActivity()
                )


            BottomSheetBehavior.from(coordinatorLayout!!.parent as View).peekHeight =
                vHeight
            bottomSheetBehavior.peekHeight = vHeight
            coordinatorLayout.layoutParams = coordinatorLayout.layoutParams.apply {
                height = vHeight
            }
            coordinatorLayout.parent.requestLayout()
        }
    }

}