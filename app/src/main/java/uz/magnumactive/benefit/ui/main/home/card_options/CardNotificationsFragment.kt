package uz.magnumactive.benefit.ui.main.home.card_options

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import uz.magnumactive.benefit.R
import kotlinx.android.synthetic.main.fragment_card_change_design.*

/**
 * Created by jahon on 03-Sep-20
 */
import uz.magnumactive.benefit.ui.base.BaseFragment

class CardNotificationsFragment : BaseFragment(R.layout.fragment_card_notifications) {


    private val viewModel: CardOptionsViewModel by activityViewModels()

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