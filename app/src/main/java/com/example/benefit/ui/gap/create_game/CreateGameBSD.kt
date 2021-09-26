package com.example.benefit.ui.gap.create_game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.benefit.R
import com.example.benefit.util.MyBSDialog
import com.example.benefit.util.MyNestedScrollBSDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.bsd_fill_card.*



class CreateGameBSD : MyNestedScrollBSDialog() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bsd_create_game, null)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}