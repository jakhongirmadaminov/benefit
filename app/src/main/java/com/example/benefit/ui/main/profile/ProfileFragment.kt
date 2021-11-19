package com.example.benefit.ui.main.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
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
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.item_news_and_promos.*
import splitties.preferences.edit


class ProfileFragment : BaseFragment(R.layout.fragment_profile) {

    val viewModel: ProfileViewModel by viewModels()

    val newsAdapter: GroupAdapter<GroupieViewHolder> = GroupAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        attachListeners()
        subscribeObservers()
        rvNews.adapter = newsAdapter
    }

    private fun subscribeObservers() {

        viewModel.currencyResp.observe(viewLifecycleOwner) {
            val resp = it ?: return@observe
            when (resp) {
                is RequestState.Error -> {
                }
                RequestState.Loading -> {
                    currencyGrid.removeAllViews()
                    currencyGrid.addView(layoutInflater.inflate(ItemLoading().layout, currencyGrid))
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
        value.forEach {
            currencyGrid.addView(layoutInflater.inflate(R.layout.item_currency, currencyGrid))
        }

    }

    private fun populateNews(value: List<NewsDTO>) {
        newsAdapter.clear()
        value.forEach {
            newsAdapter.add(ItemNewsAndPromos(it) { link ->
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(link)))
            })
        }
    }

    private fun attachListeners() {
        cardPhotoIcon.setOnClickListener {
            val bsd = SettingsBSD()
            bsd.show(childFragmentManager, "")
        }

        ivLogOut.setOnClickListener {
            AppPrefs.edit {
                token = null
                userToken = null
            }
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