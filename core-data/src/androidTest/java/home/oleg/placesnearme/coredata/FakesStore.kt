package home.oleg.placesnearme.coredata

import java.util.ArrayList

import home.oleg.placesnearme.coredata.model.PlaceEntity
import home.oleg.placesnearme.coredata.model.PhotoEntity
import home.oleg.placesnearme.coredomain.models.Contact
import home.oleg.placesnearme.coredomain.models.UserLocation
import home.oleg.placesnearme.coredomain.models.Photo

object FakesStore {

    fun listPhotos(placeId: String): List<PhotoEntity> {
        val photoDbEntities = ArrayList<PhotoEntity>()

        val photo = Photo()
        photo.height = 200L
        photo.width = 200L
        photo.prefix = "test"
        photo.suffix = "test"

        val photoDbEntity = PhotoEntity(
                placeId = placeId,
                photo = photo)
        photoDbEntities.add(photoDbEntity)
        return photoDbEntities
    }

    fun getVenue(id: String): PlaceEntity {
        return PlaceEntity(
                id = id,
                contact = Contact("00000"),
                location = UserLocation(
                        lat = 45.0,
                        lng = 45.0,
                        address = null
                ),
                description = "test",
                rating = 5f,
                title = "test",
                category = null,
                isFavorite = false,
                isHereNow = false
        )
    }

}
