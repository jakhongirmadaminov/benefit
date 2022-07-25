package uz.magnumactive.benefit.ui.main.partners_category

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_partners_category.*
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.remote.models.PartnerCategoryDTO
import uz.magnumactive.benefit.ui.base.BaseFragment
import uz.magnumactive.benefit.ui.marketplace.MarketActivity
import uz.magnumactive.benefit.ui.partners_map.PartnersMapActivity
import uz.magnumactive.benefit.ui.viewgroups.BenefitMarketItem
import uz.magnumactive.benefit.ui.viewgroups.ItemPartnerCategory
import uz.magnumactive.benefit.util.ResultError
import uz.magnumactive.benefit.util.ResultSuccess
import uz.magnumactive.benefit.util.exhaustive


class PartnersCategoryFragment : BaseFragment(R.layout.fragment_partners_category) {

    val viewModel: PartnersCategoryViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getPartners()
        viewModel.getMarketCategories()

        setupViews()
        attachListeners()
        subscribeObservers()
    }

    private fun attachListeners() {
        ivMap.setOnClickListener {
            startActivity(Intent(requireActivity(), PartnersMapActivity::class.java).apply {
                putParcelableArrayListExtra(
                    PartnersMapActivity.EXTRA_CATEGORIES,
                    ArrayList((viewModel.partnersResp.value!! as ResultSuccess).value)
                )
            })
        }

        llMarketPlace.setOnClickListener {
            startActivity(Intent(requireActivity(), MarketActivity::class.java).apply { })
        }
    }

    private fun setupViews() {
        rvPartners.adapter = adapter
        rvMarket.adapter = marketPlaceAdapter
    }

    private fun subscribeObservers() {
        viewModel.partnersResp.observe(viewLifecycleOwner, Observer {
            val response = it ?: return@Observer
            when (response) {
                is ResultError -> {
                    ivMap.visibility = View.INVISIBLE
                }
                is ResultSuccess -> {
                    ivMap.visibility = View.VISIBLE
                    loadData(response.value)
                }
//                InProgress -> {
//                    ivMap.visibility = View.INVISIBLE
//                    adapter.clear()
//                    adapter.add(ItemProgress())
//                    adapter.notifyDataSetChanged()
//                }
            }.exhaustive
        })

        viewModel.marketplaceCategories.observe(viewLifecycleOwner) { categories ->
            categories ?: return@observe
            marketPlaceAdapter.apply {
                clear()
                categories.forEach {
                    add(BenefitMarketItem(it))
                }
                notifyDataSetChanged()
            }
        }

    }

    val adapter = GroupAdapter<GroupieViewHolder>()
    private val marketPlaceAdapter = GroupAdapter<GroupieViewHolder>()

    private fun loadData(response: List<PartnerCategoryDTO>) {
        adapter.clear()

        response.forEach { partner ->
            adapter.add(ItemPartnerCategory(partner))
        }

    }
}