package com.example.benefit.ui.branches_atms

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.example.benefit.R
import com.example.benefit.ui.base.BaseActionbarActivity
import com.example.benefit.ui.base.BaseActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import kotlinx.android.synthetic.main.activity_branches_atms.*


class BranchesAtmsActivity : BaseActivity(), OnMapReadyCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_branches_atms)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        setupViews()
        attachListeners()

    }

    private fun setupViews() {
        selectFillWithCash()
    }

    private fun attachListeners() {
        ivBack.setOnClickListener {
            onBackPressed()
        }

        cardFillWithCash.setOnClickListener {
            selectFillWithCash()
        }
        cardWithdrawCash.setOnClickListener {
            selectWithdrawCash()
        }

        cardNowWorking.setOnClickListener {
            selectNowWorking()
        }

    }

    private fun selectNowWorking() {

        cardFillWithCash.setBackgroundResource(R.drawable.shape_oval_grey)
        cardWithdrawCash.setBackgroundResource(R.drawable.shape_oval_grey)
        cardNowWorking.setBackgroundResource(R.drawable.shape_oval_grey_darker)

        tvFillWithCash.alpha = 0.4f
        tvWithdrawCash.alpha = 0.4f
        tvNowWorking.alpha = 1f

        ivFillWithCash.alpha = 0.4f
        ivWidthdrawCash.alpha = 0.4f
        ivNowWorking.alpha = 1f
    }

    private fun selectFillWithCash() {

        cardFillWithCash.setBackgroundResource(R.drawable.shape_oval_grey_darker)
        cardNowWorking.setBackgroundResource(R.drawable.shape_oval_grey)
        cardWithdrawCash.setBackgroundResource(R.drawable.shape_oval_grey)

        tvFillWithCash.alpha = 1f
        tvWithdrawCash.alpha = 0.4f
        tvNowWorking.alpha = 0.4f

        ivFillWithCash.alpha = 1f
        ivWidthdrawCash.alpha = 0.4f
        ivNowWorking.alpha = 0.4f
    }

    private fun selectWithdrawCash() {
        cardFillWithCash.setBackgroundResource(R.drawable.shape_oval_grey)
        cardWithdrawCash.setBackgroundResource(R.drawable.shape_oval_grey_darker)
        cardNowWorking.setBackgroundResource(R.drawable.shape_oval_grey)

        tvFillWithCash.alpha = 0.4f
        tvWithdrawCash.alpha = 1f
        tvNowWorking.alpha = 0.4f

        ivFillWithCash.alpha = 0.4f
        ivWidthdrawCash.alpha = 1f
        ivNowWorking.alpha = 0.4f
    }

    override fun onMapReady(googleMap: GoogleMap) {
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            val success: Boolean = googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    this, R.raw.style_json
                )
            )
            if (!success) {
                Log.e("TAAAG", "Style parsing failed.")
            }
        } catch (e: Resources.NotFoundException) {
            Log.e("TAAAG", "Can't find style. Error: ", e)
        }


        googleMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(41.311104, 69.279996),
                17F
            )
        )

    }


}