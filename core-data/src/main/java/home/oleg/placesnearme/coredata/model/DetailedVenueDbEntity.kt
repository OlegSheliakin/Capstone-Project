package home.oleg.placesnearme.coredata.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import home.oleg.placesnearme.coredomain.models.Category
import home.oleg.placesnearme.coredomain.models.Contact
import home.oleg.placesnearme.coredomain.models.Location

@Entity(tableName = "detailed_venue")
data class DetailedVenueDbEntity(
        @PrimaryKey
        val id: String,
        val title: String,
        val description: String?,
        val rating: Float,
        val isFavorite: Boolean,
        val isHereNow: Boolean,
        @Embedded
        val location: Location,
        @Embedded
        val contact: Contact?,
        @Embedded
        val category: Category?)

