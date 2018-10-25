package home.oleg.placesnearme.feature_map.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.smedialink.common.Optional;
import com.smedialink.common.Pair;
import com.smedialink.feature_venue_detail.venue.view.VenueViewFacade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import home.oleg.placenearme.models.Section;
import home.oleg.placenearme.models.UserLocation;
import home.oleg.placesnearme.core_presentation.ShowHideBottomBarListener;
import home.oleg.placesnearme.core_presentation.viewdata.PreviewVenueViewData;
import home.oleg.placesnearme.feature_map.R;
import home.oleg.placesnearme.feature_map.R2;
import home.oleg.placesnearme.feature_map.adapter.SectionsAdapter;
import home.oleg.placesnearme.feature_map.di.PlacesMapFragmentComponent;
import home.oleg.placesnearme.feature_map.mapper.MarkerMapper;
import home.oleg.placesnearme.feature_map.viewmodel.VenuesMapViewModelFacade;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

import static home.oleg.placesnearme.feature_map.view.VenuesMapFragmentPermissionsDispatcher.initLocationSettingsWithPermissionCheck;
import static home.oleg.placesnearme.feature_map.view.VenuesMapFragmentPermissionsDispatcher.onShowCurrentLocationClickedWithPermissionCheck;

@RuntimePermissions
public class VenuesMapFragment extends BaseMapFragment implements
        VenuesMapView,
        GoogleMap.OnMarkerClickListener,
        VenuesMapViewModelFacade.VenueClickListener, SectionsAdapter.SectionSelectListener {

    private static final int USER_LOCATION_ZOOM = 14;

    @Inject
    VenuesMapViewModelFacade venuesMapViewModelFacade;

    @Inject
    VenueViewFacade venueViewFacade;

    @Inject
    MarkerMapper markerMapper;

    @Inject
    SectionsAdapter sectionsAdapter;

    @BindView(R2.id.searchAppBar)
    AppBarLayout searchAppBar;

    @BindView(R2.id.fabSearch)
    FloatingActionButton fabSearch;

    private ShowHideBottomBarListener showHideBottomBarListener;

    private GoogleMap googleMap;

    private Unbinder unbinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        injectDependencies();

        if (context instanceof ShowHideBottomBarListener) {
            this.showHideBottomBarListener = (ShowHideBottomBarListener) context;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);

        RecyclerView rvSections = view.findViewById(R.id.rvSections);
        rvSections.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvSections.setAdapter(sectionsAdapter);

        venueViewFacade.onCreateView(view);
        venueViewFacade.setShowHideBottomBarListener(showHideBottomBarListener);

        venuesMapViewModelFacade.attachView(this);
        venuesMapViewModelFacade.addOnVenueCLickListener(this);
    }

    @OnClick(R2.id.fabSearch)
    public void oSearchClicked() {
        fabSearch.hide();
        searchAppBar.setVisibility(View.VISIBLE);
    }

    @OnClick(R2.id.fabZoomIn)
    public void onZoomInClicked() {
        Optional.of(googleMap)
                .ifPresent(m -> m.animateCamera(CameraUpdateFactory.zoomIn()));
    }

    @OnClick(R2.id.fabZoomOut)
    public void onZoomOutClicked() {
        Optional.of(googleMap)
                .ifPresent(m -> m.animateCamera(CameraUpdateFactory.zoomOut()));
    }

    @OnClick(R2.id.fabCurrentLocation)
    public void onMyLocationClicked() {
        onShowCurrentLocationClickedWithPermissionCheck(this);
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
    public void show(@NonNull List<PreviewVenueViewData> items) {
        Optional.of(googleMap).ifPresent(map -> {
            map.clear();

            List<Pair<MarkerOptions, PreviewVenueViewData>> pairs = markerMapper.mapFrom(items);
            Map<String, PreviewVenueViewData> markerVenueViewDataMap = new HashMap<>();

            for (Pair<MarkerOptions, PreviewVenueViewData> pair : pairs) {
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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

        venuesMapViewModelFacade.refreshUserLocation();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        searchAppBar.setVisibility(View.GONE);
        fabSearch.show();

        Optional.of(googleMap).ifPresent(map -> {

        });
        venuesMapViewModelFacade.select(marker.getId());
        return true;
    }

    private void injectDependencies() {
        PlacesMapFragmentComponent.Injector.inject(this);
    }

    @Override
    public void onVenueSelected(PreviewVenueViewData venueMapViewData) {
        venueViewFacade.setVenue(venueMapViewData);
    }

    @Override
    public void sectionSelected(Section section) {
        venuesMapViewModelFacade.refreshRecommendedVenues(section);
    }
}
