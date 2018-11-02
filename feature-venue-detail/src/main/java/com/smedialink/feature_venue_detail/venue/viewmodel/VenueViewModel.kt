package com.smedialink.feature_venue_detail.venue.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.smedialink.feature_venue_detail.state.VenueViewState
import home.oleg.placenearme.models.DetailedVenue
import home.oleg.placesnearme.core_presentation.error_handler.ErrorHandler
import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Oleg Sheliakin on 18.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
class VenueViewModel(
        private val errorHandler: ErrorHandler,
        private val observerDetailedVenue: (String) -> Flowable<DetailedVenue>,
        private val observerCachedDetailedVenue: (String) -> Flowable<DetailedVenue>,
        private val evaluateDistance: (DetailedVenue) -> Single<DetailedVenue>) : ViewModel() {

    private var disposable: Disposable? = null

    var venueViewData: VenueViewData? = null
        private set

    val state = MutableLiveData<VenueViewState>()

    fun setVenue(venueId: String) {
        cancel()

        disposable = observerDetailedVenue(venueId)
                .flatMapSingle(evaluateDistance)
                .map { VenueViewData.mapFrom(it) }
                .doOnNext { data -> this.venueViewData = data }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { _ -> state.setValue(VenueViewState.loading()) }
                .subscribe(
                        { detailedVenue -> state.setValue(VenueViewState.success(detailedVenue)) },
                        { throwable ->
                            val errorEvent = errorHandler.handle(throwable)
                            state.setValue(VenueViewState.error(errorEvent))
                        })
    }

    fun load(venueId: String) {
        cancel()

        disposable = observerCachedDetailedVenue(venueId)
                .flatMapSingle(evaluateDistance)
                .map { VenueViewData.mapFrom(it) }
                .doOnNext { data -> this.venueViewData = data }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { detailedVenue -> state.setValue(VenueViewState.success(detailedVenue)) },
                        { throwable ->
                            val errorEvent = errorHandler.handle(throwable)
                            state.setValue(VenueViewState.error(errorEvent))
                        })
    }

    fun cancel() {
        disposable?.takeUnless { it.isDisposed }?.dispose()
    }
}
