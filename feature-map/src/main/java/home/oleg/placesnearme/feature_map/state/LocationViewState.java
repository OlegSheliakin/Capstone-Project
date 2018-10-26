package home.oleg.placesnearme.feature_map.state;

import home.oleg.placenearme.models.UserLocation;

public class LocationViewState {

    private final UserLocation userLocation;

    public LocationViewState(UserLocation userLocation) {
        this.userLocation = userLocation;
    }

    public static LocationViewState initial() {
        return new LocationViewState(null);
    }

    public static LocationViewState showLocation(UserLocation userLocation) {
        return new LocationViewState(userLocation);
    }

    public UserLocation getUserLocation() {
        return userLocation;
    }
}
