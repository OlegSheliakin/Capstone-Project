package com.smedialink.feature_venue_detail.view

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.view.doOnLayout
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.smedialink.feature_add_favorite.presentation.CreateFavoriteViewModel
import com.smedialink.feature_venue_detail.R
import com.smedialink.feature_venue_detail.state.VenueViewState
import com.smedialink.feature_venue_detail.viewmodel.VenueViewModel
import home.oleg.coordinator_behavior.GoogleMapsBottomSheetBehavior
import home.oleg.coordinator_behavior.MergedAppBarLayout
import home.oleg.coordinator_behavior.MergedAppBarLayoutBehavior
import home.oleg.feature_add_history.CheckInViewModel
import home.oleg.placesnearme.core_presentation.ShowHideBottomBarListener
import home.oleg.placesnearme.core_presentation.base.MessageEvent
import home.oleg.placesnearme.core_presentation.delegate.ToastDelegate
import home.oleg.placesnearme.core_presentation.utils.ImageLoader
import home.oleg.placesnearme.core_presentation.viewdata.PreviewVenueViewData
import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData
import javax.inject.Inject

/**
 * Created by Oleg Sheliakin on 09.10.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
class VenueViewFacade @Inject
constructor(
        private val lifecycleOwner: LifecycleOwner,
        private val venueViewModel: VenueViewModel,
        private val createFavoriteViewModel: CreateFavoriteViewModel,
        private val checkInViewModel: CheckInViewModel,
        private val toastDelegate: ToastDelegate) : Observer<MessageEvent> {
    private var showHideBottomBarListener: ShowHideBottomBarListener? = null

    private var venueDetailsView: VenueDetailsView? = null
    private var ivVenuePhoto: ImageView? = null
    private var favoriteButton: FloatingActionButton? = null
    private var fabCheckInButton: FloatingActionButton? = null
    private var mergedAppBarLayoutBehavior: MergedAppBarLayoutBehavior? = null
    private var behavior: GoogleMapsBottomSheetBehavior<*>? = null
    private var nestedScrollView: NestedScrollView? = null

    private var behaviorState = GoogleMapsBottomSheetBehavior.STATE_HIDDEN

    val isShown: Boolean
        get() = behavior!!.state != GoogleMapsBottomSheetBehavior.STATE_HIDDEN

    fun onCreateView(view: View) {
        venueDetailsView = view.findViewById(R.id.venueView)
        ivVenuePhoto = view.findViewById(R.id.ivVenuePhoto)
        toastDelegate.attach(view.context)
        fabCheckInButton = view.findViewById(R.id.fabCheckInButton)
        fabCheckInButton!!.setOnClickListener { _ -> checkInViewModel.manage(venueViewModel.venueViewData) }

        favoriteButton = view.findViewById(R.id.fabFavoriteButton)
        favoriteButton!!.setOnClickListener { _ -> createFavoriteViewModel.manageFavorite(venueViewModel.venueViewData) }

        initBehavior(view)

        venueViewModel.state.observe(lifecycleOwner, Observer<VenueViewState?> { this.render(it) })
        createFavoriteViewModel.state.observe(lifecycleOwner, this)
        checkInViewModel.state.observe(lifecycleOwner, this)
    }

    fun onSaveState(state: Bundle) {
        behaviorState = behavior!!.state
        state.putInt(KEY_STATE_BEHAVIOR, behaviorState)
    }

    fun onRestoreState(state: Bundle) {
        behaviorState = state.getInt(KEY_STATE_BEHAVIOR)
        nestedScrollView?.doOnLayout {
            behavior!!.state = behaviorState
        }
    }

    fun setShowHideBottomBarListener(showHideBottomBarListener: ShowHideBottomBarListener) {
        this.showHideBottomBarListener = showHideBottomBarListener
    }

    fun setVenue(venueMapViewData: PreviewVenueViewData) {
        venueDetailsView?.clearContent()
        venueViewModel.setVenue(venueMapViewData.id)
        venueDetailsView!!.setRetryClickListener { venueViewModel.setVenue(venueMapViewData.id) }
        openBottomIfNeed()
    }

    private fun openBottomIfNeed() {
        val state = behavior!!.state
        if (state == GoogleMapsBottomSheetBehavior.STATE_HIDDEN) {
            behavior!!.state = GoogleMapsBottomSheetBehavior.STATE_COLLAPSED
        }
    }

    private fun initBehavior(view: View) {
        nestedScrollView = view.findViewById(R.id.nestedScrollView)

        behavior = GoogleMapsBottomSheetBehavior.from(nestedScrollView!!)
        behavior!!.parallax = ivVenuePhoto

        val mergedAppBarLayout = view.findViewById<MergedAppBarLayout>(R.id.mergedappbarlayout)
        mergedAppBarLayoutBehavior = MergedAppBarLayoutBehavior.from(mergedAppBarLayout)

        mergedAppBarLayoutBehavior!!.setNavigationOnClickListener { _ -> behavior!!.setState(GoogleMapsBottomSheetBehavior.STATE_HIDDEN) }

        behavior!!.setBottomSheetCallback(object : GoogleMapsBottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == GoogleMapsBottomSheetBehavior.STATE_HIDDEN) {
                    showHideBottomBarListener!!.hideBottomBar()
                    venueViewModel.cancel()
                } else {
                    showHideBottomBarListener!!.showBottomBar()
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
            venueDetailsView!!.showLoading()
            return
        } else if (venueViewState.errorEvent != null) {
            val errorEvent = venueViewState.errorEvent
            venueDetailsView!!.showError(errorEvent.errorText)
            return
        }

        if (venueViewState.venueViewData != null) {
            venueDetailsView!!.hideLoading()
            showVenue(venueViewState.venueViewData)
        }
    }

    private fun showVenue(venue: VenueViewData) {
        mergedAppBarLayoutBehavior!!.setToolbarTitle(venue.title)

        val url = venue.bestPhoto?.fullSizeUrl

        ivVenuePhoto?.let {
            ImageLoader.loadImage(it, url)
        }

        venueDetailsView!!.show(venue)
        favoriteButton!!.isSelected = venue.isFavorite
        fabCheckInButton!!.isSelected = venue.isHere
    }

    companion object {

        private val KEY_STATE_BEHAVIOR = "key_state_behavior"
    }
}
