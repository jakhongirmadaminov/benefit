package com.example.benefit.ui.partners_map

import android.animation.LayoutTransition
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.benefit.R
import com.example.benefit.remote.models.PartnerCategoryDTO
import com.example.benefit.ui.viewgroups.ItemCategorySmall
import com.example.benefit.ui.viewgroups.ItemCategorySquare
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.wunderlist.slidinglayer.SlidingLayer
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_branches_atms.*
import kotlinx.android.synthetic.main.activity_partners_map.*
import kotlinx.android.synthetic.main.activity_partners_map.ivBack
import java.util.ArrayList

class PartnersMapActivity : AppCompatActivity(), OnMapReadyCallback {

    private val adapter = GroupAdapter<GroupieViewHolder>()
    lateinit var categories: ArrayList<PartnerCategoryDTO>

    companion object {

        const val EXTRA_CATEGORIES = "CATEGORIES"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_partners_map)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        categories = intent.getParcelableArrayListExtra(EXTRA_CATEGORIES)!!
        setupViews()
        attachListeners()

    }

    private fun setupViews() {
        rlParent.layoutTransition.enableTransitionType(LayoutTransition.CHANGING);
        rvCategories.adapter = adapter

        adapter.clear()
        categories.forEach {
            adapter.add(ItemCategorySquare(it))
        }
        adapter.notifyDataSetChanged()
    }

    private fun attachListeners() {

        slidingLayer.setOnInteractListener(object : SlidingLayer.OnInteractListener {
            override fun onOpen() {
                clContent.layoutParams =
                    (clContent.layoutParams as RelativeLayout.LayoutParams).apply {
                        addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
                        addRule(RelativeLayout.ALIGN_PARENT_TOP, 0)
                    }

            }

            override fun onShowPreview() {

            }

            override fun onClose() {
                clContent.layoutParams =
                    (clContent.layoutParams as RelativeLayout.LayoutParams).apply {
                        addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 0)
                        addRule(RelativeLayout.ALIGN_PARENT_TOP)
                    }

            }

            override fun onOpened() {
            }

            override fun onPreviewShowed() {
            }

            override fun onClosed() {
            }
        })

        ivBack.setOnClickListener {
            onBackPressed()
        }

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