package home.oleg.placesnearme.feature_map.presentation.ui

import android.os.Bundle
import android.view.View
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.smedialink.common.base.BaseFragment
import home.oleg.placesnearme.feature_map.R

abstract class BaseMapFragment : BaseFragment() {

    private var mapView: MapView? = null

    override val layoutRes: Int = R.layout.fragment_map

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView = view.findViewById(R.id.mapView)
        mapView?.getMapAsync(this::onMapReady)
        mapView?.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    abstract fun onMapReady(googleMap: GoogleMap)

}
