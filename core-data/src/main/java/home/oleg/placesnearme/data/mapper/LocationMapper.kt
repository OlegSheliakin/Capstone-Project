package home.oleg.placesnearme.data.mapper

import android.location.Location

import home.oleg.placenearme.models.UserLocation

/**
 * Created by Oleg Sheliakin on 17.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
object LocationMapper {

    fun map(location: Location): UserLocation {
        return UserLocation(location.latitude, location.longitude)
    }

    fun map(userLocation: UserLocation): Location {
        val location = Location("")
        location.latitude = userLocation.lat
        location.longitude = userLocation.lng
        return location
    }

    fun map(location: home.oleg.placesnearme.network.models.Location) = home.oleg.placenearme.models.Location(
            address = location.formattedAddress.firstOrNull(),
            lat = location.lat,
            lng = location.lng
    )

}
