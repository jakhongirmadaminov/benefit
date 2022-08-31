package uz.magnumactive.benefit.ui.marketplace.dialogs

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import uz.magnumactive.benefit.R
import uz.magnumactive.benefit.ui.main.BenefitFixedHeightBSD

class SelectAddressOnMapBSD(val onPositionSelected: (LatLng, String) -> Unit) :
    BenefitFixedHeightBSD(), OnMapReadyCallback {
    private lateinit var gMap: GoogleMap

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
    }

}
