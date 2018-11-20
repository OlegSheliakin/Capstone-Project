package home.oleg.placesnearme.feature_map.presentation.ui.adapter

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import home.oleg.placesnearme.feature_map.R

/**
 * Created by Oleg Sheliakin on 20.11.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */

class LifeCycleAwareMap : LifecycleObserver, OnMapReadyCallback {

    private var mapView: MapView? = null
    private var lifecycle: Lifecycle? = null

    fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mapView = view.findViewById(R.id.mapView)
        mapView?.getMapAsync(this)
        mapView?.onCreate(savedInstanceState)
    }

    override fun onMapReady(p0: GoogleMap?) {

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        mapView?.onResume()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        mapView?.onPause()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        mapView?.onDestroy()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun onLowMemory() {
        mapView?.onLowMemory()
    }

}