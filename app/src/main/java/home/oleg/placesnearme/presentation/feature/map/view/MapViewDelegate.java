package home.oleg.placesnearme.presentation.feature.map.view;

import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import javax.inject.Inject;

import home.oleg.placesnearme.presentation.base.ViewActionObserver;
import home.oleg.placesnearme.presentation.feature.map.viewmodel.MapViewModel;
import home.oleg.placesnearme.presentation.feature.map.marker.MarkerMapper;
import home.oleg.placesnearme.presentation.viewdata.VenueViewData;
import io.reactivex.annotations.NonNull;

public class MapViewDelegate implements OnMapReadyCallback, LifecycleObserver, MapView {

    private final MapViewModel viewModel;
    private final MarkerMapper markerMapper;

    private GoogleMap map;
    private LifecycleOwner lifecycleOwner;

    @Inject
    public MapViewDelegate(@NonNull MapViewModel viewModel,
                           @NonNull MarkerMapper markerMapper) {
        this.viewModel = viewModel;
        this.markerMapper = markerMapper;
    }

    public void attachLifecycleOwner(LifecycleOwner lifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner;

        lifecycleOwner.getLifecycle().addObserver(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;

        viewModel.observe().observe(lifecycleOwner, ViewActionObserver.create(this));
    }

    @Override
    public void showVenues(List<VenueViewData> items) {
        if (map == null) {
            return;
        }

        map.clear();

        List<MarkerOptions> markers = markerMapper.mapFrom(items);
        for (MarkerOptions marker : markers) {
            map.addMarker(marker);
        }
    }

    @Override
    public void showError() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
