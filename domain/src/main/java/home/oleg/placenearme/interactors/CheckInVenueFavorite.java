package home.oleg.placenearme.interactors;

import home.oleg.placenearme.models.DetailedVenue;
import home.oleg.placenearme.repositories.FavoriteVenuesRepository;
import home.oleg.placenearme.repositories.VenueHistoryRepository;
import io.reactivex.Completable;
import io.reactivex.Single;

public class CheckInVenueFavorite {

    private final VenueHistoryRepository venueHistoryRepository;

    public CheckInVenueFavorite(VenueHistoryRepository venueHistoryRepository) {
        this.venueHistoryRepository = venueHistoryRepository;
    }

    public Completable execute(DetailedVenue detailedVenue) {
        return venueHistoryRepository
                .dropCurrentVenue()
                .andThen(venueHistoryRepository.checkIn(detailedVenue));
    }
}
