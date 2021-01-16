package home.oleg.placesnearme.feature_place_detail.domain

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

    operator fun invoke(id: String): Flowable<Place> {
        return Flowable.combineLatest(
                venueHistoryRepository.isHereNow(id),
                detailedVenueRepository.getPlaceById(id),
                BiFunction<Boolean, Place, Place> { b, v ->
                    v.copy(isHereNow = b)
                })
                .flatMapSingle(evaluateDistance::evaluateDistance)
    }

}
