package home.oleg.feature_favorite_venues.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import home.oleg.placenearme.models.DetailedVenue
import home.oleg.placesnearme.core_presentation.base.BaseViewModel
import home.oleg.placesnearme.core_presentation.recyclerview.VenueViewItem
import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

/**
 * Created by Oleg Sheliakin on 04.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
class FavoritePlacesViewModel(
        private val observeFavoriteVenues: () -> Flowable<List<DetailedVenue>>) : BaseViewModel() {

    private val stateInternal: MutableLiveData<List<VenueViewItem>> by lazy {
        return@lazy MutableLiveData<List<VenueViewItem>>()
    }

    val state: LiveData<List<VenueViewItem>> by lazy {
        disposables += observeFavoriteVenues()
                .map { VenueViewData.mapFrom(it) }
                .map { VenueViewItem.map(it) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onNext = { it -> stateInternal.value = it },
                        onError = Throwable::printStackTrace)

        return@lazy stateInternal
    }

}
