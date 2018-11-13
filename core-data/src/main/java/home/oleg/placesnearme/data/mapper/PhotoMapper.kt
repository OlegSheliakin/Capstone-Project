package home.oleg.placesnearme.data.mapper

import java.util.ArrayList

import home.oleg.placenearme.models.Photo
import home.oleg.placesnearme.data.model.PhotoDbEntity

/**
 * Created by Oleg Sheliakin on 25.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
object PhotoMapper {

    fun map(photo: home.oleg.placesnearme.network.models.Photo) = Photo(
            height = photo.height,
            suffix = photo.suffix,
            prefix = photo.prefix,
            width = photo.width
    )

    fun map(photos: List<home.oleg.placesnearme.network.models.Photo>): List<Photo> {
        val result = ArrayList<Photo>()

        for (photo in photos) {
            result.add(map(photo))
        }

        return result
    }

    fun mapFromDb(photos: List<PhotoDbEntity>): List<Photo> {
        val result = ArrayList<Photo>()

        for (photo in photos) {
            result.add(photo.photo)
        }

        return result
    }

    fun mapToDb(photos: List<Photo>): List<PhotoDbEntity> {
        val result = ArrayList<PhotoDbEntity>()

        for (photo in photos) {
            val photoDbEntity = PhotoDbEntity(
                    photo = photo
            )
            result.add(photoDbEntity)
        }

        return result
    }
}
