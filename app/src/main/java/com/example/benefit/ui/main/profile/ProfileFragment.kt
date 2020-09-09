package com.example.benefit.ui.main.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.benefit.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_reg_profile_setup.*
import kotlinx.android.synthetic.main.item_news_and_promos.*

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var profileViewModel: ProfileViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        attachListeners()
    }

    private fun attachListeners() {


        cardPhotoIcon.setOnClickListener {

        }
    }

    private fun setupViews() {

        cardPhoto.setBackgroundResource(R.drawable.shape_round_window_bg_color)
        cardPhotoIcon.setBackgroundResource(R.drawable.shape_round_window_bg_color)
        cardNewsAndPromos.setBackgroundResource(R.drawable.gradient_fuchsia)

    }

}