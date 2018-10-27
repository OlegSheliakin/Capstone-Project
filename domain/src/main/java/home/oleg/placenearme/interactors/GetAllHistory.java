package home.oleg.placenearme.interactors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import home.oleg.placenearme.models.DetailedVenue;
import home.oleg.placenearme.models.LatLng;
import home.oleg.placenearme.models.UserLocation;
import home.oleg.placenearme.models.Venue;
import home.oleg.placenearme.repositories.DistanceRepository;
import home.oleg.placenearme.repositories.UserLocationRepository;
import home.oleg.placenearme.repositories.VenueHistoryRepository;
import io.reactivex.Flowable;

public final class GetAllHistory {

    private final VenueHistoryRepository venueHistoryRepository;

    public GetAllHistory(VenueHistoryRepository venueHistoryRepository) {
        this.venueHistoryRepository = venueHistoryRepository;
    }

    public Flowable<List<DetailedVenue>> getAllHistory() {
        return venueHistoryRepository
                .getHistory();
    }

}
