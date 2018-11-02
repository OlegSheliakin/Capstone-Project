package home.oleg.placesnearme.data.mapper

import java.util.ArrayList

import home.oleg.placenearme.models.DetailedVenue
import home.oleg.placesnearme.data.model.DetailedVenueDbEntity
import home.oleg.placesnearme.data.model.DetailedVenueWithPhotos
import home.oleg.placesnearme.data.provider.LocationStore

/**
 * Created by Oleg Sheliakin on 25.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
object DetailedVenueMapper {

    fun map(detailedVenue: home.oleg.placesnearme.network.models.DetailedVenue) = DetailedVenue(
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

    fun map(venue: DetailedVenue): DetailedVenueWithPhotos {
        val detailedVenue = DetailedVenueDbEntity(
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

        val detailedVenueWithPhotos = DetailedVenueWithPhotos(detailedVenue)
        detailedVenueWithPhotos.photos = PhotoMapper.mapToDb(venue.photos)

        return detailedVenueWithPhotos
    }

    fun map(detailedVenueWithPhotos: List<DetailedVenueWithPhotos>): List<DetailedVenue> {
        val detailedVenues = ArrayList<DetailedVenue>()

        for (detailedVenueWithPhoto in detailedVenueWithPhotos) {
            detailedVenues.add(map(detailedVenueWithPhoto))
        }

        return detailedVenues
    }

    fun map(detailedVenueWithPhotos: DetailedVenueWithPhotos): DetailedVenue {
        val venue = detailedVenueWithPhotos.venue

        return DetailedVenue(
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
