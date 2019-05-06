package home.oleg.placesnearme.corepresentation.viewdata

import home.oleg.placesnearme.coredomain.models.Category
import home.oleg.placesnearme.coredomain.models.Contact
import home.oleg.placesnearme.coredomain.models.Place
import home.oleg.placesnearme.coredomain.models.Hours
import home.oleg.placesnearme.coredomain.models.UserLocation
import home.oleg.placesnearme.coredomain.models.Section
import java.util.*

/**
 * Created by Oleg Sheliakin on 14.08.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
data class PlaceViewData(
        val openingHoursStatus: String? = null,
        val isFavorite: Boolean = false,
        val isHere: Boolean = false,
        val rating: Float = 0.toFloat(),
        val isOpen: Boolean? = null,
        val photos: List<PhotoViewData> = emptyList(),
        val description: String = "-",
        val formattedPhone: String = "-",
        override val id: String,
        override val title: String,
        override val distance: Double,
        override val address: String,
        override val lat: Double,
        override val lng: Double,
        override val iconViewData: IconViewData?,
        override val categoryName: String?,
        override val sectionType: Section?
) : PreviewPlace {

    val adoptedRating: Float
        get() = rating / 2.0f

    val bestPhoto: PhotoViewData?
        get() = if (!photos.isEmpty()) {
            photos[0]
        } else null

    fun mapToDetailVenue() = Place(
            category = Category(categoryName, iconSuffix = iconViewData?.suffix, iconPrefix = iconViewData?.prefix),
            distance = distance,
            isHereNow = isHere,
            id = id,
            name = title,
            contact = Contact(formattedPhone = formattedPhone),
            description = description,
            hours = Hours(isOpen = isOpen, status = openingHoursStatus),
            isFavorite = isFavorite,
            location = UserLocation(address = address, lat = lat, lng = lng),
            photos = photos.map(PhotoViewData.Companion::map),
            rating = rating
    )

    companion object {

        fun mapFrom(detailedVenues: List<Place>): List<PlaceViewData> {
            if (detailedVenues.isEmpty()) {
                return emptyList()
            }

            val list = ArrayList<PlaceViewData>()
            for (venue in detailedVenues) {
                list.add(PlaceViewData.mapFrom(venue))
            }
            return list
        }

        fun mapFrom(venue: Place) = PlaceViewData(
                openingHoursStatus = venue.hours?.status ?: "-",
                rating = venue.rating,
                photos = venue.photos.map { PhotoViewData.map(it) },
                isFavorite = venue.isFavorite,
                isOpen = venue.hours?.isOpen ?: false,
                description = venue.description ?: "-",
                formattedPhone = venue.contact?.formattedPhone ?: "-",
                isHere = venue.isHereNow,
                lng = venue.location.lng,
                lat = venue.location.lat,
                address = venue.location.address ?: "-",
                id = venue.id,
                distance = venue.distance,
                title = venue.name,
                categoryName = venue.category?.name,
                iconViewData = venue.category?.let { IconViewData(it.iconPrefix, it.iconSuffix) },
                sectionType = null
        )
    }
}
