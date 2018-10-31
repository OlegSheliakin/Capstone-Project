package home.oleg.placesnearme.feature_venues_history

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import home.oleg.placenearme.interactors.EvaluateDistance
import home.oleg.placenearme.models.DetailedVenue
import home.oleg.placesnearme.core_presentation.recyclerview.VenueViewItem
import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData
import home.oleg.placesnearme.feature_venues_history.state.VenuesHistoryState
import home.oleg.placesnearme.mapkeys.ViewModelKey
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Oleg Sheliakin on 04.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
class VenuesHistoryViewModel(
        private val observeHistory: () -> Flowable<List<DetailedVenue>>,
        private val evaluateDistance: EvaluateDistance) : ViewModel() {

    private var disposable: Disposable? = null

    val state: MutableLiveData<List<VenueViewItem>> by lazy {
        disposable = observeHistory()
                .flatMapSingle(evaluateDistance::evaluateDistance)
                .map { VenueViewData.mapFrom(it) }
                .map { VenueViewItem.map(it) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    state.value = it
                }, Throwable::printStackTrace)

        return@lazy MutableLiveData<List<VenueViewItem>>()
    }

}
