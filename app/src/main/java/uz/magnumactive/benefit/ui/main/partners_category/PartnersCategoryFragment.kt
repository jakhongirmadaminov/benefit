package uz.magnumactive.benefit.ui.main.partners_category

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.remote.models.PartnerCategoryDTO
import uz.magnumactive.benefit.ui.base.BaseFragment
import uz.magnumactive.benefit.ui.partners_map.PartnersMapActivity
import uz.magnumactive.benefit.ui.viewgroups.ItemPartnerCategory
import uz.magnumactive.benefit.util.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_partners_category.*


class PartnersCategoryFragment : BaseFragment(R.layout.fragment_partners_category) {

    val viewModel: PartnersCategoryViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getPartners()

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
    }

    private fun setupViews() {
        rvPartners.adapter = adapter
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
    }

    val adapter = GroupAdapter<GroupieViewHolder>()

    private fun loadData(response: List<PartnerCategoryDTO>) {
        adapter.clear()

        response.forEach { partner ->
            adapter.add(ItemPartnerCategory(partner))
        }

    }
}