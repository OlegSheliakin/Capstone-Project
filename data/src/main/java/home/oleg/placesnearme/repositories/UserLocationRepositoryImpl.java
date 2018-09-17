package home.oleg.placesnearme.repositories;

import home.oleg.placenearme.models.UserLocation;
import home.oleg.placenearme.repositories.UserLocationRepository;
import io.reactivex.Single;

/**
 * Created by Oleg Sheliakin on 17.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public class UserLocationRepositoryImpl implements UserLocationRepository {

    @Override
    public Single<UserLocation> getLocation() {
        return Single.just(new UserLocation(34.7576236, 39.5017049));
    }
    
}

