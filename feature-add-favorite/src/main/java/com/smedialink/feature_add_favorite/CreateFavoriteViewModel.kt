package com.smedialink.feature_add_favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import home.oleg.placenearme.interactors.CreateVenueFavorite
import home.oleg.placesnearme.core_presentation.base.MessageEvent
import home.oleg.placesnearme.core_presentation.provider.ResourceProvider
import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class CreateFavoriteViewModel(
        private val addRemoveVenueFavorite: CreateVenueFavorite,
        private val resourceProvider: ResourceProvider) : ViewModel() {
    val state = MutableLiveData<MessageEvent>()

    private var disposable: Disposable? = null

    fun manageFavorite(venueViewData: VenueViewData?) {
        if (venueViewData == null) return

        disposable = addRemoveVenueFavorite.execute(venueViewData.mapToDetailVenue())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ isFavorite ->
                    val stringsRes: Int
                    if (isFavorite) {
                        stringsRes = R.string.add_favorite_message_success_added
                    } else {
                        stringsRes = R.string.add_favorite_message_success_removed
                    }
                    val messageEvent = MessageEvent(resourceProvider.getString(stringsRes))
                    state.setValue(messageEvent)
                }, { it.printStackTrace() })
    }
}
