package home.oleg.placesnearme.coredomain.interactors

import home.oleg.placesnearme.coredomain.models.Section
import home.oleg.placesnearme.coredomain.models.UserLocation
import home.oleg.placesnearme.coredomain.models.Venue
import home.oleg.placesnearme.coredomain.repositories.SectionRepository
import home.oleg.placesnearme.coredomain.repositories.UserLocationRepository
import home.oleg.placesnearme.coredomain.repositories.VenueRepository
import home.oleg.placesnearme.coredomain.repositories.VenueRequestParams
import io.reactivex.Single
import javax.inject.Inject

class GetRecommendedVenues @Inject constructor(
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
