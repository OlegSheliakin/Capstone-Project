package home.oleg.placesnearme.feature_venues_history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import home.oleg.placenearme.interactors.EvaluateDistance
import home.oleg.placenearme.models.DetailedVenue
import home.oleg.placesnearme.core_presentation.base.BaseViewModel
import home.oleg.placesnearme.core_presentation.recyclerview.VenueViewItem
import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

/**
 * Created by Oleg Sheliakin on 04.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
class VenuesHistoryViewModel(
        private val observeHistory: () -> Flowable<List<DetailedVenue>>,
        private val evaluateDistance: EvaluateDistance) : BaseViewModel() {

    private val srateInternal: MutableLiveData<List<VenueViewItem>> by lazy {
        return@lazy MutableLiveData<List<VenueViewItem>>()
    }

    val state: LiveData<List<VenueViewItem>> by lazy {
        disposables += observeHistory()
                .flatMapSingle(evaluateDistance::evaluateDistance)
                .map { VenueViewData.mapFrom(it) }
                .map { VenueViewItem.map(it) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onNext = { it -> srateInternal.value = it },
                        onError = Throwable::printStackTrace)
        return@lazy srateInternal
    }

}
