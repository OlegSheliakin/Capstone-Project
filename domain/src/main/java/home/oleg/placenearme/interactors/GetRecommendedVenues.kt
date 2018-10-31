package home.oleg.placenearme.interactors

import home.oleg.placenearme.models.Section
import home.oleg.placenearme.models.UserLocation
import home.oleg.placenearme.models.Venue
import home.oleg.placenearme.repositories.SectionRepository
import home.oleg.placenearme.repositories.UserLocationRepository
import home.oleg.placenearme.repositories.VenueRepository
import home.oleg.placenearme.repositories.VenueRequestParams
import io.reactivex.Single
import io.reactivex.annotations.NonNull

class GetRecommendedVenues(
        private val venueRepository: VenueRepository,
        private val locationRepository: UserLocationRepository,
        private val categoryRepository: SectionRepository) {

    val recommendedSection: Single<Pair<Section, List<Venue>>>
        get() {
            val section = categoryRepository.mostFrequent
            return getVenues(section)
        }

    fun getRecommendedSection(section: Section): Single<Pair<Section, List<Venue>>> {
        return getVenues(section)
    }

    private fun getVenues(section: Section): Single<Pair<Section, List<Venue>>> {
        return locationRepository.location
                .flatMap { userLocation -> venueRepository.getRecommendedBySection(section, createFilter(userLocation)) }
                .map { venues -> Pair(section, venues) }
    }

    private fun createFilter(userLocation: UserLocation): VenueRequestParams {
        return VenueRequestParams.withLocation(
                userLocation.lat,
                userLocation.lng)
    }

}
