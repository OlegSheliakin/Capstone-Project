package home.oleg.placesnearme.coredomain.repositories

import home.oleg.placesnearme.coredomain.models.Photo
import io.reactivex.Observable

interface PhotoRepository {

    fun getPhotosById(venueId: String): Observable<List<Photo>>

}
