package home.oleg.placesnearme.baseadd

import com.smedialink.common.base.MessageEvent
import home.oleg.placesnearme.coredomain.models.Place
import home.oleg.placesnearme.corepresentation.viewdata.PlaceViewData
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Oleg Sheliakin on 13.11.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */

abstract class BaseAddViewModelDelegate(
        private val useCase: (Place) -> Single<Boolean>,
        private val mapper: (Boolean) -> MessageEvent
) {

    fun manage(venue: PlaceViewData): Single<MessageEvent> {
        return Single.fromCallable { venue.mapToDetailVenue() }
                .flatMap<Boolean> { useCase(it) }
                .map { mapper(it) }
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
    }
}
