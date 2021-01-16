package home.oleg.placesnearme.coredata.mapper

import home.oleg.placesnearme.coredata.model.PlaceEntity
import home.oleg.placesnearme.coredata.model.PlaceAndPhotos
import home.oleg.placesnearme.coredomain.models.Place
import java.util.*

/**
 * Created by Oleg Sheliakin on 25.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
object DetailedVenueMapper {

    fun map(detailedVenue: home.oleg.placesnearme.corenetwork.models.DetailedVenue) = Place(
            description = detailedVenue.description,
            name = detailedVenue.name,
            id = detailedVenue.id,
            photos = detailedVenue.photos.map { PhotoMapper.map(it) },
            rating = detailedVenue.rating,
            location = detailedVenue.location.let { LocationMapper.map(it) },
            contact = detailedVenue.contact?.let(ContactMapper::map),
            category = detailedVenue.categories?.firstOrNull()?.let(CategoryMapper::map),
            hours = detailedVenue.hours?.let { HoursMapper.map(it) }
    )

    fun map(venue: Place): PlaceAndPhotos {
        val detailedVenue = PlaceEntity(
                id = venue.id,
                title = venue.name,
                category = venue.category,
                contact = venue.contact,
                location = venue.location,
                rating = venue.rating,
                isFavorite = venue.isFavorite,
                description = venue.description,
                isHereNow = venue.isHereNow
        )

        val detailedVenueWithPhotos = PlaceAndPhotos(detailedVenue)
        detailedVenueWithPhotos.photos = PhotoMapper.mapToDb(venue.photos)

        return detailedVenueWithPhotos
    }

    fun map(detailedVenueWithPhotos: List<PlaceAndPhotos>): List<Place> {
        val detailedVenues = ArrayList<Place>()

        for (detailedVenueWithPhoto in detailedVenueWithPhotos) {
            detailedVenues.add(map(detailedVenueWithPhoto))
        }

        return detailedVenues
    }

    fun map(detailedVenueWithPhotos: PlaceAndPhotos): Place {
        val venue = detailedVenueWithPhotos.place

        return Place(
                id = venue.id,
                isHereNow = venue.isHereNow,
                description = venue.description,
                isFavorite = venue.isFavorite,
                rating = venue.rating,
                location = venue.location,
                contact = venue.contact,
                category = venue.category,
                photos = PhotoMapper.mapFromDb(detailedVenueWithPhotos.photos),
                name = venue.title
        )
    }
}
