
package home.oleg.placesnearme.models.responses;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import home.oleg.placenearme.models.Group;
import home.oleg.placenearme.models.SuggestedBounds;
import home.oleg.placenearme.models.Venue;
import home.oleg.placenearme.models.VenueItem;
import home.oleg.placenearme.models.Warning;

public class ExploreResponse {

    @SerializedName("groups")
    private List<Group<VenueItem>> groups;
    @SerializedName("headerFullLocation")
    private String headerFullLocation;
    @SerializedName("headerLocation")
    private String headerLocation;
    @SerializedName("headerLocationGranularity")
    private String headerLocationGranularity;
    @SerializedName("suggestedBounds")
    private SuggestedBounds suggestedBounds;
    @SerializedName("suggestedRadius")
    private Long suggestedRadius;
    @SerializedName("totalResults")
    private Long totalResults;
    @SerializedName("warning")
    private Warning warning;

    public List<Venue> getVenues() {
        if (groups == null || groups.isEmpty()) {
            return Collections.emptyList();
        }

        List<Venue> venues = new ArrayList<>();
        for (VenueItem venueItem : groups.get(0).getItems()) {
            venues.add(venueItem.getVenue());
        }

        return venues;
    }

    public List<Group<VenueItem>> getGroups() {
        return groups;
    }

    public void setGroups(List<Group<VenueItem>> groups) {
        this.groups = groups;
    }

    public String getHeaderFullLocation() {
        return headerFullLocation;
    }

    public void setHeaderFullLocation(String headerFullLocation) {
        this.headerFullLocation = headerFullLocation;
    }

    public String getHeaderLocation() {
        return headerLocation;
    }

    public void setHeaderLocation(String headerLocation) {
        this.headerLocation = headerLocation;
    }

    public String getHeaderLocationGranularity() {
        return headerLocationGranularity;
    }

    public void setHeaderLocationGranularity(String headerLocationGranularity) {
        this.headerLocationGranularity = headerLocationGranularity;
    }

    public SuggestedBounds getSuggestedBounds() {
        return suggestedBounds;
    }

    public void setSuggestedBounds(SuggestedBounds suggestedBounds) {
        this.suggestedBounds = suggestedBounds;
    }

    public Long getSuggestedRadius() {
        return suggestedRadius;
    }

    public void setSuggestedRadius(Long suggestedRadius) {
        this.suggestedRadius = suggestedRadius;
    }

    public Long getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Long totalResults) {
        this.totalResults = totalResults;
    }

    public Warning getWarning() {
        return warning;
    }

    public void setWarning(Warning warning) {
        this.warning = warning;
    }

}
