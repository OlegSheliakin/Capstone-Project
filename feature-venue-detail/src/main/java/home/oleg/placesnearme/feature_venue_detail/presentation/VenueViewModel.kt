package home.oleg.placesnearme.feature_venue_detail.presentation

import com.smedialink.common.base.BaseViewModel
import com.smedialink.common.ext.reduceState
import com.smedialink.common.propertydelegate.disposableDelegate
import home.oleg.placesnearme.corepresentation.viewdata.PlaceViewData
import home.oleg.placesnearme.corettools.error_handler.ErrorHandler
import home.oleg.placesnearme.feature_add_favorite.presentation.CreateFavoriteViewModelDelegate
import home.oleg.placesnearme.feature_add_favorite.presentation.UpdateFavorite
import home.oleg.placesnearme.feature_add_history.presentation.viewmodel.CheckInViewModelDelegate
import home.oleg.placesnearme.feature_add_history.presentation.viewmodel.UpdateCheckIn
import home.oleg.placesnearme.feature_venue_detail.domain.GetDetailedVenue
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiConsumer
import io.reactivex.rxkotlin.subscribeBy

/**
 * Created by Oleg Sheliakin on 18.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
class VenueViewModel(
        createFavoriteViewModelDelegate: CreateFavoriteViewModelDelegate,
        checkInViewModelDelegate: CheckInViewModelDelegate,
        private val errorHandler: ErrorHandler,
        private val getDetailedVenue: GetDetailedVenue) : BaseViewModel<PlaceViewState>(),
        UpdateFavorite by createFavoriteViewModelDelegate, UpdateCheckIn by checkInViewModelDelegate {

    private var disposable: Disposable? by disposableDelegate()

    private val venueViewData: PlaceViewData
        get() {
            return stateInternal.value?.place ?: throw IllegalStateException()
        }

    init {
        stateInternal.value = PlaceViewState.initial()
    }

    fun load(venueId: String) {
        disposable = getDetailedVenue(venueId, GetDetailedVenue.Type.STREAM)
                .map { PlaceViewData.mapFrom(it) }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    stateInternal.reduceState {
                        it.copy(isLoading = true)
                    }
                }
                .subscribe(
                        { place ->
                            stateInternal.reduceState {
                                it.copy(isLoading = false, place = place)
                            }
                        },
                        { throwable ->
                            val errorEvent = errorHandler.handle(throwable)
                            stateInternal.reduceState {
                                it.copy(isLoading = false, error = errorEvent)
                            }
                        })
    }

    fun updateCheckIn() {
        update(UpdateType.CHECK_IN)
    }

    fun updateFavorite() {
        update(UpdateType.FAVORITE)
    }

    private fun update(type: UpdateType) {
        when (type) {
            UpdateType.FAVORITE -> updateFavorite(venueViewData)
            UpdateType.CHECK_IN -> updateCheckIn(venueViewData)
        }.subscribeBy(
                onSuccess = { message ->
                    stateInternal.reduceState {
                        it.copy(message = message)
                    }
                }
        ).autoDispose()
    }

    fun cancel() {
        disposable = null
    }

    private enum class UpdateType {
        FAVORITE, CHECK_IN
    }


}
