package home.oleg.placesnearme.data.repositories;

import android.location.Location;

import home.oleg.placenearme.models.LatLng;
import home.oleg.placenearme.repositories.DistanceRepository;

/**
 * Created by Oleg Sheliakin on 09.10.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public class DistanceRepositoryImpl implements DistanceRepository {

    @Override
    public double evaluate(LatLng from, LatLng to) {
        Location locationFrom = create(from);
        Location locationTo = create(to);
        return locationFrom.distanceTo(locationTo);
    }

    private Location create(LatLng latLng) {
        Location location = new Location("");
        location.setLatitude(latLng.getLat());
        location.setLongitude(latLng.getLng());
        return location;
    }
}
