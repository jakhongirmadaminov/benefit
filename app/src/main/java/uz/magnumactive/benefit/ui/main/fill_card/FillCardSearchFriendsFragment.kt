package uz.magnumactive.benefit.ui.main.fill_card

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.ui.main.home.card_options.CardOptionsViewModel
import kotlinx.android.synthetic.main.fragment_fill_card_search_friends.*

/**
 * Created by jahon on 03-Sep-20
 */
import uz.magnumactive.benefit.ui.base.BaseFragment

class FillCardSearchFriendsFragment : BaseFragment(R.layout.fragment_fill_card_search_friends) {



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        attachListeners()
        subscribeObservers()
    }

    private fun subscribeObservers() {


    }

    private fun attachListeners() {
        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

    }

}