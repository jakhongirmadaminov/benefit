package uz.magnumactive.benefit.ui.marketplace.orders

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.fragment_orders.*
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.ui.base.BaseFragment


class OrdersFragment : BaseFragment(R.layout.fragment_orders) {

    //    private val adapter = GroupAdapter<GroupieViewHolder>()
//    val viewModel: PaymentsAndTransfersViewModel by viewModels()
    private lateinit var ordersPagerAdapter: OrdersPagerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        viewModel.getMyCards()
//        viewModel.getMyAutoPayments()

        setupViews()
        attachListeners()
        subscribeObservers()
    }

    private fun subscribeObservers() {

    }

    private fun setupViews() {
        ordersPagerAdapter = OrdersPagerAdapter(this)
        pager.adapter = ordersPagerAdapter

    }


    private fun attachListeners() {

        rbActive.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) pager.currentItem = 0

        }

        rbHistory.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) pager.currentItem = 1
        }

        pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if (position == 0) {
                    rbActive.background =
                        ContextCompat.getDrawable(requireContext(), R.drawable.selector_orange)
                    rbActive.setTextColor(Color.WHITE)
                    rbHistory.background =
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.selector_grey_rounded
                        )
                    rbHistory.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.grey_text
                        )
                    )
                } else {
                    rbActive.background =
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.selector_grey_rounded
                        )
                    rbActive.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.grey_text
                        )
                    )
                    rbHistory.background =
                        ContextCompat.getDrawable(requireContext(), R.drawable.selector_orange)
                    rbHistory.setTextColor(Color.WHITE)
                }
            }
        })

    }
}