package home.oleg.placesnearme.feature_map.presentation.ui.adapter

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import home.oleg.placesnearme.feature_map.R
import javax.inject.Inject

/**
 * Created by Oleg Sheliakin on 20.11.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */

class LifeCycleAwareMap private constructor(
        lifecycleOwner: LifecycleOwner,
        private val onMapReadyCallback: OnMapReadyCallback
) : LifecycleObserver {

    private var mapView: MapView? = null

    init {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mapView = view.findViewById(R.id.mapView)
        mapView?.getMapAsync(onMapReadyCallback)
        mapView?.onCreate(savedInstanceState)
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

    fun onLowMemory() {
        mapView?.onLowMemory()
    }

    companion object {
        fun create(lifecycleOwner: LifecycleOwner, onMapReadyCallback: OnMapReadyCallback) : LifeCycleAwareMap {
            return LifeCycleAwareMap(lifecycleOwner, onMapReadyCallback)
        }
    }
}