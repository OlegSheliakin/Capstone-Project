
package home.oleg.placesnearme.network.models.reposnses;

import java.util.List;

import home.oleg.placesnearme.network.models.Venue;

public class VenuesResponse {

    private List<Venue> venues;

    public List<Venue> getVenues() {
        return venues;
    }

    public void setVenues(List<Venue> venues) {
        this.venues = venues;
    }

}
