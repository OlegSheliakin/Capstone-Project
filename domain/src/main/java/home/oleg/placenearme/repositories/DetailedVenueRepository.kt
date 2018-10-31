package home.oleg.placenearme.repositories

import home.oleg.placenearme.models.DetailedVenue
import io.reactivex.Flowable
import io.reactivex.Single

interface DetailedVenueRepository {

    fun getDetailedVenueById(venueId: String): Flowable<DetailedVenue>

    fun stream(venueId: String): Flowable<DetailedVenue>

    fun fetch(venueId: String): Single<DetailedVenue>

}
