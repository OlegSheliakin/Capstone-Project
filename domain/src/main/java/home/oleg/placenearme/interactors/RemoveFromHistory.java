package home.oleg.placenearme.interactors;

import home.oleg.placenearme.repositories.VenueHistoryRepository;
import io.reactivex.Completable;

public class RemoveFromHistory {
    private final VenueHistoryRepository venueHistoryRepository;

    public RemoveFromHistory(VenueHistoryRepository venueHistoryRepository) {
        this.venueHistoryRepository = venueHistoryRepository;
    }

    public Completable execute(String venueId) {
        return venueHistoryRepository.remove(venueId);
    }
}
