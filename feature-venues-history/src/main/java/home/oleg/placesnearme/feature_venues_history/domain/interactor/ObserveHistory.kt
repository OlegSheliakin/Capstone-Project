package home.oleg.placesnearme.feature_venues_history.domain.interactor

import home.oleg.placenearme.interactors.EvaluateDistance
import home.oleg.placenearme.models.DetailedVenue
import home.oleg.placenearme.repositories.VenueHistoryRepository
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * Created by Oleg Sheliakin on 13.11.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */

class ObserveHistory @Inject constructor(
        private val evaluateDistance: EvaluateDistance,
        private val venueHistoryRepository: VenueHistoryRepository) {

    operator fun invoke(): Flowable<List<DetailedVenue>> {
        return venueHistoryRepository.history.flatMapSingle {
            evaluateDistance.evaluateDistance(it)
        }
    }
}