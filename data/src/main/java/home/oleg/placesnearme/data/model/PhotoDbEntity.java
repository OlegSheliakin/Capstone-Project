package home.oleg.placesnearme.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import home.oleg.placenearme.models.Photo;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = DetailedVenueDbEntity.class,
        parentColumns = "id",
        childColumns = "venueId",
        onDelete = CASCADE))
public class PhotoDbEntity {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(index = true)
    private String venueId;

    @Embedded
    private Photo photo;

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

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }
}
