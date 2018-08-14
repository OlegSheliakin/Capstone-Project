
package home.oleg.placesnearme.models.responses;

import java.util.List;

import home.oleg.placenearme.models.Venue;

public class VenuesResponse {

    private List<Venue> venues;

    public List<Venue> getVenues() {
        return venues;
    }

    public void setVenues(List<Venue> venues) {
        this.venues = venues;
    }

}
