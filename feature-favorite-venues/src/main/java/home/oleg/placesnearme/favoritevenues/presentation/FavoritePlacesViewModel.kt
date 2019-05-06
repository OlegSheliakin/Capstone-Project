package home.oleg.placesnearme.favoritevenues.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.smedialink.common.base.BaseViewModel
import home.oleg.placesnearme.corepresentation.recyclerview.VenueViewItem
import home.oleg.placesnearme.corepresentation.viewdata.VenueViewData
import home.oleg.placesnearme.coredomain.repositories.FavoriteVenuesRepository
import home.oleg.placesnearme.feature_add_favorite.presentation.CreateFavoriteViewModelDelegate
import home.oleg.placesnearme.feature_add_favorite.presentation.UpdateFavorite
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

/**
 * Created by Oleg Sheliakin on 04.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */

class FavoritePlacesViewModel(
        createFavoriteViewModelDelegate: CreateFavoriteViewModelDelegate,
        private val favoriteVenuesRepository: FavoriteVenuesRepository) : BaseViewModel(),
        UpdateFavorite by createFavoriteViewModelDelegate {

    private val stateInternal: MutableLiveData<List<VenueViewItem>> by lazy {
        return@lazy MutableLiveData<List<VenueViewItem>>()
    }

    val state: LiveData<List<VenueViewItem>> by lazy {
        favoriteVenuesRepository.observeFavorites()
                .map { VenueViewData.mapFrom(it) }
                .map { VenueViewItem.map(it) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onNext = { it -> stateInternal.value = it },
                        onError = Throwable::printStackTrace).autoDispose()

        return@lazy stateInternal
    }

}
