package home.oleg.placenearme.repositories;

import java.util.List;

import home.oleg.placenearme.models.DetailedVenue;
import io.reactivex.Completable;
import io.reactivex.Flowable;

public interface VenueHistoryRepository {

    Flowable<List<DetailedVenue>> getHistory();

    Completable checkOutFromCurrent();

    Flowable<Boolean> isHereNow(String venueId);

    Completable checkIn(DetailedVenue detailedVenue);

    Completable checkOut(String venueId);

    Completable remove(String venueId);

}
