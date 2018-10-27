package home.oleg.placenearme.interactors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import home.oleg.placenearme.models.DetailedVenue;
import home.oleg.placenearme.models.LatLng;
import home.oleg.placenearme.models.UserLocation;
import home.oleg.placenearme.repositories.DistanceRepository;
import home.oleg.placenearme.repositories.UserLocationRepository;
import io.reactivex.Single;

/**
 * Created by Oleg Sheliakin on 27.10.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public class EvaluateDistance {

    private final UserLocationRepository locationRepository;
    private final DistanceRepository distanceRepository;

    public EvaluateDistance(UserLocationRepository locationRepository, DistanceRepository distanceRepository) {
        this.locationRepository = locationRepository;
        this.distanceRepository = distanceRepository;
    }

    public Single<List<DetailedVenue>> evaluateDistance(Iterable<DetailedVenue> venues) {
        return locationRepository.getLocation().map(userLocation -> {
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
        });
    }

    public Single<DetailedVenue> evaluateDistance(DetailedVenue venue) {
        return locationRepository.getLocation().map(userLocation -> {
            LatLng to = new LatLng(venue.getLocation().getLat(), venue.getLocation().getLng());
            LatLng from = new LatLng(userLocation.getLat(), userLocation.getLng());
            double distance = distanceRepository.evaluate(from, to);
            venue.setDistance(distance);
            return venue;
        });
    }
}
