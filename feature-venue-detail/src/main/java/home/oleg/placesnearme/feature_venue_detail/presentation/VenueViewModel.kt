package home.oleg.placesnearme.feature_venue_detail.presentation

import com.smedialink.common.base.BaseViewModel
import com.smedialink.common.propertydelegate.disposableDelegate
import home.oleg.placesnearme.corepresentation.viewdata.VenueViewData
import home.oleg.placesnearme.corettools.error_handler.ErrorHandler
import home.oleg.placesnearme.feature_add_favorite.presentation.CreateFavoriteViewModelDelegate
import home.oleg.placesnearme.feature_add_favorite.presentation.UpdateFavorite
import home.oleg.placesnearme.feature_add_history.presentation.viewmodel.CheckInViewModelDelegate
import home.oleg.placesnearme.feature_add_history.presentation.viewmodel.UpdateCheckIn
import home.oleg.placesnearme.feature_venue_detail.domain.GetDetailedVenue
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

/**
 * Created by Oleg Sheliakin on 18.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
class VenueViewModel(
        createFavoriteViewModelDelegate: CreateFavoriteViewModelDelegate,
        checkInViewModelDelegate: CheckInViewModelDelegate,
        private val errorHandler: ErrorHandler,
        private val getDetailedVenue: GetDetailedVenue) : BaseViewModel<VenueViewState>(),
        UpdateFavorite by createFavoriteViewModelDelegate, UpdateCheckIn by checkInViewModelDelegate {

    private var disposable: Disposable? by disposableDelegate()

    // private val venueInternal = MutableLiveData<VenueViewData>()
    // val venue: LiveData<VenueViewData> = venueInternal

    private val venueViewData: VenueViewData
        get() {
            return (stateInternal.value as? VenueViewState.Success)?.venue
                    ?: throw IllegalStateException()
        }

    init {
        stateInternal.value = VenueViewState.Initial
    }

    fun load(venueId: String) {
        disposable = getDetailedVenue(venueId, GetDetailedVenue.Type.FETCH)
                .map { VenueViewData.mapFrom(it) }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    stateInternal.value = VenueViewState.Loading
                }
                .subscribe(
                        { venue ->
                            stateInternal.value = VenueViewState.Success(venue)
                        },
                        { throwable ->
                            val errorEvent = errorHandler.handle(throwable)
                            stateInternal.value = VenueViewState.Error(errorEvent)
                        })
    }

    fun updateCheckIn() {
        updateCheckIn(venueViewData)
    }

    fun updateFavorite() {
        updateFavorite(venueViewData)
    }

    fun cancel() {
        disposable = null
    }

}
