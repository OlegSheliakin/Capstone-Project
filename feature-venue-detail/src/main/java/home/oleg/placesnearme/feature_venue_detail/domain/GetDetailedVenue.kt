package home.oleg.placesnearme.feature_venue_detail.domain

import home.oleg.placesnearme.coredomain.interactors.EvaluateDistance
import home.oleg.placesnearme.coredomain.models.Place
import home.oleg.placesnearme.coredomain.repositories.DetailedVenueRepository
import home.oleg.placesnearme.coredomain.repositories.VenueHistoryRepository
import io.reactivex.Flowable
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class GetDetailedVenue @Inject constructor(
        private val evaluateDistance: EvaluateDistance,
        private val detailedVenueRepository: DetailedVenueRepository,
        private val venueHistoryRepository: VenueHistoryRepository) {

    operator fun invoke(id: String, type: Type): Flowable<Place> {
        return when (type) {
            Type.OBSERVE -> get(id, detailedVenueRepository.stream(id))
            Type.UPDATE -> get(id, detailedVenueRepository.getDetailedVenueById(id))
        }.flatMapSingle(evaluateDistance::evaluateDistance)
    }

    private fun get(id: String, detailedVenueFlowable: Flowable<Place>): Flowable<Place> {
        return Flowable.combineLatest(venueHistoryRepository.isHereNow(id), detailedVenueFlowable,
                BiFunction { b, v ->
                    v.copy(isHereNow = b)
                })
    }

    enum class Type {
        OBSERVE, UPDATE
    }

}
