package home.oleg.placesnearme.feature_place_detail.presentation

import android.os.Bundle
import android.view.View
import androidx.core.view.doOnLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.olegsheliakin.statebinder.StateBinder
import com.smedialink.common.base.LiveEvent
import com.smedialink.common.ext.observeExt
import home.oleg.coordinator_behavior.BottomSheetAwareFabBehavior
import home.oleg.coordinator_behavior.GoogleMapsBottomSheetBehavior
import home.oleg.coordinator_behavior.MergedAppBarLayoutBehavior
import home.oleg.placesnearme.corepresentation.api.ShowHideBottomBar
import home.oleg.placesnearme.corepresentation.delegate.ToastDelegate
import home.oleg.placesnearme.corepresentation.utils.ImageLoader
import home.oleg.placesnearme.corepresentation.viewdata.PreviewPlace
import home.oleg.placesnearme.corepresentation.viewdata.PlaceViewData
import kotlinx.android.synthetic.main.layout_venue.view.*
import javax.inject.Inject

/**
 * Created by Oleg Sheliakin on 09.10.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
class VenueViewFacade @Inject constructor(
        private val venueViewModel: VenueViewModel,
        private val toastDelegate: ToastDelegate) : Observer<LiveEvent> by toastDelegate {

    private var showHideBottomBarListener: ShowHideBottomBar? = null

    private var mergedAppBarLayoutBehavior: MergedAppBarLayoutBehavior? = null

    private var fabsBehavior: BottomSheetAwareFabBehavior? = null

    private var behavior: GoogleMapsBottomSheetBehavior<*>? = null

    private var behaviorState = GoogleMapsBottomSheetBehavior.STATE_HIDDEN

    private val stateBinder = StateBinder.create<PlaceViewState>()

    private val isShown: Boolean
        get() = behavior?.state != GoogleMapsBottomSheetBehavior.STATE_HIDDEN

    private lateinit var view: View

    fun onCreateView(view: View, lifecycleOwner: LifecycleOwner) {
        this.view = view
        stateBinder.applyCurrentState()
        toastDelegate.attach(view.context)

        view.fabCheckInButton.setOnClickListener { venueViewModel.updateCheckIn() }
        view.fabFavoriteButton.setOnClickListener { venueViewModel.updateFavorite() }

        initBehavior()

        bindState()

        venueViewModel.state.observeExt(lifecycleOwner, stateBinder::newState)
    }

    fun onSaveState(state: Bundle) {
        behaviorState = behavior?.state ?: GoogleMapsBottomSheetBehavior.STATE_HIDDEN
        state.putInt(KEY_STATE_BEHAVIOR, behaviorState)
    }

    fun onRestoreState(state: Bundle) {
        behaviorState = state.getInt(KEY_STATE_BEHAVIOR)
        view.nestedScrollView.doOnLayout {
            behavior?.state = behaviorState
        }
    }

    fun setShowHideBottomBarListener(showHideBottomBarListener: ShowHideBottomBar) {
        this.showHideBottomBarListener = showHideBottomBarListener
    }

    fun setVenue(venueMapViewData: PreviewPlace) {
        view.venueView.clearContent()
        venueViewModel.load(venueMapViewData.id)
        view.venueView.setRetryClickListener { venueViewModel.load(venueMapViewData.id) }
        openBottomIfNeed()
    }

    fun open(venueId: String) {
        behavior?.state = GoogleMapsBottomSheetBehavior.STATE_ANCHORED
        venueViewModel.load(venueId)
    }

    fun dismiss(): Boolean {
        if (isShown) {
            behavior?.state = GoogleMapsBottomSheetBehavior.STATE_HIDDEN
            return true
        }

        return false
    }

    private fun bindState() {
        stateBinder.apply {
            bind(PlaceViewState::isLoading) {
                view.venueView.isLoading = it
            }

            bindNullable(PlaceViewState::place) { placeViewData ->
                placeViewData?.apply(this@VenueViewFacade::showVenue)
            }

            bindNullable(PlaceViewState::error) { errorEvent ->
                val errorText = errorEvent?.errorText
                if(errorText != null) {
                    view.venueView.showError(errorText)
                    fabsBehavior?.setShouldIntercept(false)
                }
            }

            bindNullable(PlaceViewState::message, toastDelegate::onChanged)
        }
    }

    private fun openBottomIfNeed() {
        val state = behavior?.state
        if (state == GoogleMapsBottomSheetBehavior.STATE_HIDDEN) {
            behavior?.state = GoogleMapsBottomSheetBehavior.STATE_COLLAPSED
        }
    }

    private fun initBehavior() {
        behavior = GoogleMapsBottomSheetBehavior.from(view.nestedScrollView)
        behavior?.parallax = view.ivVenuePhoto

        mergedAppBarLayoutBehavior = MergedAppBarLayoutBehavior.from(view.mergedappbarlayout)
        fabsBehavior = BottomSheetAwareFabBehavior.from(view.fabsContainer)

        mergedAppBarLayoutBehavior?.setNavigationOnClickListener { dismiss() }

        behavior?.setBottomSheetCallback(object : GoogleMapsBottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == GoogleMapsBottomSheetBehavior.STATE_HIDDEN) {
                    showHideBottomBarListener?.hideBottomBar()
                    venueViewModel.cancel()
                } else {
                    showHideBottomBarListener?.showBottomBar()
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }
        })
    }

    private fun showVenue(venue: PlaceViewData) {
        mergedAppBarLayoutBehavior?.setToolbarTitle(venue.title)

        val url = venue.bestPhoto?.fullSizeUrl

        ImageLoader.loadImage(view.ivVenuePhoto, url)

        view.venueView.show(venue)

        fabsBehavior?.setShouldIntercept(true)
        view.fabCheckInButton.isSelected = venue.isHere
        view.fabFavoriteButton.isSelected = venue.isFavorite
    }

    companion object {
        private const val KEY_STATE_BEHAVIOR = "key_state_behavior"
    }

}
