package home.oleg.placesnearme.data.repositories;

import home.oleg.placenearme.models.UserLocation;
import home.oleg.placenearme.repositories.UserLocationRepository;
import home.oleg.placesnearme.data.mapper.LocationMapper;
import home.oleg.placesnearme.data.provider.CachedLocationsStore;
import home.oleg.placesnearme.data.provider.ReactiveLocationStore;
import io.reactivex.Single;

/**
 * Created by Oleg Sheliakin on 17.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public class UserLocationRepositoryImpl implements UserLocationRepository {

    private final ReactiveLocationStore reactiveLocationStore;
    private final CachedLocationsStore cachedLocationsStore;

    public UserLocationRepositoryImpl(ReactiveLocationStore reactiveLocationStore, CachedLocationsStore cachedLocationsStore) {
        this.reactiveLocationStore = reactiveLocationStore;
        this.cachedLocationsStore = cachedLocationsStore;
    }

    @Override
    public Single<UserLocation> getLocation() {
        return reactiveLocationStore
                .getLastLocation()
                .doOnSuccess(cachedLocationsStore::save)
                .onErrorResumeNext(cachedLocationsStore.getLastLocation())
                .map(LocationMapper::map);
    }

}
