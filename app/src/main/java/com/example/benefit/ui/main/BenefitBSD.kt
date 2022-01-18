package com.example.benefit.ui.main

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import com.example.benefit.R
import com.example.benefit.util.SizeUtils
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

open class BenefitBSD : BottomSheetDialogFragment() {

    var peekHeight: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.setOnShowListener {
            val d = dialog as BottomSheetDialog
            val bottomSheet = d.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout

            bottomSheet.setBackgroundResource(R.drawable.shape_top_rounded)
            peekHeight =
                SizeUtils.getScreenHeight(requireActivity()) - SizeUtils.getActionBarHeight(
                    requireActivity()
                )
            d.behavior.peekHeight = peekHeight
        }
    }

}