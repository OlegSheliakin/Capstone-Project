package home.oleg.placesnearme.presentation.feature.map.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.smedialink.common.Optional;
import com.smedialink.common.function.Action;

import java.util.List;

import javax.inject.Inject;

import home.oleg.placenearme.models.UserLocation;
import home.oleg.placesnearme.PlacesNearMeApp;
import home.oleg.placesnearme.R;
import home.oleg.placesnearme.di.components.DaggerApplicationComponent;
import home.oleg.placesnearme.presentation.base.ViewActionObserver;
import home.oleg.placesnearme.presentation.feature.map.marker.MarkerMapper;
import home.oleg.placesnearme.presentation.feature.map.viewmodel.VenueViewModel;
import home.oleg.placesnearme.presentation.feature.map.viewmodel.UserLocationViewModel;
import home.oleg.placesnearme.presentation.viewdata.VenueViewData;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class VenuesMapFragment extends BaseMapFragment implements MapView {

    public static final int USER_LOCATION_ZOOM = 16;

    @Inject
    VenueViewModel venueViewModel;

    @Inject
    MarkerMapper markerMapper;

    private GoogleMap googleMap;

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

        fabCurrentLocation.setOnClickListener(v -> onShowCurrenLocationClicked());

        fabZoomIn.setOnClickListener(v -> {
            Optional.of(googleMap)
                    .ifPresent(m -> m.animateCamera(CameraUpdateFactory.zoomIn()));
        });

        fabZoomOut.setOnClickListener(v -> {
            Optional.of(googleMap)
                    .ifPresent(m -> m.animateCamera(CameraUpdateFactory.zoomOut()));
        });

        venueViewModel.observer().observe(this, ViewActionObserver.create(this));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        VenuesMapFragmentPermissionsDispatcher.initLocationSettingsWithPermissionCheck(this, googleMap);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        VenuesMapFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
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
    public void showUserLocation(UserLocation userLocation) {
        LatLng latLng = new LatLng(userLocation.getLat(), userLocation.getLng());

        Optional.of(googleMap).ifPresent(googleMap -> {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, USER_LOCATION_ZOOM));
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

    @SuppressLint("MissingPermission")
    @NeedsPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
    void onShowCurrenLocationClicked() {
        venueViewModel.getUserLocation();
    }

    @SuppressLint("MissingPermission")
    @NeedsPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
    void initLocationSettings(GoogleMap googleMap) {
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        googleMap.setMyLocationEnabled(true);

        venueViewModel.getUserLocation();
        venueViewModel.getRecommendedVenues();
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

}
