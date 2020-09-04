//package com.example.benefit.ui.auth
//
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.FragmentFactory
//import androidx.lifecycle.ViewModelProvider
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import javax.inject.Inject
//
//@ExperimentalCoroutinesApi
//class LoginFragmentFactory @Inject constructor() :
//    FragmentFactory() {
//
//    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
//        return when (className) {
//
//            PhoneFragment::class.java.name -> {
//                val fragment = PhoneFragment()
//                fragment
//            }
//
//            else -> super.instantiate(classLoader, className)
//        }
//    }
//}