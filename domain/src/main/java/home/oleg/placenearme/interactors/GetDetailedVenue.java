package home.oleg.placenearme.interactors;

import home.oleg.placenearme.models.DetailedVenue;
import home.oleg.placenearme.repositories.DetailedVenueRepository;
import io.reactivex.Single;

public class GetDetailedVenue {

    private final DetailedVenueRepository detailedVenueRepository;

    public GetDetailedVenue(DetailedVenueRepository detailedVenueRepository) {
        this.detailedVenueRepository = detailedVenueRepository;
    }

    public Single<DetailedVenue> getDetailedVenue(String id) {
        return detailedVenueRepository.getDetailedVenueById(id);
    }
}
