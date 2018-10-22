package home.oleg.placesnearme.data.model;


import android.arch.persistence.room.Embedded;

public class DetailedVenueHistory {

    private Long createdAt;

    @Embedded
    private DetailedVenueWithPhotos detailedVenueWithPhotos;

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public DetailedVenueWithPhotos getDetailedVenueWithPhotos() {
        return detailedVenueWithPhotos;
    }

    public void setDetailedVenueWithPhotos(DetailedVenueWithPhotos detailedVenueWithPhotos) {
        this.detailedVenueWithPhotos = detailedVenueWithPhotos;
    }
}
