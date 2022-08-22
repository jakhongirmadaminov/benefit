package uz.magnumactive.benefit.ui.marketplace.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.bsd_market_filter.*
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.ui.main.BenefitBSD

class MarketFilterBSD(
    val filterSelection: FilterEnum,
    val onSelectedListener: IOnMarketFilterItemSelected
) : BenefitBSD() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bsd_market_filter, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        when (filterSelection) {
            FilterEnum.LATEST -> {
                filterRadioGroup.check(R.id.rbLatest)
            }
            FilterEnum.SALE -> {
                filterRadioGroup.check(R.id.rbSale)
            }
            FilterEnum.POPULAR -> {
                filterRadioGroup.check(R.id.rbPopular)
            }
            FilterEnum.ASCENDING -> {
                filterRadioGroup.check(R.id.rbPriceAscending)
            }
            FilterEnum.DESCENDING -> {
                filterRadioGroup.check(R.id.rbPriceDescending)
            }
        }

        filterRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rbLatest -> {
                    onSelectedListener.onItemSelected(FilterEnum.LATEST)
                }
                R.id.rbSale -> {
                    onSelectedListener.onItemSelected(FilterEnum.SALE)
                }
                R.id.rbPopular -> {
                    onSelectedListener.onItemSelected(FilterEnum.POPULAR)
                }
                R.id.rbPriceAscending -> {
                    onSelectedListener.onItemSelected(FilterEnum.ASCENDING)
                }
                R.id.rbPriceDescending -> {
                    onSelectedListener.onItemSelected(FilterEnum.DESCENDING)
                }
            }
            dismiss()
        }
    }

    companion object {
        const val ARG_SELECTED_OPTION = "SELECTED_OPTION"
    }

    enum class FilterEnum(val type: Int) {
        LATEST(0),
        SALE(1),
        POPULAR(2),
        ASCENDING(3),
        DESCENDING(4)
    }

    interface IOnMarketFilterItemSelected {
        fun onItemSelected(item: FilterEnum)
    }

}