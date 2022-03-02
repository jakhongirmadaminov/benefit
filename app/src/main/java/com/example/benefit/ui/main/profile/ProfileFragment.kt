package com.example.benefit.ui.main.profile

import android.animation.LayoutTransition.CHANGING
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.benefit.R
import com.example.benefit.remote.models.CurrencyDTO
import com.example.benefit.remote.models.NewsDTO
import com.example.benefit.ui.auth.AuthActivity
import com.example.benefit.ui.base.BaseFragment
import com.example.benefit.ui.main.profile.settings_bsd.SettingsBSD
import com.example.benefit.ui.viewgroups.ItemLoading
import com.example.benefit.ui.viewgroups.ItemNewsAndPromos
import com.example.benefit.util.AppPrefs
import com.example.benefit.util.RequestState
import com.example.benefit.util.loadDrawableCircleCrop
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.item_currency.view.*


class ProfileFragment : BaseFragment(R.layout.fragment_profile) {

    val viewModel: ProfileViewModel by viewModels()

    val newsAdapter: GroupAdapter<GroupieViewHolder> = GroupAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        attachListeners()
        subscribeObservers()
        rvNews.adapter = newsAdapter
        scrollParent.layoutTransition.enableTransitionType(CHANGING)
    }

    private fun subscribeObservers() {

        viewModel.currencyResp.observe(viewLifecycleOwner) {
            val resp = it ?: return@observe
            when (resp) {
                is RequestState.Error -> {
                    Toast.makeText(context, getString(R.string.error), Toast.LENGTH_SHORT).show()
                }
                RequestState.Loading -> {
                    currencyGrid.removeAllViews()
                    currencyGrid.addView(layoutInflater.inflate(ItemLoading().layout, null))
                }
                is RequestState.Success -> {
                    populateCurrencies(resp.value)
                }
            }
        }

        viewModel.newsResp.observe(viewLifecycleOwner) {
            val resp = it ?: return@observe
            when (resp) {
                is RequestState.Error -> {
                }
                RequestState.Loading -> {
                    newsAdapter.clear()
                    newsAdapter.add(ItemLoading())
                }
                is RequestState.Success -> {
                    populateNews(resp.value)
                }
            }
        }

    }

    private fun populateCurrencies(value: List<CurrencyDTO>) {
        currencyGrid.removeAllViews()
        value.filter { listOf("USD", "JPY", "EUR", "GBP").contains(it.code) }.forEach {
            val view = layoutInflater.inflate(R.layout.item_currency, currencyGrid, false)
//            view.tvCurrency.text = it.code
            view.tvRate.text = "${it.nbu_buy_price} UZS"
            view.tvRate2.text = "${it.nbu_cell_price} UZS"
            (view.icFLag as ImageView).loadDrawableCircleCrop(getFlagIconFor(it))
            currencyGrid.addView(view)
        }
        currencyGrid.requestLayout()

    }

    private fun getFlagIconFor(it: CurrencyDTO): Int {
        return when (it.code) {
            "USD" -> R.drawable.ic_round_usa
            "JPY" -> R.drawable.ic_flag_japan
            "GBP" -> R.drawable.ic_flag_gb
            else -> R.drawable.ic_round_eu
        }
    }

    private fun populateNews(value: List<NewsDTO>) {
        newsAdapter.clear()
        value.forEach {
            newsAdapter.add(ItemNewsAndPromos(it) { link ->
            })
        }
    }

    private fun attachListeners() {
        cardPhotoIcon.setOnClickListener {
            val bsd = SettingsBSD()
            bsd.show(childFragmentManager, "")
        }

        ivLogOut.setOnClickListener {
            AppPrefs.logOut()
            startActivity(Intent(requireActivity(), AuthActivity::class.java))
            requireActivity().finish()
        }

        cardInviteFriend.setOnClickListener {
            InviteFriendsBSD().show(childFragmentManager, "")
        }

    }

    private fun setupViews() {
        tvNameLastName.text = AppPrefs.firstName + " " + AppPrefs.lastName
        cardPhoto.setBackgroundResource(R.drawable.shape_round_window_bg_color)
        cardPhotoIcon.setBackgroundResource(R.drawable.shape_round_window_bg_color)

    }

}