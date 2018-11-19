package home.oleg.placesnearme.coredata.mapper

import home.oleg.placesnearme.coredomain.models.Venue
import home.oleg.placesnearme.corenetwork.models.VenueDto
import java.util.*

/**
 * Created by Oleg Sheliakin on 25.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
object VenueMapper {

    fun map(venue: VenueDto) = Venue(
            name = venue.name,
            category = venue.categories.first { it.primary }.let { CategoryMapper.map(it) },
            id = venue.id,
            location = LocationMapper.map(venue.location)
    )

    fun map(venues: List<VenueDto>): List<Venue> {
        val result = ArrayList<Venue>()

        for (venue in venues) {
            result.add(map(venue))
        }

        return result
    }

}
