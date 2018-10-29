package home.oleg.feature_add_history.interactor;

import home.oleg.placenearme.models.DetailedVenue;
import home.oleg.placenearme.repositories.VenueHistoryRepository;
import io.reactivex.Single;

public class CheckInOut {

    private final VenueHistoryRepository venueHistoryRepository;

    public CheckInOut(VenueHistoryRepository venueHistoryRepository) {
        this.venueHistoryRepository = venueHistoryRepository;
    }

    public Single<Boolean> execute(DetailedVenue detailedVenue) {
        if (detailedVenue.isHereNow()) {
            return venueHistoryRepository.checkOut(detailedVenue.getId())
                    .andThen(Single.just(false));
        } else {
            return venueHistoryRepository
                    .checkOutFromCurrent()
                    .andThen(venueHistoryRepository.checkIn(detailedVenue))
                    .andThen(Single.just(true));
        }
    }

}
