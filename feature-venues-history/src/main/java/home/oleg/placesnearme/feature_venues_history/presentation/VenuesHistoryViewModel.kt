package home.oleg.placesnearme.feature_venues_history.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.smedialink.feature_add_favorite.presentation.CreateFavoriteViewModelDelegate
import com.smedialink.feature_add_favorite.presentation.UpdateFavorite
import home.oleg.placenearme.interactors.EvaluateDistance
import home.oleg.placenearme.models.DetailedVenue
import home.oleg.placenearme.models.Venue
import home.oleg.placesnearme.core_presentation.base.BaseViewModel
import home.oleg.placesnearme.core_presentation.recyclerview.VenueViewItem
import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData
import home.oleg.placesnearme.feature_venues_history.domain.interactor.ObserveHistory
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
        createFavoriteViewModelDelegate: CreateFavoriteViewModelDelegate,
        private val observeHistory: ObserveHistory) : BaseViewModel(), UpdateFavorite by createFavoriteViewModelDelegate {

    private val srateInternal: MutableLiveData<List<VenueViewItem>> by lazy {
        return@lazy MutableLiveData<List<VenueViewItem>>()
    }

    val state: LiveData<List<VenueViewItem>> by lazy {
        disposables += observeHistory()
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
