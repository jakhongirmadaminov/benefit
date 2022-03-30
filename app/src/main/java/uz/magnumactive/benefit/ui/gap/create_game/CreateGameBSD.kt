package uz.magnumactive.benefit.ui.gap.create_game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import uz.magnumactive.benefit.R

import uz.magnumactive.benefit.ui.main.BenefitBSD
import kotlinx.android.synthetic.main.bsd_fill_card.*



class CreateGameBSD : BenefitBSD() {


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