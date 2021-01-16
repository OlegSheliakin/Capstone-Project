package home.oleg.placesnearme.coredata.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import home.oleg.placesnearme.coredomain.models.Category
import home.oleg.placesnearme.coredomain.models.Contact
import home.oleg.placesnearme.coredomain.models.UserLocation

const val PLACES_TABLE_NAME = "places"

@Entity(tableName = PLACES_TABLE_NAME)
data class PlaceEntity(
        @PrimaryKey
        @ColumnInfo(index = true)
        val id: String,
        val title: String,
        val description: String?,
        val rating: Float,
        val isFavorite: Boolean,
        val isHereNow: Boolean,
        @Embedded
        val location: UserLocation,
        @Embedded
        val contact: Contact?,
        @Embedded
        val category: Category?)

