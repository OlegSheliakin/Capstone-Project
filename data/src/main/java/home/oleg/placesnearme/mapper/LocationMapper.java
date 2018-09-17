package home.oleg.placesnearme.mapper;

import android.location.Location;

import home.oleg.placenearme.models.UserLocation;

/**
 * Created by Oleg Sheliakin on 17.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public final class LocationMapper {

    private LocationMapper() {

    }

    public static UserLocation map(Location location) {
        return new UserLocation(location.getLatitude(), location.getLongitude());
    }

    public static Location map(UserLocation userLocation) {
        Location location = new Location("");
        location.setLatitude(userLocation.getLat());
        location.setLongitude(userLocation.getLng());
        return location;
    }

}
