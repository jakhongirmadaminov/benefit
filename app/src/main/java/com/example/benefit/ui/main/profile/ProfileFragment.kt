package com.example.benefit.ui.main.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.benefit.R
import com.example.benefit.ui.auth.AuthActivity
import com.example.benefit.ui.main.profile.settings_bsd.SettingsBSD
import com.example.benefit.util.AppPrefs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.item_news_and_promos.*
import splitties.fragments.start
import splitties.preferences.edit


class ProfileFragment : Fragment(R.layout.fragment_profile) {

    val viewModel: ProfileViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        attachListeners()
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

        cardPhoto.setBackgroundResource(R.drawable.shape_round_window_bg_color)
        cardPhotoIcon.setBackgroundResource(R.drawable.shape_round_window_bg_color)
        cardNewsAndPromos.setBackgroundResource(R.drawable.gradient_fuchsia)

    }

}