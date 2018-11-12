package home.oleg.placesnearme.feature_map.presentation.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.smedialink.feature_venue_detail.view.VenueViewFacade
import home.oleg.placenearme.models.Section
import home.oleg.placenearme.models.UserLocation
import home.oleg.placesnearme.core_presentation.ShowHideBottomBarListener
import home.oleg.placesnearme.core_presentation.base.BackHandler
import home.oleg.placesnearme.core_presentation.base.handle
import home.oleg.placesnearme.core_presentation.delegate.ToastDelegate
import home.oleg.placesnearme.core_presentation.extensions.observe
import home.oleg.placesnearme.core_presentation.viewdata.PreviewVenueViewData
import home.oleg.placesnearme.feature_map.di.PlacesMapFragmentComponent
import home.oleg.placesnearme.feature_map.mapper.MarkerMapper
import home.oleg.placesnearme.feature_map.presentation.adapter.SectionsAdapter
import home.oleg.placesnearme.feature_map.presentation.state.MapViewState
import home.oleg.placesnearme.feature_map.presentation.viewmodel.UserLocationViewModel
import home.oleg.placesnearme.feature_map.presentation.viewmodel.VenuesViewModel
import kotlinx.android.synthetic.main.fragment_map.*
import kotlinx.android.synthetic.main.layout_search.*
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions
import javax.inject.Inject

@RuntimePermissions
class VenuesMapFragment
    : BaseMapFragment(), SectionsAdapter.SectionSelectListener, BackHandler {

    @Inject
    lateinit var toastDelegate: ToastDelegate

    @Inject
    lateinit var venuesViewModel: VenuesViewModel

    @Inject
    lateinit var userLocationViewModel: UserLocationViewModel

    @Inject
    lateinit var venueViewFacade: VenueViewFacade

    @Inject
    lateinit var markerMapper: MarkerMapper

    @Inject
    lateinit var sectionsAdapter: SectionsAdapter

    private var showHideBottomBarListener: ShowHideBottomBarListener? = null

    private var googleMap: GoogleMap? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is ShowHideBottomBarListener) {
            this.showHideBottomBarListener = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toastDelegate.attach(view.context)

        rvSections.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvSections.adapter = sectionsAdapter
        sectionsAdapter.setHasStableIds(true)

        loadingView.setOnRetryCLickListener {
            sectionsAdapter.selectedItem?.let {
                venuesViewModel.getRecommendedVenues(it)
            }
        }

        venueViewFacade.onCreateView(view)
        venueViewFacade.setShowHideBottomBarListener(showHideBottomBarListener!!)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val shouldShowSearch = searchAppBar.visibility == View.VISIBLE
        outState.putBoolean(KEY_SEARCH_VISIBILITY, shouldShowSearch)
        outState.putInt(KEY_SEARCH_SELECTED_ITEM_POSITION, sectionsAdapter.selectedItemPosition)
        venueViewFacade.onSaveState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            val position = savedInstanceState.getInt(KEY_SEARCH_SELECTED_ITEM_POSITION)
            val shouldShow = savedInstanceState.getBoolean(KEY_SEARCH_VISIBILITY)
            showSearch(shouldShow)
            sectionsAdapter.setSelected(position)
            venueViewFacade.onRestoreState(savedInstanceState)
        } else {
            showSearch(false)
        }
    }

    fun onCloseSearchClicked() {
        venuesViewModel.closeSearch()
    }

    fun oSearchClicked() {
        venuesViewModel.openSearch()
    }

    fun onZoomInClicked() {
        googleMap?.animateCamera(CameraUpdateFactory.zoomIn())
    }

    fun onZoomOutClicked() {
        googleMap?.animateCamera(CameraUpdateFactory.zoomOut())
    }

    fun onMyLocationClicked() {
        onShowCurrentLocationClickedWithPermissionCheck()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap

        observe(venuesViewModel.data, this::show)
        observe(venuesViewModel.state, this::render)
        observe(userLocationViewModel.location, this::renderLocation)

        initLocationSettingsWithPermissionCheck(googleMap)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    private fun show(items: Collection<PreviewVenueViewData>) {
        googleMap?.let {
            it.clear()

            val pairs = markerMapper.mapFrom(items)
            val markerVenueViewDataMap = HashMap<String, PreviewVenueViewData>()

            for (pair in pairs) {
                val id = it.addMarker(pair.first).id
                markerVenueViewDataMap[id] = pair.second
            }

            venuesViewModel.setVenues(markerVenueViewDataMap)
        }
    }

    @SuppressLint("MissingPermission")
    @NeedsPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
    fun onShowCurrentLocationClicked() {
        userLocationViewModel.requestUserLocation()
    }

    @SuppressLint("MissingPermission")
    @NeedsPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
    fun initLocationSettings(googleMap: GoogleMap) {
        googleMap.uiSettings.isMyLocationButtonEnabled = false
        googleMap.isMyLocationEnabled = true
        googleMap.setOnMarkerClickListener {
            val venueMapViewData = venuesViewModel.getVenue(it.id)
            venueViewFacade.setVenue(venueMapViewData)
            return@setOnMarkerClickListener true
        }

        userLocationViewModel.requestUserLocation()
    }

    override fun sectionSelected(section: Section) {
        venuesViewModel.getRecommendedVenues(section)
    }

    override fun inject() {
        PlacesMapFragmentComponent.Injector.inject(this)
    }

    private fun render(mapViewState: MapViewState?) {
        if (mapViewState == null) return

        mapViewState.error?.handle { toastDelegate.showError(it.errorText) }

        showSearch(mapViewState.isSearchShown)

        if (mapViewState.isVenuesLoading) {
            loadingView.showLoading()
        } else if (mapViewState.error != null) {
            loadingView.showRetry()
        } else {
            loadingView.hide()
        }

    }

    private fun showSearch(shouldShow: Boolean) {
        if (shouldShow) {
            searchAppBar.visibility = View.VISIBLE
            fabSearch.hide()
        } else {
            searchAppBar.visibility = View.GONE
            fabSearch.show()
        }
    }

    private fun renderLocation(userLocation: UserLocation?) {
        if (userLocation == null) return
        val latLng = LatLng(userLocation.lat, userLocation.lng)
        googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, USER_LOCATION_ZOOM.toFloat()))
    }

    override fun onBackPressed(): Boolean {
        if (venueViewFacade.isShown) {
            venueViewFacade.dismiss()
            return true
        }

        if (searchAppBar.isVisible) {
            venuesViewModel.closeSearch()
            return true
        }

        return false
    }

    companion object {
        private val KEY_SEARCH_VISIBILITY = "key_search_visibility"
        private val KEY_SEARCH_SELECTED_ITEM_POSITION = "key_search_selected_item_position"
        private val USER_LOCATION_ZOOM = 14
    }
}
