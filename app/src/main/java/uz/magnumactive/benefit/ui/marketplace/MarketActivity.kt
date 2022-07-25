package uz.magnumactive.benefit.ui.marketplace

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_market.*
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.ui.base.BaseActionbarActivity

class MarketActivity : BaseActionbarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_market)
        setSupportActionBar(tool_bar)

        val navController = findNavController(R.id.nav_host_fragment)
        nav_view.setupWithNavController(navController)
    }

}