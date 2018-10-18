package home.oleg.placesnearme.data.mapper;

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

    public static home.oleg.placenearme.models.Location map(home.oleg.placesnearme.network.models.Location location) {
        home.oleg.placenearme.models.Location result = new home.oleg.placenearme.models.Location();

        if (location.getAddress() != null) {
            result.setAddress(location.getAddress());
        } else if (location.getFormattedAddress() != null && !location.getFormattedAddress().isEmpty()) {
            String address = location.getFormattedAddress().get(0);
            result.setAddress(address);
        }

        result.setLat(location.getLat());
        result.setLng(location.getLng());
        return result;
    }

}
