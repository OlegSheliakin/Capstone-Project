package home.oleg.placesnearme.coredomain.repositories

import home.oleg.placesnearme.coredomain.models.LatLng
import home.oleg.placesnearme.coredomain.models.UserLocation
import io.reactivex.Single

interface UserLatLngRepository {

    val latlng: Single<LatLng>

}
