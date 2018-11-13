package com.smedialink.feature_venue_detail.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.home.olegsheliakin.corettools.error_handler.ErrorHandler
import home.oleg.placenearme.models.DetailedVenue
import home.oleg.placesnearme.core_presentation.base.BaseViewModel
import com.smedialink.feature_add_favorite.presentation.CreateFavoriteViewModelDelegate
import com.smedialink.feature_add_favorite.presentation.UpdateFavorite
import com.smedialink.feature_venue_detail.domain.GetDetailedVenue
import home.oleg.feature_add_history.presentation.viewmodel.CheckInViewModelDelegate
import home.oleg.feature_add_history.presentation.viewmodel.UpdateCheckIn
import home.oleg.placesnearme.core_presentation.delegate.disposableDelegate
import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData
import io.reactivex.Flowable
import io.reactivex.Single
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
        private val getDetailedVenue: GetDetailedVenue) : BaseViewModel(),
        UpdateFavorite by createFavoriteViewModelDelegate, UpdateCheckIn by checkInViewModelDelegate {

    private var disposable: Disposable? by disposableDelegate()

    private val stateInternal = MutableLiveData<VenueViewState>()

    val state: LiveData<VenueViewState> = stateInternal

    val venueViewData: VenueViewData
        get() {
            return state.value?.venueViewData ?: throw IllegalStateException()
        }

    fun setVenue(venueId: String) {
        disposable = getDetailedVenue(venueId, GetDetailedVenue.Type.FETCH)
                .map { VenueViewData.mapFrom(it) }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { _ -> changeState { it.copy(isLoading = true) } }
                .subscribe(
                        { venue ->
                            changeState {
                                it.copy(venueViewData = venue, isLoading = false)
                            }
                        },
                        { throwable ->
                            val errorEvent = errorHandler.handle(throwable)
                            changeState { it.copy(errorEvent = errorEvent, isLoading = false) }
                        })
    }

    fun load(venueId: String) {
        disposable = getDetailedVenue.invoke(venueId, GetDetailedVenue.Type.FETCH)
                .map { VenueViewData.mapFrom(it) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { venue -> changeState { it.copy(venueViewData = venue) } },
                        { throwable ->
                            val errorEvent = errorHandler.handle(throwable)
                            changeState { it.copy(errorEvent = errorEvent) }
                        })
    }

    private fun changeState(function: (VenueViewState) -> VenueViewState) {
        stateInternal.value?.let {
            stateInternal.value = function(it)
        }
    }

    fun cancel() {
        disposable = null
    }

}
