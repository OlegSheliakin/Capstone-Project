package home.oleg.placesnearme.coredata.mapper

import home.oleg.placesnearme.coredata.model.PhotoEntity
import home.oleg.placesnearme.coredomain.models.Photo
import java.util.*

/**
 * Created by Oleg Sheliakin on 25.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
object PhotoMapper {

    fun map(photo: home.oleg.placesnearme.corenetwork.models.Photo) = Photo(
            height = photo.height,
            suffix = photo.suffix,
            prefix = photo.prefix,
            width = photo.width
    )

    fun map(photos: List<home.oleg.placesnearme.corenetwork.models.Photo>): List<Photo> {
        val result = ArrayList<Photo>()

        for (photo in photos) {
            result.add(map(photo))
        }

        return result
    }

    fun mapFromDb(photos: List<PhotoEntity>): List<Photo> {
        val result = ArrayList<Photo>()

        for (photo in photos) {
            result.add(photo.photo)
        }

        return result
    }

    fun mapToDb(photos: List<Photo>): List<PhotoEntity> {
        val result = ArrayList<PhotoEntity>()

        for (photo in photos) {
            val photoDbEntity = PhotoEntity(
                    photo = photo
            )
            result.add(photoDbEntity)
        }

        return result
    }
}
