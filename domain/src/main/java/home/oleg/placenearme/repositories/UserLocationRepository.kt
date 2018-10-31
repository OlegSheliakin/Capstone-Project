package home.oleg.placenearme.repositories

import home.oleg.placenearme.models.UserLocation
import io.reactivex.Single

interface UserLocationRepository {

    val location: Single<UserLocation>

}
