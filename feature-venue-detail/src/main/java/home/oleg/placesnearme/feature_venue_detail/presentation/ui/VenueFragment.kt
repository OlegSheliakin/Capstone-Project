package home.oleg.placesnearme.feature_venue_detail.presentation.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.oleg.placesnearme.feature_venue_detail.R
import com.smedialink.common.base.BaseFragment
import com.smedialink.common.base.LiveEvent
import com.smedialink.common.ext.observe
import com.smedialink.common.propertydelegate.bundle
import home.oleg.coordinator_behavior.GoogleMapsBottomSheetBehavior
import home.oleg.coordinator_behavior.MergedAppBarLayoutBehavior
import home.oleg.placesnearme.core_presentation.delegate.ToastDelegate
import home.oleg.placesnearme.core_presentation.utils.ImageLoader
import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData
import home.oleg.placesnearme.feature_venue_detail.di.VenueDetailComponent
import home.oleg.placesnearme.feature_venue_detail.presentation.LocalVenueViewModel
import kotlinx.android.synthetic.main.layout_venue.*
import kotlinx.android.synthetic.main.layout_venue.view.*
import javax.inject.Inject

class VenueFragment
@SuppressLint("ValidFragment") constructor(private val toastDelegate: ToastDelegate)
    : BaseFragment(), Observer<LiveEvent> by toastDelegate {

    @Inject
    lateinit var venueViewModel: LocalVenueViewModel

    override val layoutRes: Int = R.layout.fragment_venue

    private var venueId: String? by bundle()

    private var mergedAppBarLayoutBehavior: MergedAppBarLayoutBehavior? = null
    private var behavior: GoogleMapsBottomSheetBehavior<*>? = null

    constructor() : this(ToastDelegate())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBehavior(view)

        venueView.hideLoading()

        fabCheckInButton.setOnClickListener { _ ->
            venueViewModel.updateCheckIn()
        }

        fabFavoriteButton.setOnClickListener { _ ->
            venueViewModel.updateFavorite()
        }

        venueViewModel.venue.observe(this, ::show)
        venueViewModel.favoriteMessage.observe(this, this)
        venueViewModel.checkInMesage.observe(this, this)

        venueId?.let(this::open)
    }

    override fun inject() {
        VenueDetailComponent.Injector.inject(this)
    }

    fun open(venueId: String) {
        this.venueId = venueId

        behavior?.state = GoogleMapsBottomSheetBehavior.STATE_ANCHORED

        venueViewModel.load(venueId)
    }

    fun dismiss(): Boolean {
        if (behavior?.state != GoogleMapsBottomSheetBehavior.STATE_HIDDEN) {
            behavior?.state = GoogleMapsBottomSheetBehavior.STATE_HIDDEN
            return true
        }

        return false
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

    private fun show(venue: VenueViewData) {
        venueView.show(venue)

        val url = venue.bestPhoto?.fullSizeUrl
        ImageLoader.loadImage(ivVenuePhoto, url)

        mergedAppBarLayoutBehavior?.setToolbarTitle(venue.title)
        fabFavoriteButton.isSelected = venue.isFavorite
        fabCheckInButton.isSelected = venue.isHere
    }

}
