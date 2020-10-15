package com.example.benefit.ui.main.home

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager.widget.PagerAdapter
import com.example.benefit.R
import com.example.benefit.ui.branches_atms.BranchesAtmsActivity
import com.example.benefit.ui.expenses_by_categories.ExpensesByCategoriesActivity
import com.example.benefit.ui.loans.LoanActivity
import com.example.benefit.ui.main.home.card_options.CardOptionsBSD
import com.example.benefit.ui.main.fill_card.FillCardBSD
import com.example.benefit.ui.transactions_history.TransactionsHistoryActivity
import com.example.benefit.util.SizeUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.item_card.view.*
import splitties.fragments.start

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    val viewModel: HomeViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        attachListeners()

    }

    private fun attachListeners() {

        page_one.setOnClickListener {
            val dialog = DialogCashBack()
            dialog.show(childFragmentManager, "")

        }

        cardPayments.setOnClickListener {
            start<TransactionsHistoryActivity> {}
        }

        page_two.setOnClickListener {
            start<LoanActivity> {}
        }
        cardBranches.setOnClickListener {
            start<BranchesAtmsActivity> {}
        }
        cardExpenses.setOnClickListener {
            start<ExpensesByCategoriesActivity> {}
        }

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

        setupCardsPager()

    }

    private fun setupCardsPager() {

        val cardView = layoutInflater.inflate(R.layout.item_card, null)

        cardView.icPlus.setOnClickListener {
            FillCardBSD().show(childFragmentManager, "")
        }
        cardView.setOnClickListener {
            CardOptionsBSD().show(childFragmentManager, "")
        }

        cardsPager.addView(cardView)
        val cardView2 = layoutInflater.inflate(R.layout.item_add_card, null)
        cardsPager.addView(cardView2)


        cardsPager.adapter = WizardPagerAdapter(listOf(cardView, cardView2))
        cardsPager.offscreenPageLimit = 2
        cardsPager.clipToPadding = false
        cardsPager.setPadding(
            SizeUtils.dpToPx(requireContext(), 26).toInt(),
            0,
            SizeUtils.dpToPx(requireContext(), 26).toInt(),
            0
        )
        cardsPager.pageMargin = SizeUtils.dpToPx(requireContext(), 15).toInt()


    }

    private fun setupServicesPager() {

        servicesPager.adapter = WizardPagerAdapter(listOf(page_one, page_two, page_three))
        servicesPager.offscreenPageLimit = 2
        servicesPager.clipToPadding = false
        servicesPager.setPadding(
            SizeUtils.dpToPx(requireContext(), 26).toInt(),
            0,
            SizeUtils.dpToPx(requireContext(), 26).toInt(),
            0
        )
        servicesPager.pageMargin = SizeUtils.dpToPx(requireContext(), 15).toInt()


    }


    class WizardPagerAdapter(val views: List<View>) : PagerAdapter() {


        override fun instantiateItem(collection: ViewGroup, position: Int): Any {
            return views[position]
//            return servicesPager.findViewById(resId)
        }

        override fun getCount(): Int {
            return views.size
        }

        override fun isViewFromObject(arg0: View, arg1: Any): Boolean {
            return arg0 === arg1
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            // No super
        }
    }

}