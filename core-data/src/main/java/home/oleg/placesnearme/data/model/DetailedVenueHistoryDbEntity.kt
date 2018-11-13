package home.oleg.placesnearme.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(tableName = "venue_history",
        foreignKeys = [ForeignKey(
                entity = DetailedVenueDbEntity::class,
                parentColumns = arrayOf("id"),
                childColumns = arrayOf("venueId"),
                onDelete = CASCADE, onUpdate = CASCADE)])
data class DetailedVenueHistoryDbEntity(
        @PrimaryKey(autoGenerate = true)
        val id: Long = 0,
        @ColumnInfo(index = true)
        val venueId: String,
        val createdAt: Long,
        val isLastCheckIn: Boolean)
