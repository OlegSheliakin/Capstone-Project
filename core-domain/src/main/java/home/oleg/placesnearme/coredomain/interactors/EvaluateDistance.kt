package home.oleg.placesnearme.coredomain.interactors

import home.oleg.placesnearme.coredomain.models.Place
import home.oleg.placesnearme.coredomain.models.LatLng
import home.oleg.placesnearme.coredomain.models.Venue
import home.oleg.placesnearme.coredomain.repositories.DistanceRepository
import home.oleg.placesnearme.coredomain.repositories.UserLatLngRepository
import io.reactivex.Single
import java.util.*
import javax.inject.Inject

/**
 * Created by Oleg Sheliakin on 27.10.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
class EvaluateDistance @Inject constructor(
        private val locationRepository: UserLatLngRepository,
        private val distanceRepository: DistanceRepository) {

    fun evaluateDistance(venues: List<Place>): Single<List<Place>> {
        return locationRepository.latlng.map { userLocation ->
            val detailedVenues = ArrayList<Place>()
            for (venue in venues) {
                val to = LatLng(venue.location.lat, venue.location.lng)
                val from = LatLng(userLocation.lat, userLocation.lng)
                val distance = distanceRepository.evaluate(from, to)
                detailedVenues.add(venue.copy(distance = distance))
            }
            detailedVenues.sortedBy { it.distance }
        }
    }

    fun evaluateDistanceVenue(venues: List<Venue>): Single<List<Venue>> {
        return locationRepository.latlng.map { userLocation ->
            val detailedVenues = ArrayList<Venue>()
            for (venue in venues) {
                val to = LatLng(venue.location.lat, venue.location.lng)
                val from = LatLng(userLocation.lat, userLocation.lng)
                val distance = distanceRepository.evaluate(from, to)
                detailedVenues.add(venue.copy(distance = distance))
            }
            detailedVenues.sortedBy { it.distance }
        }
    }

    fun evaluateDistance(venue: Place): Single<Place> {
        return locationRepository.latlng.map { userLocation ->
            val to = LatLng(venue.location.lat, venue.location.lng)
            val from = LatLng(userLocation.lat, userLocation.lng)
            val distance = distanceRepository.evaluate(from, to)
            venue.copy(distance = distance)
        }
    }
}
