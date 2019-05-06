package home.oleg.placesnearme.feature_venue_detail.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.smedialink.common.base.BaseViewModel
import com.smedialink.common.propertydelegate.disposableDelegate
import home.oleg.placesnearme.corepresentation.viewdata.PlaceViewData
import home.oleg.placesnearme.corettools.logger.Logger
import home.oleg.placesnearme.feature_add_favorite.presentation.CreateFavoriteViewModelDelegate
import home.oleg.placesnearme.feature_add_favorite.presentation.UpdateFavorite
import home.oleg.placesnearme.feature_add_history.presentation.viewmodel.CheckInViewModelDelegate
import home.oleg.placesnearme.feature_add_history.presentation.viewmodel.UpdateCheckIn
import home.oleg.placesnearme.feature_venue_detail.domain.GetDetailedVenue
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy

class LocalVenueViewModel(
        createFavoriteViewModelDelegate: CreateFavoriteViewModelDelegate,
        checkInViewModelDelegate: CheckInViewModelDelegate,
        private val logger: Logger,
        private val getDetailedVenue: GetDetailedVenue) : BaseViewModel<PlaceViewData>(),
        UpdateFavorite by createFavoriteViewModelDelegate, UpdateCheckIn by checkInViewModelDelegate {

    private var disposable: Disposable? by disposableDelegate()

    private val venueInternal: MutableLiveData<PlaceViewData> = MutableLiveData()

    val venue: LiveData<PlaceViewData> = venueInternal

    private val venueViewData: PlaceViewData
        get() {
            return venue.value ?: throw IllegalStateException()
        }

    fun load(venueId: String) {
        disposable = getDetailedVenue(venueId, GetDetailedVenue.Type.STREAM)
                .map { PlaceViewData.mapFrom(it) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onNext = venueInternal::setValue,
                        onError = logger::error)
    }

    fun updateCheckIn() {
        updateCheckIn(venueViewData)
    }

    fun updateFavorite() {
        updateFavorite(venueViewData)
    }

}