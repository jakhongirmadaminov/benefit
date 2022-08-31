package uz.magnumactive.benefit.ui.marketplace.dialogs

import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.bsd_market_address_on_map.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.ui.main.BenefitFixedHeightBSD


class SelectAddressOnMapBSD(val onPositionSelected: (LatLng, String?) -> Unit) :
    BenefitFixedHeightBSD(), OnMapReadyCallback {
    private lateinit var gMap: GoogleMap
    var point: LatLng? = null
    var placeName: String? = null


    private fun getLocationDetails(value: LatLng) {
        val client = OkHttpClient().newBuilder()
            .build()
        val request = Request.Builder()
            .url("https://maps.googleapis.com/maps/api/geocode/json?latlng=${value.latitude},${value.longitude}&key=AIzaSyDYlvQBH89UFA3INzVX2FwK5TpzcHcBowg")
            .method("GET", null)
            .build()

        lifecycleScope.launch(IO) {
            val response: Response = client.newCall(request).execute()
            val resultBodyString = response.body?.string() ?: ""
            val result =
                Regex("\"formatted_address\" : \"(.*)\"").find(resultBodyString)?.destructured?.component1()
            withContext(Main) {
                placeName = result
                btnConfirm.myEnabled(true)
                btnConfirm.setTextColor(Color.WHITE)
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bsd_market_address_on_map, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        attachListeners()
        subscribeObservers()
        setupView()
    }

    private fun attachListeners() {
        btnConfirm.setOnClickListener {
            point?.let {
                onPositionSelected(it, placeName)
                dismiss()
            }
        }
    }

    private fun setupView() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun subscribeObservers() {

    }


    override fun onMapReady(googleMap: GoogleMap) {
        gMap = googleMap
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            val success: Boolean = gMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.style_json)
            )
            if (!success) {
                Log.e("TAAAG", "Style parsing failed.")
            }
        } catch (e: Resources.NotFoundException) {
            Log.e("TAAAG", "Can't find style. Error: ", e)
        }
//        partner.coords_array?.lat?.let { latitude ->
//            val targetLatLng = LatLng(latitude.toDouble(), partner.coords_array!!.lan!!.toDouble())
//            displayData(targetLatLng)
//            gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(targetLatLng, 17F))
//        }

        gMap.setOnMapClickListener {
            gMap.clear()
            point = it
            getLocationDetails(it)
            gMap.addMarker(MarkerOptions().position(it))
        }
    }

}
