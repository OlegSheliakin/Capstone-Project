package com.smedialink.feature_venue_detail.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.smedialink.feature_add_favorite.presentation.CreateFavoriteViewModel
import com.smedialink.feature_venue_detail.R
import com.smedialink.feature_venue_detail.state.VenueViewState
import com.smedialink.feature_venue_detail.di.VenueDetailComponent
import com.smedialink.feature_venue_detail.viewmodel.VenueViewModel
import home.oleg.coordinator_behavior.GoogleMapsBottomSheetBehavior
import home.oleg.coordinator_behavior.MergedAppBarLayout
import home.oleg.coordinator_behavior.MergedAppBarLayoutBehavior
import home.oleg.feature_add_history.CheckInViewModel
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

class VenueFragment : BaseFragment(), Observer<MessageEvent> {

    private var mergedAppBarLayoutBehavior: MergedAppBarLayoutBehavior? = null
    private var behavior: GoogleMapsBottomSheetBehavior<*>? = null

    @Inject
    lateinit var venueViewModel: VenueViewModel

    @Inject
    lateinit var createFavoriteViewModel: CreateFavoriteViewModel

    @Inject
    lateinit var checkInViewModel: CheckInViewModel

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
            checkInViewModel.manage(venueViewModel.venueViewData)
        }

        fabFavoriteButton.setOnClickListener { _ ->
            createFavoriteViewModel.manageFavorite(venueViewModel.venueViewData)
        }


        observe(venueViewModel.state, this@VenueFragment::render)
        observe(createFavoriteViewModel.state) {

        }
        venueViewModel.state.observe(this, Observer { this.render(it) })
        createFavoriteViewModel.state.observe(this, this)
        checkInViewModel.state.observe(this, this)

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

    override fun onChanged(messageEvent: MessageEvent?) {
        messageEvent?.handle { it ->
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
