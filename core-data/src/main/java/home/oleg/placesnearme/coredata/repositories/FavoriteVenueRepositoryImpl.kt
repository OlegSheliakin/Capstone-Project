package home.oleg.placesnearme.coredata.repositories

import home.oleg.placesnearme.coredata.dao.PlacesDao
import home.oleg.placesnearme.coredata.mapper.DetailedVenueMapper
import home.oleg.placesnearme.coredomain.models.Place
import home.oleg.placesnearme.coredomain.repositories.FavoriteVenuesRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class FavoriteVenueRepositoryImpl @Inject constructor(private val dao: PlacesDao) : FavoriteVenuesRepository {

    override fun observeFavorites(): Flowable<List<Place>> {
        return dao.streamFavorites().map { DetailedVenueMapper.map(it) }
    }

    override fun addToFavorite(venue: Place): Completable {
        return Completable.fromAction {
            val placeAndPhotos = DetailedVenueMapper.map(venue)
            placeAndPhotos.venue = placeAndPhotos.venue.copy(isFavorite = true)
            dao.insertOrReplace(placeAndPhotos.venue, placeAndPhotos.photos)
        }
    }

    override fun deleteFromFavorite(venue: Place): Completable {
        return Completable.fromAction {
            val detailedVenueDbEntity = DetailedVenueMapper.map(venue).venue
            dao.update(detailedVenueDbEntity.copy(isFavorite = false))
        }
    }

}
