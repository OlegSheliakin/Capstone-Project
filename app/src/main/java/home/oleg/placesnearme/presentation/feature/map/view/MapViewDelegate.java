package home.oleg.placesnearme.presentation.feature.map.view;

import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import javax.inject.Inject;

import home.oleg.placesnearme.presentation.feature.map.viewmodel.MapViewModel;
import home.oleg.placesnearme.presentation.mapper.MarkerMapper;
import home.oleg.placesnearme.presentation.viewobjects.VenueViewObject;
import io.reactivex.annotations.NonNull;

public class MapViewDelegate implements OnMapReadyCallback, LifecycleObserver, MapView {

    private GoogleMap map;
    private LifecycleOwner lifecycleOwner;
    private final MapViewModel viewModel;

    @Inject
    public MapViewDelegate(@NonNull MapViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void attachLifecycleOwner(LifecycleOwner lifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner;

        lifecycleOwner.getLifecycle().addObserver(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;

        viewModel.observeState().observe(lifecycleOwner, viewAction -> {
            if (viewAction != null) {
                viewAction.accept(this);
            }
        });
    }

    @Override
    public void showVenues(List<VenueViewObject> items) {
        if (map == null) {
            return;
        }

        map.clear();

        List<MarkerOptions> markers = MarkerMapper.mapFrom(items);
        for (MarkerOptions marker : markers) {
            map.addMarker(marker);
        }
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
