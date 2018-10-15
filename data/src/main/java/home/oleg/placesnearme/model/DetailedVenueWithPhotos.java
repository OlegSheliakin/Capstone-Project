package home.oleg.placesnearme.model;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.util.List;


public class DetailedVenueWithPhotos {

    @Embedded
    private DetailedVenueDbEntity venue;

    @Relation(parentColumn = "id", entityColumn = "venueId")
    private List<PhotoDbEntity> photos;

    public DetailedVenueDbEntity getVenue() {
        return venue;
    }

    public void setVenue(DetailedVenueDbEntity venue) {
        this.venue = venue;
    }

    public List<PhotoDbEntity> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotoDbEntity> photos) {
        this.photos = photos;
    }
}
