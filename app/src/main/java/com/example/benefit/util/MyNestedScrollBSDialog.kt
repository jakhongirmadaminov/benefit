//package com.example.benefit.util
//
//import android.animation.LayoutTransition.CHANGING
//import android.os.Bundle
//import android.view.View
//import android.view.WindowManager
//import android.widget.FrameLayout
//import androidx.coordinatorlayout.widget.CoordinatorLayout
//import com.example.benefit.R
//import com.google.android.material.bottomsheet.BottomSheetBehavior
//import com.google.android.material.bottomsheet.BottomSheetDialog
//import com.google.android.material.bottomsheet.BottomSheetDialogFragment
//
//open class MyNestedScrollBSDialog : BottomSheetDialogFragment() {
//
//
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        dialog?.setOnShowListener {
//            val d = dialog as BottomSheetDialog
//            val bottomSheet = d.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout
//            bottomSheet.setBackgroundResource(android.R.color.transparent)
//            val coordinatorLayout =
//                d.findViewById<View>(R.id.locUXCoordinatorLayout) as CoordinatorLayout?
//            coordinatorLayout?.layoutTransition?.enableTransitionType(CHANGING)
//            val bottomSheetInternal = d.findViewById<View>(R.id.locUXView)
//            val bottomSheetBehavior = BottomSheetBehavior.from<View>(bottomSheetInternal!!)
//            bottomSheetBehavior.isHideable = false
//
//            val vHeight =
//                SizeUtils.getScreenHeight(requireActivity()) - SizeUtils.getActionBarHeight(
//                    requireActivity()
//                )
//
//
//            BottomSheetBehavior.from(coordinatorLayout!!.parent.parent as View).peekHeight =
//                vHeight
//            bottomSheetBehavior.peekHeight = vHeight
//            coordinatorLayout.layoutParams = coordinatorLayout.layoutParams.apply {
//                height = vHeight
//            }
//            coordinatorLayout.parent.requestLayout()
//        }
//    }
//
//}
