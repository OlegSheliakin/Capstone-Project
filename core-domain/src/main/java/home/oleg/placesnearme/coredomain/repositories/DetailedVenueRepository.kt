package home.oleg.placesnearme.coredomain.repositories

import home.oleg.placesnearme.coredomain.models.DetailedVenue
import io.reactivex.Flowable
import io.reactivex.Single

interface DetailedVenueRepository {

    fun getDetailedVenueById(venueId: String): Flowable<DetailedVenue>

    fun stream(venueId: String): Flowable<DetailedVenue>

    fun fetch(venueId: String): Single<DetailedVenue>

}
