package home.oleg.placesnearme.core_presentation.mapper

import java.util.ArrayList

import home.oleg.placenearme.models.Section
import home.oleg.placenearme.models.Venue
import home.oleg.placesnearme.core_presentation.viewdata.IconViewData
import home.oleg.placesnearme.core_presentation.viewdata.PreviewVenueViewData

object VenueMapViewMapper {

    fun map(section: Section, venue: Venue) = object : PreviewVenueViewData {
        override val id = venue.id
        override val title = venue.name
        override val distance: Double = venue.distance
        override val address: String = venue.location.address ?: "-"
        override val lat: Double = venue.location.lat
        override val lng: Double = venue.location.lng
        override val iconViewData: IconViewData? = venue.category?.let { IconViewData(it.iconPrefix, it.iconSuffix) }
        override val categoryName: String? = venue.category?.name
        override val sectionType: Section? = section
    }

    fun map(section: Section, venues: List<Venue>): List<PreviewVenueViewData> {
        val venueMapViewDataList = ArrayList<PreviewVenueViewData>()

        for (venue in venues) {
            venueMapViewDataList.add(map(section, venue))
        }

        return venueMapViewDataList
    }
}
