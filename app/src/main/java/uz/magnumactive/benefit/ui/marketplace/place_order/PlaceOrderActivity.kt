package uz.magnumactive.benefit.ui.marketplace.place_order

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_loan.*
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.ui.base.BaseActionbarActivity

class PlaceOrderActivity : BaseActionbarActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_place_order)
        setSupportActionBar(tool_bar)

        super.onCreate(savedInstanceState)
    }
}
