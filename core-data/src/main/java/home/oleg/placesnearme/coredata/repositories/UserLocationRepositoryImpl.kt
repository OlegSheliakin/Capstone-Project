package home.oleg.placesnearme.coredata.repositories

import home.oleg.placesnearme.coredata.location.CachedLocationsStore
import home.oleg.placesnearme.coredata.location.ReactiveLocationStore
import home.oleg.placesnearme.coredata.mapper.LocationMapper
import home.oleg.placesnearme.coredomain.models.LatLng
import home.oleg.placesnearme.coredomain.models.UserLocation
import home.oleg.placesnearme.coredomain.repositories.UserLatLngRepository
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by Oleg Sheliakin on 17.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
class UserLocationRepositoryImpl @Inject constructor(
        private val reactiveLocationStore: ReactiveLocationStore,
        private val cachedLocationsStore: CachedLocationsStore) : UserLatLngRepository {

    override val latlng: Single<LatLng>
        get() = reactiveLocationStore.lastLocation
                    .doOnSuccess { cachedLocationsStore.save(it) }
                    .onErrorResumeNext(cachedLocationsStore.lastLocation)
                    .map(LocationMapper::map)

}
