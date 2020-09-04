package com.example.benefit.util

import android.app.Activity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.example.benefit.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


open class MyBSDialogAuth : BottomSheetDialogFragment() {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        dialog?.setOnShowListener {

            val d = dialog as BottomSheetDialog
            val bottomSheet = d.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout
//            bottomSheet.setBackgroundResource(android.R.color.transparent)
            val coordinatorLayout = bottomSheet.parent as CoordinatorLayout
            val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
//            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
//            bottomSheetBehavior.peekHeight = DisplayMetrics().heightPixels
//            bottomSheetBehavior.peekHeight = SizeUtils.dpToPx(
//                requireContext(),
//                SizeUtils.getScreenHeight(requireActivity()) - SizeUtils.getActionBarHeight(
//                    requireActivity()
//                )
//            ).toInt()
            setupRatio(d)
//            bottomSheetBehavior.isFitToContents = true
//            bottomSheetBehavior.peekHeight = 0
            coordinatorLayout.parent.requestLayout()
        }
    }

     fun setupRatio(bottomSheetDialog: BottomSheetDialog) {
        //id = com.google.android.material.R.id.design_bottom_sheet for Material Components
        //id = android.support.design.R.id.design_bottom_sheet for support librares
        val bottomSheet =
            bottomSheetDialog.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout
        val behavior= BottomSheetBehavior.from(bottomSheet)
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height =
            SizeUtils.getScreenHeight(requireActivity()) - SizeUtils.getActionBarHeight(
                requireActivity()
            )

        bottomSheet.layoutParams = layoutParams
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }


}
