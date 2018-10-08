package home.oleg.placenearme.interactors;

import home.oleg.placenearme.models.UserLocation;
import home.oleg.placenearme.repositories.UserLocationRepository;
import io.reactivex.Single;

public class GetUserLocation {
    private final UserLocationRepository locationRepository;

    public GetUserLocation(UserLocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Single<UserLocation> getUserLocation() {
        return locationRepository.getLocation();
    }

}
