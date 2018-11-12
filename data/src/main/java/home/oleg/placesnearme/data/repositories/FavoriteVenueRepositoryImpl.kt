package home.oleg.placesnearme.data.repositories

import home.oleg.placenearme.models.DetailedVenue
import home.oleg.placenearme.repositories.FavoriteVenuesRepository
import home.oleg.placesnearme.data.dao.DetailedVenueDao
import home.oleg.placesnearme.data.mapper.DetailedVenueMapper
import home.oleg.placesnearme.data.model.DetailedVenueDbEntity
import home.oleg.placesnearme.data.model.DetailedVenueWithPhotos
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
