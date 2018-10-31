package home.oleg.placenearme.interactors

import home.oleg.placenearme.models.DetailedVenue
import home.oleg.placenearme.models.LatLng
import home.oleg.placenearme.models.Venue
import home.oleg.placenearme.repositories.DistanceRepository
import home.oleg.placenearme.repositories.UserLocationRepository
import io.reactivex.Single
import java.util.*

/**
 * Created by Oleg Sheliakin on 27.10.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
class EvaluateDistance(
        private val locationRepository: UserLocationRepository,
        private val distanceRepository: DistanceRepository) {

    fun evaluateDistance(venues: List<DetailedVenue>): Single<List<DetailedVenue>> {
        return locationRepository.location.map { userLocation ->
            val detailedVenues = ArrayList<DetailedVenue>()
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
        return locationRepository.location.map { userLocation ->
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

    fun evaluateDistance(venue: DetailedVenue): Single<DetailedVenue> {
        return locationRepository.location.map { userLocation ->
            val to = LatLng(venue.location.lat, venue.location.lng)
            val from = LatLng(userLocation.lat, userLocation.lng)
            val distance = distanceRepository.evaluate(from, to)
            venue.copy(distance = distance)
        }
    }
}
