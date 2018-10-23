
package home.oleg.placenearme.models;

public class DetailedVenueWithCreatedDate {

    private final long createdAt;
    private final DetailedVenue detailedVenue;

    public DetailedVenueWithCreatedDate(long createdAt, DetailedVenue detailedVenue) {
        this.createdAt = createdAt;
        this.detailedVenue = detailedVenue;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public DetailedVenue getDetailedVenue() {
        return detailedVenue;
    }
}
