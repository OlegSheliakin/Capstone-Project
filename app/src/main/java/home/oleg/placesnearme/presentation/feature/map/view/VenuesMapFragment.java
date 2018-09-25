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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.smedialink.common.Optional;
import com.smedialink.common.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import home.oleg.placenearme.models.UserLocation;
import home.oleg.placesnearme.PlacesNearMeApp;
import home.oleg.placesnearme.R;
import home.oleg.placesnearme.di.HasComponent;
import home.oleg.placesnearme.presentation.feature.map.di.PlacesMapFragmentComponent;
import home.oleg.placesnearme.presentation.feature.map.marker.MarkerMapper;
import home.oleg.placesnearme.presentation.feature.map.viewmodel.VenuesMapViewModelFacade;
import home.oleg.placesnearme.presentation.feature.venue.view.VenueDetailsView;
import home.oleg.placesnearme.presentation.viewdata.VenueViewData;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

import static home.oleg.placesnearme.presentation.feature.map.view.VenuesMapFragmentPermissionsDispatcher.initLocationSettingsWithPermissionCheck;
import static home.oleg.placesnearme.presentation.feature.map.view.VenuesMapFragmentPermissionsDispatcher.onShowCurrenLocationClickedWithPermissionCheck;

@RuntimePermissions
public class VenuesMapFragment extends BaseMapFragment implements
        VenuesMapView,
        GoogleMap.OnMarkerClickListener,
        HasComponent<PlacesMapFragmentComponent>,
        GoogleMap.OnMapClickListener {

    private static final int USER_LOCATION_ZOOM = 16;

    @Inject
    VenuesMapViewModelFacade venuesMapViewModelFacade;

    @Inject
    MarkerMapper markerMapper;

    private PlacesMapFragmentComponent component;

    private GoogleMap googleMap;

    private VenueDetailsView venueDetailsView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        injectDependencies();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        venueDetailsView = view.findViewById(R.id.venueView);

        FloatingActionButton fabCurrentLocation = view.findViewById(R.id.fabCurrentLocation);
        FloatingActionButton fabZoomIn = view.findViewById(R.id.fabZoomIn);
        FloatingActionButton fabZoomOut = view.findViewById(R.id.fabZoomOut);

        fabCurrentLocation.setOnClickListener(v ->
                onShowCurrenLocationClickedWithPermissionCheck(this));

        fabZoomIn.setOnClickListener(v -> {
            Optional.of(googleMap)
                    .ifPresent(m -> m.animateCamera(CameraUpdateFactory.zoomIn()));
        });

        fabZoomOut.setOnClickListener(v -> {
            Optional.of(googleMap)
                    .ifPresent(m -> m.animateCamera(CameraUpdateFactory.zoomOut()));
        });

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
    public void showVenues(@NonNull List<VenueViewData> items) {
        Optional.of(googleMap).ifPresent(map -> {
            map.clear();

            List<Pair<MarkerOptions, VenueViewData>> pairs = markerMapper.mapFrom(items);
            Map<String, VenueViewData> markerVenueViewDataMap = new HashMap<>();

            for (Pair<MarkerOptions, VenueViewData> pair : pairs) {
                String id = map.addMarker(pair.getFirst()).getId();
                markerVenueViewDataMap.put(id, pair.getSecond());
            }

            venuesMapViewModelFacade.setVenues(markerVenueViewDataMap);
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
        venuesMapViewModelFacade.refreshRecomendedVenues();
    }

    private void injectDependencies() {
       /* component = DaggerApplicationComponent.builder()
                .bind((PlacesNearMeApp) getActivity().getApplication())
                .build()
                .placeMapFragmentComponentBuilder()
                .bind(this)
                .build();
        component.inject(this);*/
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        venuesMapViewModelFacade.selectVenue(marker.getId());
        return true;
    }

    @Override
    public PlacesMapFragmentComponent get() {
        return component;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        venuesMapViewModelFacade.geocode(latLng.latitude, latLng.longitude);
    }

    @Override
    public void showVenue(VenueViewData venueViewData) {
        venueDetailsView.showVenue(venueViewData);
    }
}
