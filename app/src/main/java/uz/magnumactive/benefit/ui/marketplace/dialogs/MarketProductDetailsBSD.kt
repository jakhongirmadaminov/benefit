package uz.magnumactive.benefit.ui.marketplace.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.bsd_market_product_details.*
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.remote.models.MarketProductDTO
import uz.magnumactive.benefit.remote.models.Partner
import uz.magnumactive.benefit.ui.main.BenefitBSD
import uz.magnumactive.benefit.util.RequestState

@AndroidEntryPoint
class MarketProductDetailsBSD(val obj: MarketProductDTO) : BenefitBSD() {

    private val viewModel: MarketProductDetailsViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bsd_market_product_details, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getProductDetails(obj.id!!)
        subscribeObservers()
    }

    private fun subscribeObservers() {

        viewModel.details.observe(viewLifecycleOwner) {
            val resp = it ?: return@observe
            when (resp) {
                is RequestState.Error -> {
                    progress.isVisible = false
                }
                RequestState.Loading -> {
                    progress.isVisible = true
                }
                is RequestState.Success -> {
                    progress.isVisible = false
                    loadDetails(resp.value)
                }
            }
        }
    }

    private fun loadDetails(value: Partner) {
    }
}
