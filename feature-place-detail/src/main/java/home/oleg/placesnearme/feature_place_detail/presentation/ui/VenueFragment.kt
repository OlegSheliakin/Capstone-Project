package home.oleg.placesnearme.feature_place_detail.presentation.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.oleg.placesnearme.feature_place_detail.R
import com.olegsheliakin.statebinder.StateBinder
import com.smedialink.common.base.BaseFragment
import com.smedialink.common.base.LiveEvent
import com.smedialink.common.propertydelegate.bundle
import home.oleg.placesnearme.corepresentation.delegate.ToastDelegate
import home.oleg.placesnearme.feature_place_detail.di.VenueDetailComponent
import home.oleg.placesnearme.feature_place_detail.presentation.PlaceViewState
import home.oleg.placesnearme.feature_place_detail.presentation.VenueViewFacade
import javax.inject.Inject

class VenueFragment
@SuppressLint("ValidFragment") constructor(private val toastDelegate: ToastDelegate)
    : BaseFragment(), Observer<LiveEvent> by toastDelegate {

    override val layoutRes: Int = R.layout.fragment_venue

    /*  @Inject
      lateinit var viewModel: VenueViewModel
  */
    @Inject
    lateinit var venueViewFacade: VenueViewFacade

    private val stateBinder = StateBinder.create<PlaceViewState>()

    private var venueId: String? by bundle()
    /*private var mergedAppBarLayoutBehavior: MergedAppBarLayoutBehavior? = null
    private var behavior: GoogleMapsBottomSheetBehavior<*>? = null*/

    constructor() : this(ToastDelegate())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        venueViewFacade.onCreateView(view, viewLifecycleOwner)
        venueId?.also(venueViewFacade::open)
        /* initBehavior(view)

         fabCheckInButton.setOnClickListener {
             viewModel.updateCheckIn()
         }

         fabFavoriteButton.setOnClickListener {
             viewModel.updateFavorite()
         }

         viewModel.state.observeExt(this, stateBinder::newState)

         venueId?.let(this::open)*/
    }

    override fun inject() {
        VenueDetailComponent.Injector.inject(this)
    }

    fun open(venueId: String) {
        venueViewFacade.open(venueId)
    }

    fun dismiss(): Boolean = venueViewFacade.dismiss()


    /*  private fun initBehavior(view: View) {
          toastDelegate.attach(view.context)

          behavior = GoogleMapsBottomSheetBehavior.from(view.nestedScrollView)
          behavior?.parallax = ivVenuePhoto
          behavior?.skipCollapsed = true

          mergedAppBarLayoutBehavior = MergedAppBarLayoutBehavior.from(view.mergedappbarlayout)

          mergedAppBarLayoutBehavior?.setNavigationOnClickListener {
              behavior?.setState(GoogleMapsBottomSheetBehavior.STATE_HIDDEN)
          }
      }*/

    /* private fun show(place: PlaceViewData) {
         venueView.show(place)

         val url = place.bestPhoto?.fullSizeUrl
         ImageLoader.loadImage(ivVenuePhoto, url)

         mergedAppBarLayoutBehavior?.setToolbarTitle(place.title)
         fabFavoriteButton.isSelected = place.isFavorite
         fabCheckInButton.isSelected = place.isHere
     }*/

}
