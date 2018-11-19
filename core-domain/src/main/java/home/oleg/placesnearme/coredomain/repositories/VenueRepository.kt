package home.oleg.placesnearme.coredomain.repositories

import home.oleg.placesnearme.coredomain.models.Section
import home.oleg.placesnearme.coredomain.models.Venue
import io.reactivex.Single

interface VenueRepository {

    fun getRecommendedBySection(section: Section, requestParams: VenueRequestParams): Single<List<Venue>>

    fun search(query: String, requestParams: VenueRequestParams): Single<List<Venue>>

}
