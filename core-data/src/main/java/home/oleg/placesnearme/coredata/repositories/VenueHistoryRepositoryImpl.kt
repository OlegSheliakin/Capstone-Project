package home.oleg.placesnearme.coredata.repositories

import android.util.Log
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
        private val venueHistoryDao: DetailedVenueHistoryDao) : VenueHistoryRepository {

    override val history: Flowable<List<Place>>
        get() = venueHistoryDao.allHistory().map { this.map(it) }

    override fun dropCheckIns(): Completable = Completable.fromAction {
        venueHistoryDao.dropCheckIns()
    }

    override fun isHereNow(venueId: String): Flowable<Boolean> {
        return venueHistoryDao.observeById(venueId)
                .map(DetailedVenueHistoryDbEntity::isLastCheckIn).startWith(false)
                .doOnNext {
                    Log.d("TAG", "isherenow $it")
                }
    }

    override fun checkIn(detailedVenue: Place): Completable {
        return Completable.fromAction {
            venueHistoryDao.checkIn(detailedVenue.id)
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
