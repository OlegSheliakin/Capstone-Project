package home.oleg.placesnearme.coredata.repositories

import home.oleg.placesnearme.coredata.dao.DetailedVenueDao
import home.oleg.placesnearme.coredata.mapper.DetailedVenueMapper
import home.oleg.placesnearme.coredomain.models.DetailedVenue
import home.oleg.placesnearme.coredomain.repositories.FavoriteVenuesRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class FavoriteVenueRepositoryImpl @Inject constructor(private val dao: DetailedVenueDao) : FavoriteVenuesRepository {

    override fun observeFavorites(): Flowable<List<DetailedVenue>> {
        return dao.allFavorites.map { DetailedVenueMapper.map(it) }
    }

    override fun addToFavorite(venue: DetailedVenue): Completable {
        return Completable.fromAction {
            val detailedVenueWithPhotos = DetailedVenueMapper.map(venue)
            detailedVenueWithPhotos.venue = detailedVenueWithPhotos.venue.copy(isFavorite = true)

            val oldDetailedVenueDbEntity = dao.getDetailedVenueById(venue.id)
            if (oldDetailedVenueDbEntity != null) {
                dao.update(detailedVenueWithPhotos.venue, detailedVenueWithPhotos.photos)
            } else {
                dao.insert(detailedVenueWithPhotos)
            }
        }
    }

    override fun deleteFromFavorite(venue: DetailedVenue): Completable {
        return Completable.fromAction {
            val (detailedVenueDbEntity) = DetailedVenueMapper.map(venue)
            dao.update(detailedVenueDbEntity.copy(isFavorite = false))
        }
    }

}
