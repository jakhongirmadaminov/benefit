package uz.magnumactive.benefit.ui.marketplace.orders

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class OrdersPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    val fragmentOne = ActiveOrdersFragment()
    val fragmentTwo = HistoryOrdersFragment()

    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        // Return a NEW fragment instance in createFragment(int)
//        val fragment = DemoObjectFragment()
//        fragment.arguments = Bundle().apply {
        // Our object is just an integer :-P
//            putInt(ARG_OBJECT, position + 1)
//        }
        return if (position == 0) fragmentOne else fragmentTwo
    }


}