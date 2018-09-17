package home.oleg.placesnearme.repositories;

import home.oleg.placenearme.models.UserLocation;
import home.oleg.placenearme.repositories.UserLocationRepository;
import home.oleg.placesnearme.provider.ReactiveLocationStore;
import home.oleg.placesnearme.mapper.LocationMapper;
import io.reactivex.Single;

/**
 * Created by Oleg Sheliakin on 17.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public class UserLocationRepositoryImpl implements UserLocationRepository {

    private final ReactiveLocationStore reactiveLocationStore;

    public UserLocationRepositoryImpl(ReactiveLocationStore reactiveLocationStore) {
        this.reactiveLocationStore = reactiveLocationStore;
    }

    @Override
    public Single<UserLocation> getLocation() {
        return reactiveLocationStore
                .getLastLocation()
                .map(LocationMapper::map);
    }

}

