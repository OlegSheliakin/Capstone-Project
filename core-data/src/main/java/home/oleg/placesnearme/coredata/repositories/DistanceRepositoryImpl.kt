package home.oleg.placesnearme.coredata.repositories

import android.location.Location

import javax.inject.Inject

import home.oleg.placesnearme.coredomain.models.LatLng
import home.oleg.placesnearme.coredomain.repositories.DistanceRepository

/**
 * Created by Oleg Sheliakin on 09.10.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
class DistanceRepositoryImpl @Inject constructor() : DistanceRepository {

    override fun evaluate(from: LatLng, to: LatLng): Double {
        val locationFrom = create(from)
        val locationTo = create(to)
        return locationFrom.distanceTo(locationTo).toDouble()
    }

    private fun create(latLng: LatLng): Location {
        return Location("").apply {
            latitude = latLng.lat
            longitude = latLng.lng
        }
    }
}
