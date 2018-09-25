
package home.oleg.placesnearme.core_network.models.reposnses;

import home.oleg.placesnearme.core_network.models.Venue;

import java.util.List;


public class VenuesResponse {

    private List<Venue> venues;

    public List<Venue> getVenues() {
        return venues;
    }

    public void setVenues(List<Venue> venues) {
        this.venues = venues;
    }

}
