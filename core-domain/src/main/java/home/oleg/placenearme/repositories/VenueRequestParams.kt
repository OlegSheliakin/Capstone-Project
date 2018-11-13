package home.oleg.placenearme.repositories

import java.util.Objects

/**
 * Created by Oleg Sheliakin on 21.08.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */

data class VenueRequestParams(
        val radius: Int,
        val lat: Double,
        val lng: Double) {

    companion object {

        fun withLocation(lat: Double, lng: Double): VenueRequestParams {
            return VenueRequestParams(500, lat, lng)
        }
    }
}
