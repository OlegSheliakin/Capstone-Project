package com.smedialink.feature_venue_detail.presentation

import android.os.Bundle
import android.view.View
import androidx.core.view.doOnLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import home.oleg.coordinator_behavior.GoogleMapsBottomSheetBehavior
import home.oleg.coordinator_behavior.MergedAppBarLayoutBehavior
import home.oleg.placesnearme.core_presentation.ShowHideBottomBarListener
import home.oleg.placesnearme.core_presentation.base.MessageEvent
import home.oleg.placesnearme.core_presentation.delegate.ToastDelegate
import home.oleg.placesnearme.core_presentation.utils.ImageLoader
import home.oleg.placesnearme.core_presentation.viewdata.PreviewVenueViewData
import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData
import kotlinx.android.synthetic.main.layout_venue.view.*
import javax.inject.Inject

/**
 * Created by Oleg Sheliakin on 09.10.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
class VenueViewFacade @Inject constructor(
        private val lifecycleOwner: LifecycleOwner,
        private val venueViewModel: VenueViewModel,
        private val toastDelegate: ToastDelegate) : Observer<MessageEvent> {
    private var showHideBottomBarListener: ShowHideBottomBarListener? = null

    private lateinit var view: View

    private var mergedAppBarLayoutBehavior: MergedAppBarLayoutBehavior? = null
    private var behavior: GoogleMapsBottomSheetBehavior<*>? = null

    private var behaviorState = GoogleMapsBottomSheetBehavior.STATE_HIDDEN

    val isShown: Boolean
        get() = behavior?.state != GoogleMapsBottomSheetBehavior.STATE_HIDDEN

    fun onCreateView(view: View) {
        this.view = view
        toastDelegate.attach(view.context)

        view.fabCheckInButton.setOnClickListener { _ -> venueViewModel.updateCheckIn(venueViewModel.venueViewData) }
        view.fabFavoriteButton.setOnClickListener { _ -> venueViewModel.updateFavorite(venueViewModel.venueViewData) }

        initBehavior()

        venueViewModel.state.observe(lifecycleOwner, Observer<VenueViewState?> { this.render(it) })
        venueViewModel.checkInMesage.observe(lifecycleOwner, this)
        venueViewModel.favoriteMessage.observe(lifecycleOwner, this)
    }

    fun onSaveState(state: Bundle) {
        behaviorState = behavior!!.state
        state.putInt(KEY_STATE_BEHAVIOR, behaviorState)
    }

    fun onRestoreState(state: Bundle) {
        behaviorState = state.getInt(KEY_STATE_BEHAVIOR)
        view.nestedScrollView.doOnLayout {
            behavior?.state = behaviorState
        }
    }

    fun setShowHideBottomBarListener(showHideBottomBarListener: ShowHideBottomBarListener) {
        this.showHideBottomBarListener = showHideBottomBarListener
    }

    fun setVenue(venueMapViewData: PreviewVenueViewData) {
        view.venueView.clearContent()
        venueViewModel.setVenue(venueMapViewData.id)
        view.venueView.setRetryClickListener { venueViewModel.setVenue(venueMapViewData.id) }
        openBottomIfNeed()
    }

    private fun openBottomIfNeed() {
        val state = behavior!!.state
        if (state == GoogleMapsBottomSheetBehavior.STATE_HIDDEN) {
            behavior!!.state = GoogleMapsBottomSheetBehavior.STATE_COLLAPSED
        }
    }

    private fun initBehavior() {
        behavior = GoogleMapsBottomSheetBehavior.from(view.nestedScrollView)
        behavior?.parallax = view.ivVenuePhoto

        mergedAppBarLayoutBehavior = MergedAppBarLayoutBehavior.from(view.mergedappbarlayout)

        mergedAppBarLayoutBehavior?.setNavigationOnClickListener { _ -> behavior?.setState(GoogleMapsBottomSheetBehavior.STATE_HIDDEN) }

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

    fun dismiss() {
        behavior!!.state = GoogleMapsBottomSheetBehavior.STATE_HIDDEN
    }

    override fun onChanged(messageEvent: MessageEvent?) {
        messageEvent?.let { toastDelegate.showSuccess(it.text) }
    }

    private fun render(venueViewState: VenueViewState?) {
        if (venueViewState == null) {
            return
        }

        if (venueViewState.isLoading) {
            view.venueView.showLoading()
            return
        } else if (venueViewState.errorEvent != null) {
            val errorEvent = venueViewState.errorEvent
            view.venueView.showError(errorEvent.errorText)
            return
        }

        if (venueViewState.venueViewData != null) {
            view.venueView.hideLoading()
            showVenue(venueViewState.venueViewData)
        }
    }

    private fun showVenue(venue: VenueViewData) {
        mergedAppBarLayoutBehavior!!.setToolbarTitle(venue.title)

        val url = venue.bestPhoto?.fullSizeUrl

        ImageLoader.loadImage(view.ivVenuePhoto, url)

        view.venueView.show(venue)
        view.fabCheckInButton.isSelected = venue.isFavorite
        view.fabFavoriteButton.isSelected = venue.isHere
    }

    companion object {

        private val KEY_STATE_BEHAVIOR = "key_state_behavior"
    }
}
