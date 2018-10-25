package home.oleg.placenearme.interactors;

import home.oleg.placenearme.models.DetailedVenue;
import home.oleg.placenearme.repositories.DetailedVenueRepository;
import home.oleg.placenearme.repositories.VenueHistoryRepository;
import io.reactivex.Flowable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;

public class GetDetailedVenue {

    private final DetailedVenueRepository detailedVenueRepository;
    private final VenueHistoryRepository venueHistoryRepository;

    public GetDetailedVenue(DetailedVenueRepository detailedVenueRepository,
                            VenueHistoryRepository venueHistoryRepository) {
        this.detailedVenueRepository = detailedVenueRepository;
        this.venueHistoryRepository = venueHistoryRepository;
    }

    public Flowable<DetailedVenue> getDetailedVenue(String id) {
        return get(id, detailedVenueRepository.getDetailedVenueById(id));
    }

    public Flowable<DetailedVenue> getCachedDetailVenue(String id) {
        return get(id, detailedVenueRepository.stream(id));
    }

    private Flowable<DetailedVenue> get(String id, Flowable<DetailedVenue> detailedVenueFlowable) {
        return Flowable.combineLatest(
                venueHistoryRepository.isHereNow(id),
                detailedVenueFlowable, (aBoolean, detailedVenue) -> {
                    detailedVenue.setHereNow(aBoolean);
                    return detailedVenue;
                });
    }

}
