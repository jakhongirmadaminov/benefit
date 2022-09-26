package uz.magnumactive.benefit.ui.marketplace.place_order

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.bsd_order_placed.*
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.ui.main.BenefitBSD

class BSDOrderPlaced : BenefitBSD() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bsd_order_placed, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = false
        tvClose.setOnClickListener {
            requireActivity().setResult(Activity.RESULT_OK)
            requireActivity().finish()
            dismiss()
        }
    }
}
