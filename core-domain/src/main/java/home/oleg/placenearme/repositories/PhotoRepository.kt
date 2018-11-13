package home.oleg.placenearme.repositories

import home.oleg.placenearme.models.Photo
import io.reactivex.Observable

interface PhotoRepository {

    fun getPhotosById(venueId: String): Observable<List<Photo>>

}
