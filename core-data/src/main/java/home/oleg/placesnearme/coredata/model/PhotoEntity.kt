package home.oleg.placesnearme.coredata.model


import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import home.oleg.placesnearme.coredomain.models.Photo

const val PHOTOS_TABLE_NAME = "photos"

@Entity(tableName = PHOTOS_TABLE_NAME,
        foreignKeys = [ForeignKey(entity = PlaceEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("placeId"),
        onDelete = CASCADE)])
data class PhotoEntity(
        @PrimaryKey(autoGenerate = true)
        var id: Long = 0,
        @ColumnInfo(index = true)
        var placeId: String = "",
        @Embedded
        var photo: Photo)

