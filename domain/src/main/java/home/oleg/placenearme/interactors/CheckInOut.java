package home.oleg.placenearme.interactors;

import home.oleg.placenearme.models.DetailedVenue;
import home.oleg.placenearme.repositories.VenueHistoryRepository;
import io.reactivex.Completable;

public class CheckInOut {

    private final VenueHistoryRepository venueHistoryRepository;

    public CheckInOut(VenueHistoryRepository venueHistoryRepository) {
        this.venueHistoryRepository = venueHistoryRepository;
    }

    public Completable execute(DetailedVenue detailedVenue) {
        if (detailedVenue.isHereNow()) {
            return venueHistoryRepository.checkOut(detailedVenue.getId());
        } else {
            return venueHistoryRepository
                    .checkOutFromCurrent()
                    .andThen(venueHistoryRepository.checkIn(detailedVenue));
        }
    }

}
