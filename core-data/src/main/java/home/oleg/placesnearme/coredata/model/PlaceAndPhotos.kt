package home.oleg.placesnearme.coredata.model

import androidx.room.Embedded
import androidx.room.Relation

data class PlaceAndPhotos(
        @Embedded
        var place: PlaceEntity
) {

    @Relation(parentColumn = "id", entityColumn = "placeId")
    var photos: List<PhotoEntity> = emptyList()
}

