package com.example.benefit.util

import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.opengl.ETC1.getHeight
import android.util.DisplayMetrics
import com.example.benefit.MainActivity
import com.example.benefit.R


open class MyBSDialogAuth : BottomSheetDialogFragment() {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        dialog?.setOnShowListener {
            val d = dialog as BottomSheetDialog
            val bottomSheet = d.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout
            bottomSheet.setBackgroundResource(android.R.color.transparent)
            val coordinatorLayout = bottomSheet.parent as CoordinatorLayout
            val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            bottomSheetBehavior.peekHeight = DisplayMetrics().heightPixels
            bottomSheetBehavior.peekHeight = SizeUtils.dpToPx(
                requireContext(),
                SizeUtils.getScreenHeight(requireActivity()) - SizeUtils.getActionBarHeight(
                    requireActivity()
                )
            ).toInt()
            bottomSheetBehavior.isFitToContents = true
//            bottomSheetBehavior.peekHeight = 0
            coordinatorLayout.parent.requestLayout()
        }
    }

}
