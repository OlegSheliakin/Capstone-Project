package home.oleg.placesnearme.presentation.feature.map.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.smedialink.common.Optional;

import java.util.List;

import javax.inject.Inject;

import home.oleg.placesnearme.PlacesNearMeApp;
import home.oleg.placesnearme.R;
import home.oleg.placesnearme.di.components.DaggerApplicationComponent;
import home.oleg.placesnearme.presentation.base.ViewActionObserver;
import home.oleg.placesnearme.presentation.feature.map.marker.MarkerMapper;
import home.oleg.placesnearme.presentation.feature.map.viewmodel.MapViewModel;
import home.oleg.placesnearme.presentation.viewdata.VenueViewData;

public class PlacesMapFragment extends BaseMapFragment implements MapView {

    @Inject
    MapViewModel viewModel;

    @Inject
    MarkerMapper markerMapper;

    private GoogleMap googleMap;

    public PlacesMapFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        injectDependencies();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FloatingActionButton fabCurrentLocation = view.findViewById(R.id.fabCurrentLocation);
        FloatingActionButton fabZoomIn = view.findViewById(R.id.fabZoomIn);
        FloatingActionButton fabZoomOut = view.findViewById(R.id.fabZoomOut);

        fabCurrentLocation.setOnClickListener(v -> {
            LatLng latLng = viewModel.getCurrentLocation();
        });

        fabZoomIn.setOnClickListener(v -> {
            Optional.of(googleMap)
                    .ifPresent(m -> m.animateCamera(CameraUpdateFactory.zoomIn()));
        });

        fabZoomOut.setOnClickListener(v -> {
            Optional.of(googleMap)
                    .ifPresent(m -> m.animateCamera(CameraUpdateFactory.zoomOut()));
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        viewModel.observe().observe(this, ViewActionObserver.create(this));
    }

    private void injectDependencies() {
        DaggerApplicationComponent.builder()
                .bind((PlacesNearMeApp) getActivity().getApplication())
                .build()
                .placeMapFragmentComponentBuilder()
                .bind(this)
                .build()
                .inject(this);
    }

    @Override
    public void showVenues(@NonNull List<VenueViewData> items) {
        Optional.of(googleMap).ifPresent(map -> {
            map.clear();

            List<MarkerOptions> markers = markerMapper.mapFrom(items);
            for (MarkerOptions marker : markers) {
                map.addMarker(marker);
            }
        });
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
