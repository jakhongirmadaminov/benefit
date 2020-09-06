package com.example.benefit.ui.auth.order_card

import android.animation.LayoutTransition
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.benefit.R
import kotlinx.android.synthetic.main.activity_order_card.*

class OrderCardActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_card)

        setupViews()


//        Handler().postDelayed({
//
//            userTermsContent.visibility = View.GONE
//
//        }, 5000)
//
//        Handler().postDelayed({
//
//            userTermsContent.visibility = View.VISIBLE
//
//        }, 6000)
//
//        Handler().postDelayed({
//
//            userTermsContent.visibility = View.GONE
//
//        }, 9000)
    }

    private fun setupViews() {
        llParent.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)


    }


}
