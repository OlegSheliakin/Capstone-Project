package com.smedialink.feature_add_favorite.domain.interactor

import home.oleg.placenearme.models.DetailedVenue
import home.oleg.placenearme.repositories.FavoriteVenuesRepository
import io.reactivex.Single
import javax.inject.Inject

class CreateVenueFavorite @Inject constructor(
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
