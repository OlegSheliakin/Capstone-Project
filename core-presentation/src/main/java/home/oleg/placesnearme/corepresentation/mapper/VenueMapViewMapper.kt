package home.oleg.placesnearme.corepresentation.mapper

import home.oleg.placesnearme.corepresentation.viewdata.IconViewData
import home.oleg.placesnearme.corepresentation.viewdata.PreviewPlace
import home.oleg.placesnearme.coredomain.models.Section
import home.oleg.placesnearme.coredomain.models.Venue
import java.util.*

object VenueMapViewMapper {

    fun map(section: Section, venue: Venue) = object : PreviewPlace {
        override val id = venue.id
        override val title = venue.name
        override val distance: Double = venue.distance
        override val address: String = venue.location.address ?: "-"
        override val lat: Double = venue.location.lat
        override val lng: Double = venue.location.lng
        override val iconViewData: IconViewData? = venue.category.let { IconViewData(it.iconPrefix, it.iconSuffix) }
        override val categoryName: String? = venue.category.name
        override val sectionType: Section = section
    }

    fun map(section: Section, venues: List<Venue>): List<PreviewPlace> {
        val venueMapViewDataList = ArrayList<PreviewPlace>()

        for (venue in venues) {
            venueMapViewDataList.add(map(section, venue))
        }

        return venueMapViewDataList
    }
}
