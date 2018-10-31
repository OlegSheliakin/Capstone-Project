package home.oleg.placenearme.interactors

import home.oleg.placenearme.models.DetailedVenue
import home.oleg.placenearme.repositories.FavoriteVenuesRepository
import io.reactivex.Single

class CreateVenueFavorite(
        private val favoriteVenuesRepository: FavoriteVenuesRepository) {

    fun execute(detailedVenue: DetailedVenue): Single<Boolean> {
        return if (detailedVenue.isFavorite) {
            favoriteVenuesRepository.deleteFromFavorite(detailedVenue)
                    .andThen(Single.just(false))
        } else {
            favoriteVenuesRepository.addToFavorite(detailedVenue)
                    .andThen(Single.just(true))
        }
    }
}
