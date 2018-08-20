package home.oleg.placesnearme.presentation.feature.map.view;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import javax.inject.Inject;

import home.oleg.placesnearme.presentation.errorhandler.ErrorHandler;
import home.oleg.placesnearme.presentation.feature.map.viewmodel.MapViewModel;
import home.oleg.placesnearme.presentation.mapper.MarkerMapper;
import home.oleg.placesnearme.presentation.viewobjects.VenueViewObject;

public class MapViewDelegate implements OnMapReadyCallback, LifecycleObserver {

    private GoogleMap map;
    private final LifecycleOwner lifecycleOwner;
    private final ErrorHandler errorHandler;
    private final MapViewModel viewModel;

    @Inject
    public MapViewDelegate(LifecycleOwner lifecycleOwner,
                           ErrorHandler errorHandler,
                           MapViewModel viewModel) {
        this.lifecycleOwner = lifecycleOwner;
        this.errorHandler = errorHandler;
        this.viewModel = viewModel;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;
        viewModel.getRecommendedVenues();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        viewModel.observeResults().observe(lifecycleOwner, this::showVenues);
        viewModel.observeErrors().observe(lifecycleOwner, errorHandler::handle);
    }

    private void showVenues(Iterable<VenueViewObject> venues) {
        if (map == null) {
            return;
        }

        map.clear();

        List<MarkerOptions> markers = MarkerMapper.mapFrom(venues);
        for (MarkerOptions marker : markers) {
            map.addMarker(marker);
        }
    }

}
