package home.oleg.placenearme.repositories;

import home.oleg.placenearme.models.UserLocation;
import io.reactivex.Single;

public interface UserLocationRepository {

    Single<UserLocation> getLocation();

}
