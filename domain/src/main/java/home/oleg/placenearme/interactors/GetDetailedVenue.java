package home.oleg.placenearme.interactors;

import home.oleg.placenearme.models.DetailedVenue;
import home.oleg.placenearme.repositories.DetailedVenueRepository;
import home.oleg.placenearme.repositories.VenueHistoryRepository;
import io.reactivex.Flowable;

public class GetDetailedVenue {

    private final DetailedVenueRepository detailedVenueRepository;
    private final VenueHistoryRepository venueHistoryRepository;

    public GetDetailedVenue(DetailedVenueRepository detailedVenueRepository,
                            VenueHistoryRepository venueHistoryRepository) {
        this.detailedVenueRepository = detailedVenueRepository;
        this.venueHistoryRepository = venueHistoryRepository;
    }

    public Flowable<DetailedVenue> getDetailedVenue(String id) {
        return detailedVenueRepository.getDetailedVenueById(id)
                .flatMapSingle(detailedVenue -> venueHistoryRepository.isHereNow(id)
                        .map(isHere -> {
                            detailedVenue.setHereNow(isHere);
                            return detailedVenue;
                        }));
    }

}
