package home.oleg.placesnearme.coredata.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

const val PLACES_HISTORY_TABLE_NAME = "places_history"

@Entity(tableName = PLACES_HISTORY_TABLE_NAME,
        foreignKeys = [ForeignKey(
                entity = PlaceEntity::class,
                parentColumns = arrayOf("id"),
                childColumns = arrayOf("placeId"),
                onDelete = CASCADE, onUpdate = CASCADE)])
data class DetailedVenueHistoryDbEntity(
        @PrimaryKey(autoGenerate = true)
        val id: Long = 0,
        @ColumnInfo(index = true)
        val placeId: String,
        val createdAt: Long,
        val isLastCheckIn: Boolean)
