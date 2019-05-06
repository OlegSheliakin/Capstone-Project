package home.oleg.placesnearme.favoritevenues.presentation

import com.smedialink.common.base.BaseViewModel
import home.oleg.placesnearme.coredomain.repositories.FavoriteVenuesRepository
import home.oleg.placesnearme.corepresentation.recyclerview.VenueViewItem
import home.oleg.placesnearme.corepresentation.viewdata.VenueViewData
import home.oleg.placesnearme.feature_add_favorite.presentation.CreateFavoriteViewModelDelegate
import home.oleg.placesnearme.feature_add_favorite.presentation.UpdateFavorite
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

/**
 * Created by Oleg Sheliakin on 04.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */

class FavoritePlacesViewModel(
        createFavoriteViewModelDelegate: CreateFavoriteViewModelDelegate,
        favoriteVenuesRepository: FavoriteVenuesRepository)
    : BaseViewModel<List<VenueViewItem>>(), UpdateFavorite by createFavoriteViewModelDelegate {

    init {
        favoriteVenuesRepository.observeFavorites()
                .map(VenueViewData.Companion::mapFrom)
                .map(VenueViewItem.Companion::map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onNext = { stateInternal.value = it },
                        onError = Throwable::printStackTrace).autoDispose()
    }

}
