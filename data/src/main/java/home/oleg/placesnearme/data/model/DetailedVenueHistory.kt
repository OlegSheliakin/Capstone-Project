package home.oleg.placesnearme.data.model

import androidx.room.Embedded
import androidx.room.Entity

@Entity
data class DetailedVenueHistory(
        val createdAt: Long,
        @Embedded
        val detailedVenueWithPhotos: DetailedVenueWithPhotos?)
