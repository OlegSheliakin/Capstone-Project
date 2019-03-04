package home.oleg.placesnearme.corepresentation.viewdata

import home.oleg.placesnearme.coredomain.models.Photo

/**
 * Created by Oleg Sheliakin on 21.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
data class PhotoViewData(
        val height: Long? = null,
        val prefix: String? = null,
        val suffix: String? = null,
        val width: Long? = null) {

    val fullSizeUrl: String
        get() = prefix + width + "x" + height + suffix

    companion object {

        fun map(photo: Photo) = PhotoViewData(
                height = photo.height,
                prefix = photo.prefix,
                width = photo.width,
                suffix = photo.suffix
        )

        fun map(photoViewData: PhotoViewData) = Photo(
                height = photoViewData.height,
                prefix = photoViewData.prefix,
                width = photoViewData.width,
                suffix = photoViewData.suffix
        )
    }
}

