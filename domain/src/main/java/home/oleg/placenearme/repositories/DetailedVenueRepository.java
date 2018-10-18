package home.oleg.placenearme.repositories;

import home.oleg.placenearme.models.DetailedVenue;
import io.reactivex.Flowable;

public interface DetailedVenueRepository {

    Flowable<DetailedVenue> getDetailedVenueById(String venueId);
}
