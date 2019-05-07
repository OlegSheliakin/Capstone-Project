package home.oleg.placesnearme.coredata.repositories

import home.oleg.placesnearme.coredata.dao.PlacesDao
import home.oleg.placesnearme.coredata.dao.DetailedVenueHistoryDao
import home.oleg.placesnearme.coredata.mapper.DetailedVenueMapper
import home.oleg.placesnearme.coredata.model.DetailedVenueHistory
import home.oleg.placesnearme.coredata.model.DetailedVenueHistoryDbEntity
import home.oleg.placesnearme.coredomain.models.Place
import home.oleg.placesnearme.coredomain.repositories.VenueHistoryRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import java.util.*
import javax.inject.Inject

class VenueHistoryRepositoryImpl @Inject constructor(
        private val detailedVenueWithPhotosDao: PlacesDao,
        private val venueHistoryDao: DetailedVenueHistoryDao) : VenueHistoryRepository {

    override val history: Flowable<List<Place>>
        get() = venueHistoryDao.allHistory().map { this.map(it) }

    override fun checkOutFromCurrent(): Completable {
        return venueHistoryDao.lastCheckIn()
                .flatMapCompletable(this::dropCheckIn)
    }

    override fun isHereNow(venueId: String): Flowable<Boolean> {
        return Flowable.merge(
                Flowable.just(false),
                venueHistoryDao.observeById(venueId)
                        .map { it.isLastCheckIn })
    }

    override fun checkIn(detailedVenue: Place): Completable {
        return Completable.fromAction {
            //always inserts or replaces if exist
            val detailedVenueWithPhotos = DetailedVenueMapper.map(detailedVenue)
            detailedVenueWithPhotosDao.insertOrReplace(detailedVenueWithPhotos.place, detailedVenueWithPhotos.photos)

            //update history
            val historyDbEntity = DetailedVenueHistoryDbEntity(
                    createdAt = System.currentTimeMillis(),
                    isLastCheckIn = true,
                    placeId = detailedVenue.id
            )
            venueHistoryDao.upsert(historyDbEntity)
        }
    }

    override fun checkOut(venueId: String): Completable {
        return venueHistoryDao.getById(venueId).flatMapCompletable { this.dropCheckIn(it) }
    }

    override fun remove(venueId: String): Completable {
        return Completable.fromAction { venueHistoryDao.remove(venueId) }
    }

    private fun dropCheckIn(historyDbEntity: DetailedVenueHistoryDbEntity): Completable {
        return Completable.fromAction {
            venueHistoryDao.upsert(historyDbEntity.copy(isLastCheckIn = false))
        }
    }

    private fun map(detailedVenueHistoryList: Iterable<DetailedVenueHistory>): List<Place> {
        val result = ArrayList<Place>()
        for ((_, detailedVenueWithPhotos) in detailedVenueHistoryList) {
            val detailedVenue = DetailedVenueMapper.map(detailedVenueWithPhotos!!)
            result.add(detailedVenue)
        }

        return result
    }
}
