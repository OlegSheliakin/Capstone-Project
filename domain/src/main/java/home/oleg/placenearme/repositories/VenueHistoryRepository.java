package home.oleg.placenearme.repositories;

import java.util.List;

import home.oleg.placenearme.models.DetailedVenue;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

public interface VenueHistoryRepository {

    Flowable<List<DetailedVenue>> getHistory();

    Completable dropCurrentVenue();

    Single<Boolean> isHereNow(DetailedVenue detailedVenue);

    Completable checkIn(DetailedVenue detailedVenue);

    Completable remove(DetailedVenue detailedVenue);

}
