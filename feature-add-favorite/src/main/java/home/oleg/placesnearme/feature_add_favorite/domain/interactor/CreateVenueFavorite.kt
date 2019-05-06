package home.oleg.placesnearme.feature_add_favorite.domain.interactor

import home.oleg.placesnearme.coredomain.models.Place
import home.oleg.placesnearme.coredomain.repositories.FavoriteVenuesRepository
import io.reactivex.Single
import javax.inject.Inject

class CreateVenueFavorite @Inject constructor(
        private val favoriteVenuesRepository: FavoriteVenuesRepository) {

    fun execute(detailedVenue: Place): Single<Boolean> {
        return if (detailedVenue.isFavorite) {
            favoriteVenuesRepository.deleteFromFavorite(detailedVenue)
                    .andThen(Single.just(false))
        } else {
            favoriteVenuesRepository.addToFavorite(detailedVenue)
                    .andThen(Single.just(true))
        }
    }
}
