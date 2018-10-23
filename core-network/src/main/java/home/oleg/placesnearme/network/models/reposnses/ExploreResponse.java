
package home.oleg.placesnearme.network.models.reposnses;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import home.oleg.placesnearme.network.models.Group;
import home.oleg.placesnearme.network.models.VenueDto;
import home.oleg.placesnearme.network.models.VenueItem;

public class ExploreResponse {

    @SerializedName("groups")
    private List<Group<VenueItem>> groups;
    @SerializedName("headerFullLocation")
    private String headerFullLocation;
    @SerializedName("headerLocation")
    private String headerLocation;
    @SerializedName("headerLocationGranularity")
    private String headerLocationGranularity;
    @SerializedName("suggestedRadius")
    private Long suggestedRadius;
    @SerializedName("totalResults")
    private Long totalResults;

    public List<VenueDto> getVenues() {
        if (groups == null || groups.isEmpty()) {
            return Collections.emptyList();
        }

        List<VenueDto> venues = new ArrayList<>();
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

}
