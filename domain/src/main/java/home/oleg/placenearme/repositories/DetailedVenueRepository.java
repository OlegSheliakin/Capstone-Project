package home.oleg.placenearme.repositories;

import home.oleg.placenearme.models.DetailedVenue;
import io.reactivex.Flowable;
import io.reactivex.Single;

public interface DetailedVenueRepository {

    Flowable<DetailedVenue> getDetailedVenueById(String venueId);

    Flowable<DetailedVenue> stream(String venueId);

    Single<DetailedVenue> fetch(String venueId);

}
