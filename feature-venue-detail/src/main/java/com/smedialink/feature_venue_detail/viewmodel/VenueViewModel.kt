package com.smedialink.feature_venue_detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.smedialink.feature_venue_detail.state.VenueViewState
import home.oleg.placenearme.models.DetailedVenue
import home.oleg.placesnearme.core_presentation.base.BaseViewModel
import home.oleg.placesnearme.core_presentation.error_handler.ErrorHandler
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
        private val errorHandler: ErrorHandler,
        private val observerDetailedVenue: (String) -> Flowable<DetailedVenue>,
        private val observerCachedDetailedVenue: (String) -> Flowable<DetailedVenue>,
        private val evaluateDistance: (DetailedVenue) -> Single<DetailedVenue>) : BaseViewModel() {

    private var disposable: Disposable? = null

    private val stateInternal = MutableLiveData<VenueViewState>()

    val state: LiveData<VenueViewState> = stateInternal

    val venueViewData: VenueViewData
        get() {
            return state.value?.venueViewData ?: throw IllegalStateException()
        }

    fun setVenue(venueId: String) {
        cancel()

        disposable = observerDetailedVenue(venueId)
                .flatMapSingle(evaluateDistance)
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
        cancel()

        disposable = observerCachedDetailedVenue(venueId)
                .flatMapSingle(evaluateDistance)
                .map { VenueViewData.mapFrom(it) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { venue -> changeState { it.copy(venueViewData = venue) } },
                        { throwable ->
                            val errorEvent = errorHandler.handle(throwable)
                            changeState { it.copy(errorEvent = errorEvent) }
                        })
    }

    fun cancel() {
        disposable?.takeUnless { it.isDisposed }?.dispose()
    }

    private fun changeState(function: (VenueViewState) -> VenueViewState) {
        stateInternal.value?.let {
            stateInternal.value = function(it)
        }
    }

}
