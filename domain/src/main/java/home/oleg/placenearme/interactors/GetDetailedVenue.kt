package home.oleg.placenearme.interactors

import home.oleg.placenearme.models.DetailedVenue
import home.oleg.placenearme.repositories.DetailedVenueRepository
import home.oleg.placenearme.repositories.VenueHistoryRepository
import io.reactivex.Flowable
import io.reactivex.functions.BiFunction

class GetDetailedVenue(private val detailedVenueRepository: DetailedVenueRepository,
                       private val venueHistoryRepository: VenueHistoryRepository) {

    fun getDetailedVenue(id: String): Flowable<DetailedVenue> {
        return get(id, detailedVenueRepository.getDetailedVenueById(id))
    }

    fun getCachedDetailVenue(id: String): Flowable<DetailedVenue> {
        return get(id, detailedVenueRepository.stream(id))
    }

    private operator fun get(id: String, detailedVenueFlowable: Flowable<DetailedVenue>): Flowable<DetailedVenue> {
        return Flowable.combineLatest(venueHistoryRepository.isHereNow(id), detailedVenueFlowable,
                BiFunction { b, v ->
                    v.copy(isHereNow = b)
                })
    }

}
