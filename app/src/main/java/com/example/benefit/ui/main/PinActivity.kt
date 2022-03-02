package com.example.benefit.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.example.benefit.R
import com.example.benefit.ui.auth.AuthActivity
import com.example.benefit.util.AppPrefs
import kotlinx.android.synthetic.main.activity_pin.*

class PinActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin)

        attachListeners()

    }

    private fun attachListeners() {


        tvZero.setOnClickListener {
            appendToPin("0")
        }

        tvOne.setOnClickListener {
            appendToPin("1")
        }


        tvTwo.setOnClickListener {
            appendToPin("2")
        }


        tvThree.setOnClickListener {
            appendToPin("3")
        }


        tvFour.setOnClickListener {
            appendToPin("4")
        }


        tvFive.setOnClickListener {
            appendToPin("5")
        }


        tvSix.setOnClickListener {
            appendToPin("6")
        }


        tvSeven.setOnClickListener {
            appendToPin("7")
        }


        tvEight.setOnClickListener {
            appendToPin("8")
        }

        tvNine.setOnClickListener {
            appendToPin("9")
        }

        ivLogout.setOnClickListener {
            AppPrefs.logOut()
            startActivity(Intent(this, AuthActivity::class.java))
            this.finish()
        }


        pinView.doOnTextChanged { text, start, before, count ->
            text?.let { pinInput ->
                if (pinInput.length == 4) {
                    if (AppPrefs.pin == pinInput) {
                        finish()
                    } else {
                        pinView.setText("")
                    }
                }
            }
        }
    }

    private fun appendToPin(s: String) {
        pinView.append(s)
    }

    override fun onBackPressed() {

    }
}