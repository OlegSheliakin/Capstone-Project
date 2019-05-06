package home.oleg.placesnearme.coredata.mapper

import android.location.Location
import home.oleg.placesnearme.coredomain.models.LatLng

import home.oleg.placesnearme.coredomain.models.UserLocation
import home.oleg.placesnearme.corenetwork.models.LocationDto

/**
 * Created by Oleg Sheliakin on 17.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
object LocationMapper {

    fun map(location: Location): LatLng {
        return LatLng(location.latitude, location.longitude)
    }

    fun map(userLocation: UserLocation): Location {
        val location = Location("")
        location.latitude = userLocation.lat
        location.longitude = userLocation.lng
        return location
    }

    fun map(location: LocationDto) = home.oleg.placesnearme.coredomain.models.UserLocation(
            address = location.formattedAddress.firstOrNull(),
            lat = location.lat,
            lng = location.lng
    )

}
