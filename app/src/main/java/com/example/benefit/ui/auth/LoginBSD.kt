package com.example.benefit.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.benefit.R
import com.example.benefit.util.MyBSDialog
import com.example.benefit.util.MyBSDialogAuth
import com.example.benefit.util.SizeUtils
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginBSD : MyBSDialog() {

    private val viewModel: LoginViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bsd_login, null)
    }

//    var host: Fragment? = null
//    lateinit var navHost: Fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


//        host = childFragmentManager.findFragmentById(R.id.nav_host_fragment_login)
//        host?.let { /*do nothing*/ } ?: createNavHost()
    }

//    private fun createNavHost() {
//        navHost = AuthNavHostFragment.create(R.navigation.login_nav_graph)
//        childFragmentManager.beginTransaction().replace(R.id.nav_host_fragment_login,
//            navHost,
//            getString(R.string.LoginNavHost))
//            .setPrimaryNavigationFragment(navHost)
//            .commit()
//    }

}