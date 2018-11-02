package home.oleg.placesnearme.network.models

import java.util.ArrayList
import java.util.Collections

import io.reactivex.annotations.NonNull

data class DetailedVenue(
        val canonicalUrl: String? = null,
        val categories: List<Category>? = null,
        val contact: Contact? = null,
        val createdAt: Long? = null,
        val description: String? = null,
        val hours: Hours? = null,
        val id: String,
        val likes: Likes? = null,
        val location: Location,
        val name: String,
        val photoGroup: PhotosGroup? = null,
        val rating: Float = 0.toFloat(),
        val shortUrl: String? = null,
        val stats: Stats? = null,
        val storeId: String? = null,
        val timeZone: String? = null,
        val url: String? = null) {

    val photos: List<Photo>
        @NonNull
        get() {
            if (photoGroup!!.groups == null) {
                return emptyList()
            }

            val res = ArrayList<Photo>()

            for (photoGroup in photoGroup.groups) {
                res.addAll(photoGroup.items)
            }

            return res
        }

}
