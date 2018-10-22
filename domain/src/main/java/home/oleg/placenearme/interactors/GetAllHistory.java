package home.oleg.placenearme.interactors;

import java.util.List;

import home.oleg.placenearme.models.DetailedVenue;
import home.oleg.placenearme.repositories.VenueHistoryRepository;
import io.reactivex.Flowable;

public final class GetAllHistory {

    private final VenueHistoryRepository venueHistoryRepository;

    public GetAllHistory(VenueHistoryRepository venueHistoryRepository) {
        this.venueHistoryRepository = venueHistoryRepository;
    }

    public Flowable<List<DetailedVenue>> getAllHistory() {
        return venueHistoryRepository.getHistory();
    }
}
