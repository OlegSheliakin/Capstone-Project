package home.oleg.placenearme.repositories

import home.oleg.placenearme.models.Section
import home.oleg.placenearme.models.Venue
import io.reactivex.Single

interface VenueRepository {

    fun getRecommendedBySection(section: Section, requestParams: VenueRequestParams): Single<List<Venue>>

    fun search(query: String, requestParams: VenueRequestParams): Single<List<Venue>>

}
