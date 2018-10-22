package home.oleg.placesnearme.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "venue_history",
        foreignKeys = @ForeignKey(
                entity = DetailedVenueDbEntity.class,
                parentColumns = "id",
                childColumns = "venueId",
                onDelete = CASCADE))
public class DetailedVenueHistoryDbEntity {

    @PrimaryKey(autoGenerate = true)
    private Long id;
    @ColumnInfo(index = true)
    private String venueId;
    private long createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVenueId() {
        return venueId;
    }

    public void setVenueId(String venueId) {
        this.venueId = venueId;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}
