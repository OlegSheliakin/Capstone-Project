package home.oleg.placenearme.repositories

import home.oleg.placenearme.models.DetailedVenue
import io.reactivex.Completable
import io.reactivex.Flowable

interface FavoriteVenuesRepository {

    fun observeFavorites(): Flowable<List<DetailedVenue>>

    fun addToFavorite(venue: DetailedVenue): Completable

    fun deleteFromFavorite(venue: DetailedVenue): Completable

}
