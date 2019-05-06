package home.oleg.placesnearme.coredomain.repositories

import home.oleg.placesnearme.coredomain.models.Place
import io.reactivex.Completable
import io.reactivex.Flowable

interface FavoriteVenuesRepository {

    fun observeFavorites(): Flowable<List<Place>>

    fun addToFavorite(venue: Place): Completable

    fun deleteFromFavorite(venue: Place): Completable

}
