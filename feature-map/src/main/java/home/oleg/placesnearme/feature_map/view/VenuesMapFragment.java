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

import java.util.Collection;
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
import home.oleg.placesnearme.core_presentation.base.BackHandler;
import home.oleg.placesnearme.core_presentation.base.ErrorEvent;
import home.oleg.placesnearme.core_presentation.delegate.ToastDelegate;
import home.oleg.placesnearme.core_presentation.viewdata.PreviewVenueViewData;
import home.oleg.placesnearme.feature_map.R;
import home.oleg.placesnearme.feature_map.R2;
import home.oleg.placesnearme.feature_map.adapter.SectionsAdapter;
import home.oleg.placesnearme.feature_map.custom.LoadingView;
import home.oleg.placesnearme.feature_map.di.PlacesMapFragmentComponent;
import home.oleg.placesnearme.feature_map.mapper.MarkerMapper;
import home.oleg.placesnearme.feature_map.state.MapViewState;
import home.oleg.placesnearme.feature_map.viewmodel.UserLocationViewModel;
import home.oleg.placesnearme.feature_map.viewmodel.VenuesViewModel;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

import static home.oleg.placesnearme.feature_map.view.VenuesMapFragmentPermissionsDispatcher.initLocationSettingsWithPermissionCheck;
import static home.oleg.placesnearme.feature_map.view.VenuesMapFragmentPermissionsDispatcher.onShowCurrentLocationClickedWithPermissionCheck;

@RuntimePermissions
public class VenuesMapFragment extends BaseMapFragment implements
        GoogleMap.OnMarkerClickListener, SectionsAdapter.SectionSelectListener, BackHandler {

    private static final String KEY_SEARCH_VISIBILITY = "key_search_visibility";
    private static final String KEY_SEARCH_SELECTED_ITEM_POSITION = "key_search_selected_item_position";
    private static final int USER_LOCATION_ZOOM = 14;

    @Inject
    ToastDelegate toastDelegate;

    @Inject
    VenuesViewModel venuesViewModel;

    @Inject
    UserLocationViewModel userLocationViewModel;

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

    @BindView(R2.id.loadingView)
    LoadingView loadingView;

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
        toastDelegate.attach(view.getContext());
        
        RecyclerView rvSections = view.findViewById(R.id.rvSections);
        rvSections.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvSections.setAdapter(sectionsAdapter);

        venueViewFacade.onCreateView(view);
        venueViewFacade.setShowHideBottomBarListener(showHideBottomBarListener);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        boolean shouldShowSearch = searchAppBar.getVisibility() == View.VISIBLE;
        outState.putBoolean(KEY_SEARCH_VISIBILITY, shouldShowSearch);
        outState.putInt(KEY_SEARCH_SELECTED_ITEM_POSITION, sectionsAdapter.getSelectedItemPosition());
        venueViewFacade.onSaveState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            int position = savedInstanceState.getInt(KEY_SEARCH_SELECTED_ITEM_POSITION);
            boolean shouldShow = savedInstanceState.getBoolean(KEY_SEARCH_VISIBILITY);
            showSearch(shouldShow);
            sectionsAdapter.setSelected(position);
            venueViewFacade.onRestoreState(savedInstanceState);
        } else {
            showSearch(false);
        }
    }

    @OnClick(R2.id.btnClose)
    public void onCloseSearchClicked() {
        venuesViewModel.closeSearch();
    }

    @OnClick(R2.id.fabSearch)
    public void oSearchClicked() {
        venuesViewModel.openSearch();
    }

    @OnClick(R2.id.fabZoomIn)
    public void onZoomInClicked() {
        Optional.of(googleMap).ifPresent(m -> m.animateCamera(CameraUpdateFactory.zoomIn()));
    }

    @OnClick(R2.id.fabZoomOut)
    public void onZoomOutClicked() {
        Optional.of(googleMap).ifPresent(m -> m.animateCamera(CameraUpdateFactory.zoomOut()));
    }

    @OnClick(R2.id.fabCurrentLocation)
    public void onMyLocationClicked() {
        onShowCurrentLocationClickedWithPermissionCheck(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        venuesViewModel.getState().observe(this, this::render);
        venuesViewModel.getData().observe(this, this::show);
        userLocationViewModel.getState().observe(this, this::renderLocation);
        initLocationSettingsWithPermissionCheck(this, googleMap);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        VenuesMapFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    private void show(@NonNull Collection<PreviewVenueViewData> items) {
        Optional.of(googleMap).ifPresent(map -> {
            map.clear();

            List<Pair<MarkerOptions, PreviewVenueViewData>> pairs = markerMapper.mapFrom(items);
            Map<String, PreviewVenueViewData> markerVenueViewDataMap = new HashMap<>();

            for (Pair<MarkerOptions, PreviewVenueViewData> pair : pairs) {
                String id = map.addMarker(pair.getFirst()).getId();
                markerVenueViewDataMap.put(id, pair.getSecond());
            }

            venuesViewModel.setVenues(markerVenueViewDataMap);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @SuppressLint("MissingPermission")
    @NeedsPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
    void onShowCurrentLocationClicked() {
        userLocationViewModel.getUserLocation();
    }

    @SuppressLint("MissingPermission")
    @NeedsPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
    void initLocationSettings(GoogleMap googleMap) {
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        googleMap.setMyLocationEnabled(true);
        googleMap.setOnMarkerClickListener(this);

        userLocationViewModel.getUserLocation();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        PreviewVenueViewData venueMapViewData = venuesViewModel.getVenue(marker.getId());
        onVenueSelected(venueMapViewData);
        return true;
    }

    @Override
    public void sectionSelected(Section section) {
        venuesViewModel.getRecommendedVenues(section);
    }

    private void render(MapViewState mapViewState) {
        if (mapViewState.getError() != null) {
            ErrorEvent errorEvent = mapViewState.getError();
            errorEvent.handle(() -> toastDelegate.showError(errorEvent.getErrorText()));
        }

        showSearch(mapViewState.isSearchShown());

        if (mapViewState.isVenuesLoading()) {
            loadingView.showLoading();
        } else if (mapViewState.getError() != null) {
            loadingView.showRetry();
        } else {
            loadingView.hide();
        }

    }

    private void showSearch(boolean shouldShow) {
        if (shouldShow) {
            searchAppBar.setVisibility(View.VISIBLE);
            fabSearch.hide();
        } else {
            searchAppBar.setVisibility(View.GONE);
            fabSearch.show();
        }
    }

    private void renderLocation(UserLocation userLocation) {
        LatLng latLng = new LatLng(userLocation.getLat(), userLocation.getLng());

        Optional.of(googleMap).ifPresent(googleMap -> {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, USER_LOCATION_ZOOM));
        });
    }

    private void injectDependencies() {
        PlacesMapFragmentComponent.Injector.inject(this);
    }

    private void onVenueSelected(PreviewVenueViewData venueMapViewData) {
        venueViewFacade.setVenue(venueMapViewData);
    }

    @Override
    public boolean onBackPressed() {
        if (venueViewFacade.isShown()) {
            venueViewFacade.dismiss();
            return true;
        }

        boolean isSearchVisible = searchAppBar.getVisibility() == View.VISIBLE;
        if (isSearchVisible) {
            venuesViewModel.closeSearch();
            return true;
        }

        return false;
    }
}
