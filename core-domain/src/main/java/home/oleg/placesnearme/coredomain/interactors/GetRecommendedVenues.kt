package home.oleg.placesnearme.coredomain.interactors

import home.oleg.placesnearme.coredomain.models.LatLng
import home.oleg.placesnearme.coredomain.models.Section
import home.oleg.placesnearme.coredomain.models.Venue
import home.oleg.placesnearme.coredomain.repositories.SectionRepository
import home.oleg.placesnearme.coredomain.repositories.UserLatLngRepository
import home.oleg.placesnearme.coredomain.repositories.VenueRepository
import home.oleg.placesnearme.coredomain.repositories.VenueRequestParams
import io.reactivex.Single
import javax.inject.Inject

class GetRecommendedVenues @Inject constructor(
        private val venueRepository: VenueRepository,
        private val locationRepository: UserLatLngRepository,
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
        return locationRepository.latlng
                .map(::createParams)
                .flatMap { venueRepository.getRecommendedBySection(section, it) }
                .map { venues -> Pair(section, venues) }
    }

    private fun createParams(latlng: LatLng): VenueRequestParams {
        return VenueRequestParams.withLocation(latlng.lat, latlng.lng)
    }

}
