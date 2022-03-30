package uz.magnumactive.benefit.ui.partners_map

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.toBitmap
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.remote.models.Partner
import uz.magnumactive.benefit.ui.base.BaseActivity
import uz.magnumactive.benefit.ui.partner_home.PartnerHomeActivity
import uz.magnumactive.benefit.util.SizeUtils
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.activity_partner_on_map.*


class PartnerOnMapActivity : BaseActivity(), OnMapReadyCallback {

    private lateinit var gMap: GoogleMap
    lateinit var partner: Partner


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_partner_on_map)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        partner = intent.getParcelableExtra(PartnerHomeActivity.EXTRA_PARTNER)!!
        setupViews()
        attachListeners()
        subscribeObservers()
    }

    private fun subscribeObservers() {


    }


    private fun displayData(coordinates: LatLng) {
        var marker: Marker

        // Removes all markers, overlays, and polylines from the map.
//        gMap.clear()
//        markerList.clear()

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
        val markerOptions = MarkerOptions()
            .position(coordinates)
            .title(partner.title)
//            .snippet("Guys:" + " and Girls:")
            .icon(
                BitmapDescriptorFactory.fromBitmap(
                    AppCompatResources.getDrawable(this, R.drawable.ic_baseline_location_on_24)
                    !!.toBitmap(
                        SizeUtils.dpToPx(this, 35).toInt(),
                        SizeUtils.dpToPx(this, 35).toInt()
                    )
                )
            )
        marker = gMap.addMarker(markerOptions)
        //                Log.e(TAG, "Marker id '" + marker.getId() + "' added to list.");
//        markerList.add(marker)
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


    }

    private fun attachListeners() {

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
        partner.coords_array?.lat?.let { latitude ->
            val targetLatLng = LatLng(latitude.toDouble(), partner.coords_array!!.lan!!.toDouble())
            displayData(targetLatLng)
            gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(targetLatLng, 17F))
        }


    }

}