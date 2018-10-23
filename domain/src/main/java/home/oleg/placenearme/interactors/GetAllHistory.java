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
    private final UserLocationRepository locationRepository;
    private final DistanceRepository distanceRepository;

    public GetAllHistory(VenueHistoryRepository venueHistoryRepository,
                         UserLocationRepository locationRepository,
                         DistanceRepository distanceRepository) {
        this.venueHistoryRepository = venueHistoryRepository;
        this.locationRepository = locationRepository;
        this.distanceRepository = distanceRepository;
    }

    public Flowable<List<DetailedVenue>> getAllHistory() {
        return venueHistoryRepository
                .getHistory()
                .flatMapSingle(detailedVenues -> locationRepository
                        .getLocation()
                        .map(userLocation -> evaluateDistance(userLocation, detailedVenues))
                );
    }

    private List<DetailedVenue> evaluateDistance(UserLocation userLocation, List<DetailedVenue> venues) {
        List<DetailedVenue> detailedVenues = new ArrayList<>();
        for (DetailedVenue venue : venues) {
            LatLng to = new LatLng(venue.getLocation().getLat(), venue.getLocation().getLng());
            LatLng from = new LatLng(userLocation.getLat(), userLocation.getLng());
            double distance = distanceRepository.evaluate(from, to);
            venue.setDistance(distance);
            detailedVenues.add(venue);
        }

        Collections.sort(detailedVenues, (venue, t1) -> Double.compare(venue.getDistance(), t1.getDistance()));
        return detailedVenues;
    }
}
