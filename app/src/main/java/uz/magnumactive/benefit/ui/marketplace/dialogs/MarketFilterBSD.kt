package uz.magnumactive.benefit.ui.marketplace.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.bsd_market_filter.*
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.ui.main.BenefitBSD

class MarketFilterBSD : BenefitBSD() {

    var selected = FilterEnum.SALE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        selected = arguments?.getSerializable(ARG_SELECTED_OPTION) as FilterEnum
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bsd_market_filter, null)

        filterRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rbSale -> {

                }
                R.id.rbPopular -> {

                }
                R.id.rbPriceAscending -> {

                }
                R.id.rbPriceDescending -> {

                }
            }
        }

        return view
    }


    companion object {
        const val ARG_SELECTED_OPTION = "SELECTED_OPTION"
    }

    enum class FilterEnum {
        SALE,
        POPULAR,
        ASCENDING,
        DESCENDING
    }

}