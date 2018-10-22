package home.oleg.placesnearme.data.model;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.util.Collections;
import java.util.List;
import java.util.Objects;


public final class DetailedVenueWithPhotos {

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
        if (photos == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(photos);
    }

    public void setPhotos(List<PhotoDbEntity> photos) {
        this.photos = photos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DetailedVenueWithPhotos that = (DetailedVenueWithPhotos) o;
        return Objects.equals(venue, that.venue) &&
                Objects.equals(photos, that.photos);
    }

    @Override
    public int hashCode() {

        return Objects.hash(venue, photos);
    }
}
