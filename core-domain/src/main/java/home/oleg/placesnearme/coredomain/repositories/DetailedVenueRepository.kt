package home.oleg.placesnearme.coredomain.repositories

import home.oleg.placesnearme.coredomain.models.Place
import io.reactivex.Flowable
import io.reactivex.Single

interface DetailedVenueRepository {

    fun getPlaceById(placeId: String): Flowable<Place>

}
