package home.oleg.placesnearme.feature_map.presentation.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.smedialink.common.base.BackHandler
import com.smedialink.common.base.BaseFragment
import com.smedialink.common.base.handle
import com.smedialink.common.ext.horizontal
import com.smedialink.common.ext.observe
import home.oleg.placesnearme.corepresentation.api.ShowHideBottomBar
import home.oleg.placesnearme.corepresentation.delegate.ToastDelegate
import home.oleg.placesnearme.corepresentation.viewdata.PreviewVenueViewData
import home.oleg.placesnearme.coredata.location.ReactiveLocationSettings
import home.oleg.placesnearme.coredomain.models.Section
import home.oleg.placesnearme.coredomain.models.UserLocation
import home.oleg.placesnearme.feature_map.R
import home.oleg.placesnearme.feature_map.di.PlacesMapFragmentComponent
import home.oleg.placesnearme.feature_map.mapper.MarkerMapper
import home.oleg.placesnearme.feature_map.presentation.state.MapViewState
import home.oleg.placesnearme.feature_map.presentation.ui.adapter.LifeCycleAwareMap
import home.oleg.placesnearme.feature_map.presentation.ui.adapter.SectionsAdapter
import home.oleg.placesnearme.feature_map.presentation.viewmodel.MapViewModel
import home.oleg.placesnearme.feature_venue_detail.presentation.VenueViewFacade
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_map.*
import kotlinx.android.synthetic.main.layout_search.*
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.RuntimePermissions
import javax.inject.Inject

private const val REQUEST_CODE_CHECK_LOCATION_SETTINGS = 10001

@RuntimePermissions
class VenuesMapFragment
    : BaseFragment(), SectionsAdapter.SectionSelectListener, BackHandler {

    @Inject
    lateinit var toastDelegate: ToastDelegate

    @Inject
    lateinit var mapViewModel: MapViewModel

    @Inject
    lateinit var venueViewFacade: VenueViewFacade

    @Inject
    lateinit var markerMapper: MarkerMapper

    @Inject
    lateinit var sectionsAdapter: SectionsAdapter

    @Inject
    lateinit var reactiveLocationSettings: ReactiveLocationSettings

    override val layoutRes: Int = R.layout.fragment_map

    private val lifeCycleAwareMap = LifeCycleAwareMap.create(this, OnMapReadyCallback {
        this.googleMap = it

        mapViewModel.venues.observe(this, this::renderVenues)
        mapViewModel.viewState.observe(this, this::render)
        mapViewModel.location.observe(this, this::renderLocation)

        initLocationSettingsWithPermissionCheck(it)
    })

    private lateinit var showHideBottomBarListener: ShowHideBottomBar

    private var googleMap: GoogleMap? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is ShowHideBottomBar) {
            this.showHideBottomBarListener = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifeCycleAwareMap.onViewCreated(view, savedInstanceState)
        toastDelegate.attach(view.context)

        sectionsAdapter.setHasStableIds(true)

        rvSections.horizontal(withAdapter = sectionsAdapter)

        loadingView.setOnRetryCLickListener {
            sectionsAdapter.selectedItem?.let(mapViewModel::getRecommendedVenues)
        }

        fabSearch.setOnClickListener {
            mapViewModel.openSearch()
        }

        btnClose.setOnClickListener {
            mapViewModel.closeSearch()
        }

        fabZoomIn.setOnClickListener {
            googleMap?.animateCamera(CameraUpdateFactory.zoomIn())
        }

        fabZoomOut.setOnClickListener {
            googleMap?.animateCamera(CameraUpdateFactory.zoomOut())
        }

        fabCurrentLocation.setOnClickListener {
            onShowCurrentLocationClickedWithPermissionCheck()
        }

        venueViewFacade.onCreateView(view, viewLifecycleOwner)
        venueViewFacade.setShowHideBottomBarListener(showHideBottomBarListener)
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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    private fun renderVenues(items: Collection<PreviewVenueViewData>) {
        googleMap?.apply {
            clear()

            val pairs = markerMapper.mapFrom(items)
            val markerVenueViewDataMap = HashMap<String, PreviewVenueViewData>()

            for (pair in pairs) {
                val id = addMarker(pair.first).id
                markerVenueViewDataMap[id] = pair.second
            }

            mapViewModel.venuesHolder.clear()
            mapViewModel.venuesHolder.putAll(markerVenueViewDataMap)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_CHECK_LOCATION_SETTINGS && resultCode == RESULT_OK) {
            mapViewModel.requestUserLocation()
        }
    }

    @SuppressLint("MissingPermission")
    @NeedsPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
    fun onShowCurrentLocationClicked() {
        mapViewModel.requestUserLocation()
    }

    @SuppressLint("MissingPermission")
    @NeedsPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
    fun initLocationSettings(googleMap: GoogleMap) {
        reactiveLocationSettings.checkSettings()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onComplete = {
                            mapViewModel.requestUserLocation()
                        },
                        onError = {
                            if (it is ResolvableApiException) {
                                it.startResolutionForResult(activity, REQUEST_CODE_CHECK_LOCATION_SETTINGS)
                            } else {
                                toastDelegate.showError(getString(R.string.unexpected_error))
                            }
                        }
                )

        googleMap.apply {
            uiSettings.isMyLocationButtonEnabled = false
            isMyLocationEnabled = true
            setOnMarkerClickListener {
                mapViewModel.venuesHolder[it.id]?.let(venueViewFacade::setVenue)
                return@setOnMarkerClickListener true
            }

        }
    }

    override fun sectionSelected(section: Section) {
        mapViewModel.getRecommendedVenues(section)
    }

    override fun inject() {
        PlacesMapFragmentComponent.Injector.inject(this)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        lifeCycleAwareMap.onLowMemory()
    }

    private fun render(mapViewState: MapViewState) {
        mapViewState.error?.handle { toastDelegate.showError(it.errorText) }

        showSearch(mapViewState.isSearchShown)

        when {
            mapViewState.isVenuesLoading -> loadingView.showLoading()
            mapViewState.error != null -> loadingView.showRetry()
            else -> loadingView.hide()
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

    private fun renderLocation(userLocation: UserLocation) {
        val latLng = LatLng(userLocation.lat, userLocation.lng)
        googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, USER_LOCATION_ZOOM.toFloat()))
    }

    override fun onBackPressed(): Boolean {
        if (venueViewFacade.dismiss()) {
            return true
        }

        if (searchAppBar.isVisible) {
            mapViewModel.closeSearch()
            return true
        }

        return false
    }

    companion object {
        private const val KEY_SEARCH_VISIBILITY = "key_search_visibility"
        private const val KEY_SEARCH_SELECTED_ITEM_POSITION = "key_search_selected_item_position"
        private const val USER_LOCATION_ZOOM = 14
    }
}
