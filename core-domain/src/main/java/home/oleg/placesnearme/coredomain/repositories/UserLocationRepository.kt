package home.oleg.placesnearme.coredomain.repositories

import home.oleg.placesnearme.coredomain.models.UserLocation
import io.reactivex.Single

interface UserLocationRepository {

    val location: Single<UserLocation>

}
