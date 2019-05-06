package home.oleg.placesnearme.feature_map.presentation

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
import com.olegsheliakin.lifecycle.autodisposable.AutoDisposable
import com.olegsheliakin.lifecycle.autodisposable.disposeOnDestroy
import com.olegsheliakin.statebinder.StateBinder
import com.smedialink.common.base.BackHandler
import com.smedialink.common.base.BaseFragment
import com.smedialink.common.ext.*
import home.oleg.placesnearme.corepresentation.api.ShowHideBottomBar
import home.oleg.placesnearme.corepresentation.delegate.ToastDelegate
import home.oleg.placesnearme.corepresentation.viewdata.PreviewPlace
import home.oleg.placesnearme.coredata.location.ReactiveLocationSettings
import home.oleg.placesnearme.coredomain.models.Section
import home.oleg.placesnearme.feature_map.R
import home.oleg.placesnearme.feature_map.di.PlacesMapFragmentComponent
import home.oleg.placesnearme.feature_map.mapper.MarkerMapper
import home.oleg.placesnearme.feature_map.presentation.adapter.SectionsAdapter
import home.oleg.placesnearme.feature_map.presentation.ui.initLocationSettingsWithPermissionCheck
import home.oleg.placesnearme.feature_map.presentation.ui.onRequestPermissionsResult
import home.oleg.placesnearme.feature_map.presentation.ui.onShowCurrentLocationClickedWithPermissionCheck
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
class VenuesMapFragment : BaseFragment(), SectionsAdapter.SectionSelectListener, BackHandler {

    override val layoutRes: Int = R.layout.fragment_map

    @Inject
    lateinit var toastDelegate: ToastDelegate

    @Inject
    lateinit var viewModel: MapViewModel

    @Inject
    lateinit var venueViewFacade: VenueViewFacade

    @Inject
    lateinit var markerMapper: MarkerMapper

    @Inject
    lateinit var sectionsAdapter: SectionsAdapter

    @Inject
    lateinit var reactiveLocationSettings: ReactiveLocationSettings

    private lateinit var showHideBottomBarListener: ShowHideBottomBar

    private val lifeCycleAwareMap = LifeCycleAwareMap.create(this, OnMapReadyCallback {
        this.googleMap = it

        googleMap?.setOnMarkerClickListener {
            viewModel.venuesHolder[it.id]?.let(venueViewFacade::setVenue)
            return@setOnMarkerClickListener true
        }

        viewModel.state.observe(this, stateBinder::newState)

        initLocationSettingsWithPermissionCheck(it)
    })

    private var autoDisposable = AutoDisposable.create(viewLifecycleOwner)

    private var stateBinder = StateBinder.create<MapViewState>()

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
        venueViewFacade.onCreateView(view, viewLifecycleOwner)
        venueViewFacade.setShowHideBottomBarListener(showHideBottomBarListener)

        stateBinder.applyCurrentState()

        sectionsAdapter.setHasStableIds(true)
        rvSections.horizontal(withAdapter = sectionsAdapter)

        bindClicks()
        bindState()
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
            if(shouldShow) {
                viewModel.openSearch()
            } else {
                viewModel.closeSearch()
            }
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

    private fun bindClicks() {
        loadingView.setOnRetryCLickListener {
            sectionsAdapter.selectedItem?.let(viewModel::getRecommendedVenues)
        }

        fabSearch.setOnClickListener {
            viewModel.openSearch()
        }

        btnClose.setOnClickListener {
            viewModel.closeSearch()
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

    }

    private fun bindState() {
        stateBinder.apply {
            bind(MapViewState::isVenuesLoading) {
                loadingView.showLoading(it)
            }

            bind(MapViewState::isSearchShown, this@VenuesMapFragment::showSearch)

            bindNullable(MapViewState::error, toastDelegate::onChanged)

            bind(MapViewState::places, this@VenuesMapFragment::renderPlaces)

            bindNullable(MapViewState::userLatLng) {
                it?.let(this@VenuesMapFragment::renderLocation)
            }
        }
    }

    private fun renderPlaces(items: Collection<PreviewPlace>) {
        googleMap?.apply {
            clear()

            val pairs = markerMapper.mapFrom(items)
            val markerVenueViewDataMap = HashMap<String, PreviewPlace>()

            for (pair in pairs) {
                val id = addMarker(pair.first).id
                markerVenueViewDataMap[id] = pair.second
            }

            viewModel.venuesHolder.clear()
            viewModel.venuesHolder.putAll(markerVenueViewDataMap)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_CHECK_LOCATION_SETTINGS && resultCode == RESULT_OK) {
            viewModel.requestUserLocation()
        }
    }

    @SuppressLint("MissingPermission")
    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun onShowCurrentLocationClicked() {
        viewModel.requestUserLocation()
    }

    @SuppressLint("MissingPermission")
    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun initLocationSettings(googleMap: GoogleMap) {
        reactiveLocationSettings.checkSettings()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onComplete = {
                            viewModel.requestUserLocation()
                            googleMap.apply {
                                uiSettings.isMyLocationButtonEnabled = false
                                isMyLocationEnabled = true
                            }
                        },
                        onError = {
                            if (it is ResolvableApiException) {
                                it.startResolutionForResult(activity, REQUEST_CODE_CHECK_LOCATION_SETTINGS)
                            } else {
                                toastDelegate.showError(getString(R.string.unexpected_error))
                            }
                        }
                ).disposeOnDestroy(autoDisposable)
    }

    override fun sectionSelected(section: Section) {
        viewModel.getRecommendedVenues(section)
    }

    override fun inject() {
        PlacesMapFragmentComponent.Injector.inject(this)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        lifeCycleAwareMap.onLowMemory()
    }

    override fun onBackPressed(): Boolean {
        if (venueViewFacade.dismiss()) {
            return true
        }

        if (searchAppBar.isVisible) {
            viewModel.closeSearch()
            return true
        }

        return false
    }

    private fun showSearch(shouldShow: Boolean) {
        searchAppBar.gone(!shouldShow)
        fabSearch.showExt(shouldShow)
    }

    private fun renderLocation(userLocation: home.oleg.placesnearme.coredomain.models.LatLng) {
        val latLng = LatLng(userLocation.lat, userLocation.lng)
        googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, USER_LOCATION_ZOOM.toFloat()))
    }

    companion object {
        private const val KEY_SEARCH_VISIBILITY = "key_search_visibility"
        private const val KEY_SEARCH_SELECTED_ITEM_POSITION = "key_search_selected_item_position"
        private const val USER_LOCATION_ZOOM = 14
    }
}
