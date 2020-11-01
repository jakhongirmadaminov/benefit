package com.example.benefit.ui.partners_map

import android.animation.LayoutTransition
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.benefit.R
import com.example.benefit.remote.models.Partner
import com.example.benefit.remote.models.PartnerCategoryDTO
import com.example.benefit.ui.viewgroups.ItemCategorySquare
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.Marker
import com.wunderlist.slidinglayer.SlidingLayer
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_partners_map.*
import java.util.*


class PartnersMapActivity : AppCompatActivity(), OnMapReadyCallback {

    private val markerList = ArrayList<Marker>()
    private lateinit var gMap: GoogleMap
    private val adapter = GroupAdapter<GroupieViewHolder>()
    lateinit var categories: ArrayList<PartnerCategoryDTO>

    companion object {
        const val EXTRA_CATEGORIES = "CATEGORIES"
    }

    val viewModel: PartnersMapViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_partners_map)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        categories = intent.getParcelableArrayListExtra(EXTRA_CATEGORIES)!!
        setupViews()
        attachListeners()
        subscribeObservers()
        viewModel.getPartnersForCategory(categories[0].id)
    }

    private fun subscribeObservers() {

        viewModel.isLoading.observe(this, {
            val isLoading = it ?: return@observe
            if (isLoading) {
                slidingLayer.closeLayer(true)
                progress.visibility = View.VISIBLE
            } else {
                progress.visibility = View.GONE
            }
        })


        viewModel.partnersResp.observe(this, {
            val result = it ?: return@observe
            loadResult(result)
        })
        viewModel.errorMessage.observe(this, {
            if (it == null) {
                tvMessage.visibility = View.GONE
            } else {
                tvMessage.visibility = View.VISIBLE
                tvMessage.text = it
            }
        })


    }

    private fun loadResult(result: List<Partner>) {
        gMap.clear()
        if (result.isEmpty()) {
            tvMessage.visibility = View.VISIBLE
            tvMessage.text = getString(R.string.there_are_no_partners_in_this_category)
        } else {


        }


    }

    private fun displayData(partnerList: List<Partner>) {
        var marker: Marker

        // Removes all markers, overlays, and polylines from the map.
        gMap.clear()
        markerList.clear()

        // Zoom in, animating the camera.
        gMap.animateCamera(CameraUpdateFactory.zoomTo(17F), 2000, null)

        //       // Add marker of user's position
//        val userIndicator = MarkerOptions()
//            .position(LatLng(lat, lng))
//            .title("You are here")
//            .snippet("lat:" + lat.toString() + ", lng:" + lng)
//        marker = gMap.addMarker(userIndicator)
//        //        Log.e(TAG, "Marker id '" + marker.getId() + "' added to list.");
//        markerList.add(marker)

        // Add marker of venue if there is any
//        for (venue in partnerList) {
//            val markerOptions = MarkerOptions()
//                .position(
//                    LatLng(
//                        venue.().toDouble(),
//                        venue.getLongitude().toDouble()
//                    )
//                )
//                .title(venue.getName())
//                .snippet("Guys:" + " and Girls:" )
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_orange_pin))
//            marker = gMap.addMarker(markerOptions)
//            //                Log.e(TAG, "Marker id '" + marker.getId() + "' added to list.");
//            markerList.add(marker)
//        }
//
//        // Move the camera instantly to where lat and lng shows.
//        if (lat !== 0 && lng !== 0) gMap.moveCamera(
//            CameraUpdateFactory.newLatLngZoom(
//                LatLng(
//                    lat,
//                    lng
//                ), ZOOM_LEVEL
//            )
//        )
//        gMap.setInfoWindowAdapter(object : InfoWindowAdapter {
//            override fun getInfoWindow(marker: Marker): View {
//                return null
//            }
//
//            override fun getInfoContents(marker: Marker): View {
//                return null
//            }
//        })
//        gMap.setOnInfoWindowClickListener(OnInfoWindowClickListener { marker ->
//            var markerId = -1
//            val str = marker.id
//            Log.i(TAG, "Marker id: $str")
//            for (i in 0 until markerList.size()) {
//                markerId = i
//                val m: Marker = markerList.get(i)
//                if (m.id == marker.id) break
//            }
//            markerId -= 1 // Because first item of markerList is user's marker
//            Log.i(TAG, "Marker id $markerId clicked.")
//
//            // Ignore if User's marker clicked
//            if (markerId < 0) return@OnInfoWindowClickListener
//            try {
//                val venue: Venue = partnerList!![markerId]
//                if (venue.getCan_checkin().equalsIgnoreCase("true")) {
//                    val fragment: Fragment = VenueFragment.newInstance(venue)
//                    if (fragment != null) changeFragmentLister.OnReplaceFragment(fragment) else Log.e(
//                        TAG,
//                        "Error! venue shouldn't be null"
//                    )
//                }
//            } catch (e: NumberFormatException) {
//                e.printStackTrace()
//            } catch (e: IndexOutOfBoundsException) {
//                e.printStackTrace()
//            } catch (e: NullPointerException) {
//                e.printStackTrace()
//            }
//        })
    }


    private fun setupViews() {
        rlParent.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
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
        gMap = googleMap
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            val success: Boolean = gMap.setMapStyle(
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

        gMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(LatLng(41.311104, 69.279996), 17F)
        )

    }

}