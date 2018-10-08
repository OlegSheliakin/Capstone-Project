package home.oleg.placesnearme.feature_map.view;

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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.smedialink.common.Optional;
import com.smedialink.common.Pair;
import com.smedialink.feature_venue_detail.venue.view.VenueFragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import home.oleg.placenearme.models.UserLocation;
import home.oleg.placesnearme.core_presentation.viewdata.VenueMapViewData;
import home.oleg.placesnearme.feature_map.R;
import home.oleg.placesnearme.feature_map.di.PlacesMapFragmentComponent;
import home.oleg.placesnearme.feature_map.marker.MarkerMapper;
import home.oleg.placesnearme.feature_map.viewmodel.VenuesMapViewModelFacade;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

import static home.oleg.placesnearme.feature_map.view.VenuesMapFragmentPermissionsDispatcher.initLocationSettingsWithPermissionCheck;
import static home.oleg.placesnearme.feature_map.view.VenuesMapFragmentPermissionsDispatcher.onShowCurrentLocationClickedWithPermissionCheck;

@RuntimePermissions
public class VenuesMapFragment extends BaseMapFragment implements
        VenuesMapView,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnMapClickListener, VenuesMapViewModelFacade.VenueClickListener {

    private static final int USER_LOCATION_ZOOM = 16;

    @Inject
    VenuesMapViewModelFacade venuesMapViewModelFacade;

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

        fabCurrentLocation.setOnClickListener(v ->
                onShowCurrentLocationClickedWithPermissionCheck(this));

        fabZoomIn.setOnClickListener(v -> {
            Optional.of(googleMap)
                    .ifPresent(m -> m.animateCamera(CameraUpdateFactory.zoomIn()));
        });

        fabZoomOut.setOnClickListener(v -> {
            Optional.of(googleMap)
                    .ifPresent(m -> m.animateCamera(CameraUpdateFactory.zoomOut()));
        });

        venuesMapViewModelFacade.addOnVenueCLickListener(this);
        venuesMapViewModelFacade.attachView(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        initLocationSettingsWithPermissionCheck(this, googleMap);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        VenuesMapFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @Override
    public void show(@NonNull List<VenueMapViewData> items) {
        Optional.of(googleMap).ifPresent(map -> {
            map.clear();

            List<Pair<MarkerOptions, VenueMapViewData>> pairs = markerMapper.mapFrom(items);
            Map<String, VenueMapViewData> markerVenueViewDataMap = new HashMap<>();

            for (Pair<MarkerOptions, VenueMapViewData> pair : pairs) {
                String id = map.addMarker(pair.getFirst()).getId();
                markerVenueViewDataMap.put(id, pair.getSecond());
            }

            venuesMapViewModelFacade.setVenues(markerVenueViewDataMap);
        });
    }

    @Override
    public void show(UserLocation userLocation) {
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
    void onShowCurrentLocationClicked() {
        venuesMapViewModelFacade.refreshUserLocation();
    }

    @SuppressLint("MissingPermission")
    @NeedsPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
    void initLocationSettings(GoogleMap googleMap) {
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        googleMap.setMyLocationEnabled(true);
        googleMap.setOnMarkerClickListener(this);
        googleMap.setOnMapClickListener(this);

        venuesMapViewModelFacade.refreshUserLocation();
        venuesMapViewModelFacade.refreshRecommendedVenues();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        venuesMapViewModelFacade.select(marker.getId());
        return true;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        venuesMapViewModelFacade.geocode(latLng.latitude, latLng.longitude);
    }

    private void injectDependencies() {
        PlacesMapFragmentComponent.Injector.inject(this);
    }

    @Override
    public void onVenueSelected(VenueMapViewData venueMapViewData) {
        VenueFragment.show(getChildFragmentManager(), R.id.container, venueMapViewData);
    }
}
