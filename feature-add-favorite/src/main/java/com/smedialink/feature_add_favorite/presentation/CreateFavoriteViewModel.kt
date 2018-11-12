package com.smedialink.feature_add_favorite.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.smedialink.feature_add_favorite.domain.interactor.CreateVenueFavorite
import home.oleg.placesnearme.core_presentation.base.MessageEvent
import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class CreateFavoriteViewModel(
        private val addRemoveVenueFavorite: CreateVenueFavorite,
        private val mapper: FavoriteMessageEventMapper) : ViewModel() {

    val state = MutableLiveData<MessageEvent>()

    private var disposable: Disposable? = null

    fun manageFavorite(venueViewData: VenueViewData?) {
        disposable = addRemoveVenueFavorite.execute(venueViewData!!.mapToDetailVenue())
                .map(mapper::map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ state.setValue(it) }, { it.printStackTrace() })
    }
    
}
