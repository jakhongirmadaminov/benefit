package uz.magnumactive.benefit.ui.regular_payment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_create_regular_payment_how_it_works.*
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.ui.base.BaseFragment
import uz.magnumactive.benefit.util.SizeUtils
import javax.inject.Inject


/**
 * Created by jahon on 03-Sep-20
 */

class CreateRegularPaymentHowItWorksFragment @Inject constructor() :
    BaseFragment(R.layout.fragment_create_regular_payment_how_it_works) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupViews()

        attachListeners()
        subscribeObservers()
    }


    private fun attachListeners() {

        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupViews() {

        clParent.layoutParams = clParent.layoutParams.apply {
            height = SizeUtils.getScreenHeight(requireActivity()) - SizeUtils.getActionBarHeight(
                requireActivity()
            )

        }

    }


    private fun subscribeObservers() {


    }


}
