package home.oleg.placenearme.repositories

import home.oleg.placenearme.models.DetailedVenue
import io.reactivex.Completable
import io.reactivex.Flowable

interface VenueHistoryRepository {

    val history: Flowable<List<DetailedVenue>>

    fun checkOutFromCurrent(): Completable

    fun isHereNow(venueId: String): Flowable<Boolean>

    fun checkIn(detailedVenue: DetailedVenue): Completable

    fun checkOut(venueId: String): Completable

    fun remove(venueId: String): Completable

}
