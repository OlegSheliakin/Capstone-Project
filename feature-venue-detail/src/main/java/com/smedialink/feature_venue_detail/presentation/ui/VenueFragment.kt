package com.smedialink.feature_venue_detail.presentation.ui

import android.os.Bundle
import android.view.View
import com.smedialink.feature_venue_detail.R
import com.smedialink.feature_venue_detail.presentation.VenueViewState
import com.smedialink.feature_venue_detail.di.VenueDetailComponent
import com.smedialink.feature_venue_detail.presentation.VenueViewModel
import home.oleg.coordinator_behavior.GoogleMapsBottomSheetBehavior
import home.oleg.coordinator_behavior.MergedAppBarLayoutBehavior
import home.oleg.placesnearme.core_presentation.base.BaseFragment
import home.oleg.placesnearme.core_presentation.base.MessageEvent
import home.oleg.placesnearme.core_presentation.base.handle
import home.oleg.placesnearme.core_presentation.delegate.ToastDelegate
import home.oleg.placesnearme.core_presentation.extensions.observe
import home.oleg.placesnearme.core_presentation.utils.ImageLoader
import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData
import kotlinx.android.synthetic.main.layout_venue.*
import kotlinx.android.synthetic.main.layout_venue.view.*
import javax.inject.Inject

class VenueFragment : BaseFragment() {

    private var mergedAppBarLayoutBehavior: MergedAppBarLayoutBehavior? = null
    private var behavior: GoogleMapsBottomSheetBehavior<*>? = null

    @Inject
    lateinit var venueViewModel: VenueViewModel

    @Inject
    lateinit var toastDelegate: ToastDelegate

    override val layoutRes: Int = R.layout.fragment_venue

    val isShown: Boolean
        get() = behavior?.state != GoogleMapsBottomSheetBehavior.STATE_HIDDEN

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBehavior(view)

        venueView.hideLoading()

        fabCheckInButton.setOnClickListener { _ ->
            venueViewModel.updateCheckIn(venueViewModel.venueViewData)
        }

        fabFavoriteButton.setOnClickListener { _ ->
            venueViewModel.updateCheckIn(venueViewModel.venueViewData)
        }


        observe(venueViewModel.state, this@VenueFragment::render)
        observe(venueViewModel.favoriteMessage, this@VenueFragment::handleMessageEvent)
        observe(venueViewModel.checkInMesage, this@VenueFragment::handleMessageEvent)

        val bundle = arguments
        if (bundle != null && bundle.containsKey(KEY_VENUE_ID)) {
            val venueId = bundle.getString(KEY_VENUE_ID)
            venueViewModel.load(venueId!!)
        }
    }

    fun showVenue(venueId: String) {
        val bundle = Bundle()
        bundle.putString(KEY_VENUE_ID, venueId)
        arguments = bundle
        behavior?.state = GoogleMapsBottomSheetBehavior.STATE_ANCHORED

        venueViewModel.load(venueId)
    }

    override fun inject() {
        VenueDetailComponent.Injector.inject(this)
    }

    private fun initBehavior(view: View) {
        toastDelegate.attach(view.context)

        behavior = GoogleMapsBottomSheetBehavior.from(view.nestedScrollView)
        behavior?.parallax = ivVenuePhoto
        behavior?.skipCollapsed = true

        mergedAppBarLayoutBehavior = MergedAppBarLayoutBehavior.from(view.mergedappbarlayout)

        mergedAppBarLayoutBehavior?.setNavigationOnClickListener {
            behavior?.setState(GoogleMapsBottomSheetBehavior.STATE_HIDDEN)
        }
    }

    fun dismiss() {
        behavior?.state = GoogleMapsBottomSheetBehavior.STATE_HIDDEN
    }

    private fun handleMessageEvent(messageEvent: MessageEvent) {
        messageEvent.handle { it ->
            toastDelegate.showSuccess(it.text)
        }
    }

    private fun render(venueViewState: VenueViewState?) {
        if (venueViewState == null) return

        if (venueViewState.venueViewData != null) {
            show(venueViewState.venueViewData)
        }
    }

    private fun show(venue: VenueViewData) {
        venueView.show(venue)

        val url = venue.bestPhoto?.fullSizeUrl

        ivVenuePhoto?.let {
            ImageLoader.loadImage(it, url)
        }

        mergedAppBarLayoutBehavior?.setToolbarTitle(venue.title)
        fabFavoriteButton.isSelected = venue.isFavorite
        fabCheckInButton.isSelected = venue.isHere
    }

    companion object {
        private val KEY_VENUE_ID = "key_venue_id"
    }
}
