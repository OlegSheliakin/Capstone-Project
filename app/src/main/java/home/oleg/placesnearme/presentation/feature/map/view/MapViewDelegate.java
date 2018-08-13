package home.oleg.placesnearme.presentation.feature.map.view;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import javax.inject.Inject;

import home.oleg.placesnearme.presentation.feature.map.viewmodel.MapViewModel;

public class MapViewDelegate implements OnMapReadyCallback {

    private GoogleMap map;
    private final MapViewModel viewModel;

    @Inject
    public MapViewDelegate(MapViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;
    }


}
