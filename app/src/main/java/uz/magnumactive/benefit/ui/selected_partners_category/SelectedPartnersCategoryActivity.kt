package uz.magnumactive.benefit.ui.selected_partners_category

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_selected_partners_category.*
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.remote.models.Partner
import uz.magnumactive.benefit.remote.models.PartnerCategoryDTO
import uz.magnumactive.benefit.ui.base.BaseActivity
import uz.magnumactive.benefit.ui.partner_home.PartnerHomeActivity
import uz.magnumactive.benefit.ui.partners_map.PartnersMapActivity
import uz.magnumactive.benefit.ui.viewgroups.ItemPartnerCashback
import uz.magnumactive.benefit.util.ResultError
import uz.magnumactive.benefit.util.ResultSuccess
import uz.magnumactive.benefit.util.exhaustive


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
        tool_bar.setBackgroundColor(Color.parseColor(categoryDTO.color))
    }

    private fun subscribeObservers() {
        viewModel.partnersResp.observe(this, Observer {
            val response = it ?: return@Observer

            when (response) {
                is ResultError -> {
                }
                is ResultSuccess -> {
                    partnersCashbackInstallmentPair = response.value.partition { it.type == 0 }
                    loadData(partnersCashbackInstallmentPair.first)
                    icMap.setOnClickListener {
                        startActivity(Intent(this, PartnersMapActivity::class.java).apply {
                            putExtra(PartnersMapActivity.EXTRA_CATEGORY_ID, categoryDTO.id)
                        })
                    }
                }
            }.exhaustive

        })

    }

    var partnersCashbackInstallmentPair: Pair<List<Partner>, List<Partner>> =
        Pair(listOf(), listOf())

    private fun loadData(value: List<Partner>) {
        adapter.clear()
        llInstallment.isVisible = value.isEmpty()

        value.forEach {
            adapter.add(ItemPartnerCashback(it) {
                startActivity(Intent(this, PartnerHomeActivity::class.java).apply {
                    putExtra(PartnerHomeActivity.EXTRA_PARTNER, it)
                })
            })
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
                loadData(partnersCashbackInstallmentPair.second)
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
                loadData(partnersCashbackInstallmentPair.first)
            }
        }


    }
}


