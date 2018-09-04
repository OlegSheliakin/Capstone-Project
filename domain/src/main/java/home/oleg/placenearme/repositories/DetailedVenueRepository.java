package home.oleg.placenearme.repositories;

import home.oleg.placenearme.models.DetailedVenue;
import io.reactivex.Single;

public interface DetailedVenueRepository {

    Single<DetailedVenue> getDetailedVenueById(String venueId);

}
