package uz.magnumactive.benefit.ui.main

import android.view.View
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.util.SizeUtils
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

open class BenefitFixedHeightBSD : BottomSheetDialogFragment() {

    override fun onStart() {
        super.onStart()
        val vHeight =
            SizeUtils.getScreenHeight(requireActivity()) - SizeUtils.getActionBarHeight(
                requireActivity()
            )

        dialog?.also {
            val bottomSheet = it.findViewById<View>(R.id.design_bottom_sheet)
            bottomSheet.layoutParams.height = vHeight
            val behavior = BottomSheetBehavior.from(bottomSheet)
            bottomSheet.setBackgroundResource(R.drawable.shape_top_rounded)
            behavior.peekHeight = vHeight //replace to whatever you want
            view?.requestLayout()
        }
    }
}