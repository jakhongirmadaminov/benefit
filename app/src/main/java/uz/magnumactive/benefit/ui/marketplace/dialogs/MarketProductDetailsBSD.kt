package uz.magnumactive.benefit.ui.marketplace.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.bsd_market_product_details.*
import kotlinx.android.synthetic.main.item_product_image.view.*
import kotlinx.android.synthetic.main.item_product_param.view.*
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.remote.models.MarketProductDTO
import uz.magnumactive.benefit.remote.models.MarketProductDetailsDTO
import uz.magnumactive.benefit.ui.main.BenefitFixedHeightBSD
import uz.magnumactive.benefit.ui.main.home.HomeFragment
import uz.magnumactive.benefit.util.RequestState
import uz.magnumactive.benefit.util.SizeUtils.dpToPx
import uz.magnumactive.benefit.util.loadImageUrl
import java.text.DecimalFormat

@AndroidEntryPoint
class MarketProductDetailsBSD(val obj: MarketProductDTO) : BenefitFixedHeightBSD() {

    private val viewModel: MarketProductDetailsViewModel by viewModels()
    var details: MarketProductDetailsDTO? = null

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
        attachListeners()
        subscribeObservers()
        setupView()
    }

    private fun attachListeners() {

        cbFavourite.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                viewModel.addToFavourites(details!!.product!!.id!!)
            } else {
                viewModel.removeFromFavorites(details!!.product!!.id!!)
            }
        }

        btnAddToCart.setOnClickListener {
            details?.product?.let { viewModel.addToCart(it.id!!, viewModel.count.value!!) }
        }

        ivPlus.setOnClickListener {
            viewModel.count.value = viewModel.count.value!! + 1
        }

        ivMinus.setOnClickListener {
            if (viewModel.count.value!! > 1) {
                viewModel.count.value = viewModel.count.value!! - 1
                details?.let {
                    tvTotalSum.text =
                        DecimalFormat("#,###").format(it.product?.realSumma!! * viewModel.count.value!!) + " UZS"
                }
            }

        }

    }

    private fun setupView() {
        imagePager.offscreenPageLimit = 10
        imagePager.adapter = null
    }

    private fun subscribeObservers() {

        viewModel.addToCartResp.observe(viewLifecycleOwner) {
            val resp = it ?: return@observe
            when (resp) {
                is RequestState.Error -> {
                    Toast.makeText(requireContext(), R.string.error, LENGTH_SHORT).show()
                }
                RequestState.Loading -> {}
                is RequestState.Success -> {
                    Toast.makeText(requireContext(), R.string.added_to_cart, LENGTH_SHORT).show()
                }
            }
        }

        viewModel.addToFavResult.observe(viewLifecycleOwner) {
            val resp = it ?: return@observe

            when (resp) {
                is RequestState.Error -> {}
                RequestState.Loading -> {}
                is RequestState.Success -> {}
            }
        }

        viewModel.count.observe(viewLifecycleOwner) { count ->
            tvCount.text = count.toString()
            details?.product?.realSumma?.let { realSumm ->
                tvTotalSum.text =
                    DecimalFormat("#,###").format(realSumm * count) + " UZS"
            }

        }

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
                    details = resp.value
                    loadDetails()
                }
            }
        }
    }

    private fun loadDetails() {
        cbFavourite.isChecked = details?.isInFavourites ?: false

        details?.product?.apply {
            val imageViews = arrayListOf<View>()

            photos?.forEach {
                val imageLayout = layoutInflater.inflate(R.layout.item_product_image, null, false)
                imageLayout.layoutParams =
                    ViewGroup.LayoutParams(MATCH_PARENT, dpToPx(requireContext(), 220).toInt())
                imageLayout.productImage.loadImageUrl(it.photos ?: "")
                imagePager.addView(imageLayout)
                imageViews.add(imageLayout)
            }
            pageIndicatorView.setViewPager(imagePager)
            tvProductTitle.text = title?.getLocalized()
            tvTotalSum.text =
                DecimalFormat("#,###").format(realSumma!! * viewModel.count.value!!) + " UZS"
            imagePager.adapter = HomeFragment.WizardPagerAdapter(imageViews)
            params?.forEach {
                val imageLayout = layoutInflater.inflate(R.layout.item_product_param, null, false)
                imageLayout.tvSpecLabel.text = it.name?.getLocalized()
                imageLayout.tvSpecValue.text = it.value?.getLocalized()
                specsGrid.addView(imageLayout)
            }
        }

    }
}
