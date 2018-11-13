package com.easymedium.olegsheliakin.baseadd

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import home.oleg.placenearme.models.DetailedVenue
import home.oleg.placesnearme.core_presentation.base.MessageEvent
import home.oleg.placesnearme.core_presentation.delegate.disposableDelegate
import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

/**
 * Created by Oleg Sheliakin on 13.11.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */

abstract class BaseAddViewModelDelegate(
        private val useCase: (DetailedVenue) -> Single<Boolean>,
        private val mapper: (Boolean) -> MessageEvent
) {

    private val stateInternal = MutableLiveData<MessageEvent>()

    private var disposable: Disposable? by disposableDelegate()

    val state: LiveData<MessageEvent> = stateInternal

    fun manage(venue: VenueViewData?) {
        if (venue == null) {
            return
        }

        disposable = Single.fromCallable { venue.mapToDetailVenue() }
                .flatMap<Boolean> { useCase(it) }
                .map { mapper(it) }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = stateInternal::setValue,
                        onError = Throwable::printStackTrace)
    }
}