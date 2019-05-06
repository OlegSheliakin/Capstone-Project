package home.oleg.placesnearme.feature_add_history.domain.interactor

import home.oleg.placesnearme.coredomain.models.Place
import home.oleg.placesnearme.coredomain.repositories.VenueHistoryRepository
import io.reactivex.Single
import javax.inject.Inject

class CheckInOut @Inject constructor(private val venueHistoryRepository: VenueHistoryRepository) {

    fun execute(detailedVenue: Place): Single<Boolean> {
        return if (detailedVenue.isHereNow) {
            venueHistoryRepository.checkOut(detailedVenue.id)
                    .andThen(Single.just(false))
        } else {
            venueHistoryRepository
                    .checkOutFromCurrent()
                    .andThen(venueHistoryRepository.checkIn(detailedVenue))
                    .andThen(Single.just(true))
        }
    }

}
