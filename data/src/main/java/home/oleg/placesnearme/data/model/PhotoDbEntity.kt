package home.oleg.placesnearme.data.model


import home.oleg.placenearme.models.Photo

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(entity = DetailedVenueDbEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("venueId"),
        onDelete = CASCADE)])
data class PhotoDbEntity(
        @PrimaryKey(autoGenerate = true)
        var id: Long = 0,
        @ColumnInfo(index = true)
        var venueId: String = "",
        @Embedded
        var photo: Photo)

