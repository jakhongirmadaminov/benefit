package com.example.benefit.ui.selected_partners_category

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.benefit.R
import com.example.benefit.remote.models.PartnerCategoryDTO
import com.example.benefit.ui.base.BaseActivity
import com.example.benefit.ui.viewgroups.ItemPartnerCashback
import com.example.benefit.util.InProgress
import com.example.benefit.util.ResultError
import com.example.benefit.util.ResultSuccess
import com.example.benefit.util.exhaustive
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_selected_partners_category.*

@AndroidEntryPoint
class SelectedPartnersCategoryActivity : BaseActivity() {

    private val adapter = GroupAdapter<GroupieViewHolder>()
    lateinit var categoryDTO: PartnerCategoryDTO

    private val viewModel: SelectedPartnersCategoryViewModel by viewModels()

    companion object {
        const val EXTRA_CATEGORY = "CATEGORY"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selected_partners_category)


        categoryDTO = intent.getParcelableExtra(EXTRA_CATEGORY)!!
        setupViews()
        attachListeners()
        subscribeObservers()
        viewModel.getPartnersForCategory(categoryDTO.id)
    }

    private fun setupViews() {
        tvTitle.text = categoryDTO.title_ru
        rvPartners.adapter = adapter

    }

    private fun subscribeObservers() {
        viewModel.partnersResp.observe(this, Observer {
            val response = it ?: return@Observer

            when (response) {
                is ResultError -> {
                }
                is ResultSuccess -> {
                    loadData(response.value)
                }
                InProgress -> {

                }
            }.exhaustive

        })

    }

    private fun loadData(value: List<PartnerCategoryDTO>) {

        adapter.clear()
        value.forEach {
            adapter.add(ItemPartnerCashback(it))
        }

        adapter.notifyDataSetChanged()
    }

    private fun attachListeners() {
        ivBack.setOnClickListener {
            onBackPressed()
        }

        rbInstallment.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked) {
                llInstallment.visibility = View.VISIBLE
                rbInstallment.background =
                    ContextCompat.getDrawable(this, R.drawable.selector_orange)
                rbInstallment.setTextColor(Color.WHITE)
                rbCashBack.background =
                    ContextCompat.getDrawable(this, R.drawable.selector_grey_rounded)
                rbCashBack.setTextColor(ContextCompat.getColor(this, R.color.grey_text))
            } else {
                llInstallment.visibility = View.GONE

                rbInstallment.background =
                    ContextCompat.getDrawable(this, R.drawable.selector_grey_rounded)
                rbInstallment.setTextColor(ContextCompat.getColor(this, R.color.grey_text))
                rbCashBack.background =
                    ContextCompat.getDrawable(this, R.drawable.selector_orange)
                rbCashBack.setTextColor(Color.WHITE)
            }

        }

        rbCashBack.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked) {

            }

        }


        icMap.setOnClickListener {
//            start<PartnersMapActivity> {
//                putParcelableArrayListExtra(                    PartnersMapActivity.EXTRA_CATEGORIES,                 )
//                )
        }
    }
}


