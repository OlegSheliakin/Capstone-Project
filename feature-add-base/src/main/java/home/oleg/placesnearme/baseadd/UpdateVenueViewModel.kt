package home.oleg.placesnearme.baseadd

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.smedialink.common.base.MessageEvent
import com.smedialink.common.propertydelegate.disposableDelegate
import home.oleg.placesnearme.corepresentation.viewdata.VenueViewData
import home.oleg.placesnearme.coredomain.models.Place
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class UpdateVenueViewModel(
        private val updateVenueFunc: (Place) -> Single<Boolean>,
        private val mapper: (Boolean) -> MessageEvent
) {

    private val messageEventInternal: MutableLiveData<MessageEvent> = MutableLiveData()

    private var disposable: Disposable? by disposableDelegate()

    val messageEvent: LiveData<MessageEvent> = messageEventInternal

    fun update(venueViewData: VenueViewData) {
        disposable = Single.fromCallable { venueViewData.mapToDetailVenue() }
                .flatMap<Boolean> { updateVenueFunc(it) }
                .map { mapper(it) }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = messageEventInternal::setValue,
                        onError = Throwable::printStackTrace)
    }

}