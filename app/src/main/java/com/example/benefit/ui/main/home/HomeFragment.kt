package com.example.benefit.ui.main.home

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.PagerAdapter
import com.example.benefit.R
import com.example.benefit.ui.main.home.loans.LoanActivity
import com.example.benefit.util.SizeUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import splitties.fragments.start

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var homeViewModel: HomeViewModel


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        attachListeners()

    }

    private fun attachListeners() {
        cardOvalLoans.setOnClickListener { start<LoanActivity> {} }

    }

    private fun setupViews() {
        cardOval.setBackgroundResource(R.drawable.shape_oval)
        cardOvalExpenses.setBackgroundResource(R.drawable.shape_oval)
        cardOvalBranches.setBackgroundResource(R.drawable.shape_oval)
        cardOvalLimits.setBackgroundResource(R.drawable.shape_oval)
        cardOvalMessage.setBackgroundResource(R.drawable.shape_oval_yellow)
        cardOvalLoans.setBackgroundResource(R.drawable.shape_oval_yellow)
        cardOvalCashback.setBackgroundResource(R.drawable.shape_oval_yellow)

        setupServicesPager()

    }

    private fun setupServicesPager() {

        servicesPager.adapter = WizardPagerAdapter()
        servicesPager.offscreenPageLimit = 2
        servicesPager.clipToPadding = false
        servicesPager.setPadding(
            SizeUtils.dpToPx(requireContext(), 26).toInt(),
            0,
            SizeUtils.dpToPx(requireContext(), 26).toInt(),
            0
        )
        servicesPager.pageMargin = SizeUtils.dpToPx(requireContext(), 15).toInt()

        page_one.setOnClickListener {
            val dialog = DialogCashBack()
            dialog.show(childFragmentManager, "")
        }

    }


    inner class WizardPagerAdapter : PagerAdapter() {


        override fun instantiateItem(collection: ViewGroup, position: Int): Any {
            var resId = 0
            when (position) {
                0 -> resId = R.id.page_one
                1 -> resId = R.id.page_two
                2 -> resId = R.id.page_three
            }
            return servicesPager.findViewById(resId)
        }

        override fun getCount(): Int {
            return 3
        }

        override fun isViewFromObject(arg0: View, arg1: Any): Boolean {
            return arg0 === arg1
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            // No super
        }
    }

}