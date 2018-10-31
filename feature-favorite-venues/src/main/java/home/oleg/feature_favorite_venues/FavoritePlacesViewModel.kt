package home.oleg.feature_favorite_venues

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import home.oleg.placenearme.models.DetailedVenue
import home.oleg.placesnearme.core_presentation.recyclerview.VenueViewItem
import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Oleg Sheliakin on 04.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
class FavoritePlacesViewModel(
        private val observeFavoriteVenues: () -> Flowable<List<DetailedVenue>>) : ViewModel() {

    private var disposable: Disposable? = null

    val state: MutableLiveData<List<VenueViewItem>> by lazy {
        disposable = observeFavoriteVenues()
                .map { VenueViewData.mapFrom(it) }
                .map { VenueViewItem.map(it) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ it ->
                    state.value = it
                }, { throwable -> throwable.printStackTrace() })

        return@lazy MutableLiveData<List<VenueViewItem>>()
    }

}
