package home.oleg.placesnearme.coredomain.repositories

import home.oleg.placesnearme.coredomain.models.Place
import io.reactivex.Completable
import io.reactivex.Flowable

interface VenueHistoryRepository {

    val history: Flowable<List<Place>>

    fun dropCheckIns(): Completable

    fun isHereNow(venueId: String): Flowable<Boolean>

    fun checkIn(detailedVenue: Place): Completable

}
