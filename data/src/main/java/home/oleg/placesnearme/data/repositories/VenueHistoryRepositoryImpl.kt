package home.oleg.placesnearme.data.repositories

import java.util.ArrayList
import java.util.concurrent.TimeUnit

import home.oleg.placenearme.models.DetailedVenue
import home.oleg.placenearme.repositories.VenueHistoryRepository
import home.oleg.placesnearme.data.dao.DetailedVenueDao
import home.oleg.placesnearme.data.dao.DetailedVenueHistoryDao
import home.oleg.placesnearme.data.mapper.DetailedVenueMapper
import home.oleg.placesnearme.data.model.DetailedVenueHistory
import home.oleg.placesnearme.data.model.DetailedVenueHistoryDbEntity
import home.oleg.placesnearme.data.model.DetailedVenueWithPhotos
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class VenueHistoryRepositoryImpl @Inject constructor(
        private val detailedVenueWithPhotosDao: DetailedVenueDao,
        private val venueHistoryDao: DetailedVenueHistoryDao) : VenueHistoryRepository {

    override val history: Flowable<List<DetailedVenue>>
        get() = venueHistoryDao.allHistory.map { this.map(it) }

    override fun checkOutFromCurrent(): Completable {
        return venueHistoryDao.lastCheckIn
                .flatMapCompletable(this::dropCheckIn)
    }

    override fun isHereNow(venueId: String): Flowable<Boolean> {
        return Flowable.merge(
                Flowable.just(false),
                venueHistoryDao.observeById(venueId)
                        .map { it.isLastCheckIn })
    }

    override fun checkIn(detailedVenue: DetailedVenue): Completable {
        return Completable.fromAction {
            //always insert or replace if exist
            val detailedVenueWithPhotos = DetailedVenueMapper.map(detailedVenue)
            detailedVenueWithPhotosDao.insert(detailedVenueWithPhotos)

            val historyDbEntity = DetailedVenueHistoryDbEntity(
                    createdAt = System.currentTimeMillis(),
                    isLastCheckIn = true,
                    venueId = detailedVenue.id
            )
            venueHistoryDao.insert(historyDbEntity)
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
            venueHistoryDao.insert(historyDbEntity.copy(isLastCheckIn = false))
        }
    }

    private fun map(detailedVenueHistoryList: Iterable<DetailedVenueHistory>): List<DetailedVenue> {
        val result = ArrayList<DetailedVenue>()
        for ((_, detailedVenueWithPhotos) in detailedVenueHistoryList) {
            val detailedVenue = DetailedVenueMapper.map(detailedVenueWithPhotos!!)
            result.add(detailedVenue)
        }

        return result
    }
}
