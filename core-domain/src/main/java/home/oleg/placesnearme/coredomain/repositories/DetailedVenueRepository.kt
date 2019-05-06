package home.oleg.placesnearme.coredomain.repositories

import home.oleg.placesnearme.coredomain.models.Place
import io.reactivex.Flowable
import io.reactivex.Single

interface DetailedVenueRepository {

    fun getDetailedVenueById(venueId: String): Flowable<Place>

    fun stream(venueId: String): Flowable<Place>

    fun fetch(venueId: String): Single<Place>

}
