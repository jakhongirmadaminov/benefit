package com.example.benefit.ui.main.partners_category

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.benefit.R
import com.example.benefit.remote.models.PartnerDTO
import com.example.benefit.ui.viewgroups.ItemPartner
import com.example.benefit.ui.viewgroups.ItemProgress
import com.example.benefit.util.ErrorWrapper
import com.example.benefit.util.ResultWrapper
import com.example.benefit.util.exhaustive
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_partners_category.*

@AndroidEntryPoint
class PartnersCategoryFragment : Fragment(R.layout.fragment_partners_category) {

    val viewModel: PartnersCategoryViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getPartners()

        setupViews()
        subscribeObservers()
    }

    private fun setupViews() {
        rvPartners.adapter = adapter
    }

    private fun subscribeObservers() {
        viewModel.partnersResp.observe(viewLifecycleOwner, Observer {
            val response = it ?: return@Observer

            when (response) {
                is ErrorWrapper.ResponseError -> {
                }
                is ErrorWrapper.SystemError -> {
                }
                is ResultWrapper.Success -> {
                    loadData(response.value)
                }
                ResultWrapper.InProgress -> {
                    adapter.clear()
                    adapter.add(ItemProgress())
                    adapter.notifyDataSetChanged()
                }
            }.exhaustive


        })
    }

    val adapter = GroupAdapter<GroupieViewHolder>()

    private fun loadData(response: List<PartnerDTO>) {
        adapter.clear()

        response.forEach {partner->
            adapter.add(ItemPartner(partner))
        }

    }
}