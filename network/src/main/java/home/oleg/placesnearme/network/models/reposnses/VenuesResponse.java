
package home.oleg.placesnearme.network.models.reposnses;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import home.oleg.placesnearme.network.models.VenueDto;

public class VenuesResponse {

    private List<VenueDto> venues;

    public List<VenueDto> getVenues() {
        return Collections.unmodifiableList(venues);
    }

    public void setVenues(List<VenueDto> venues) {
        this.venues = new ArrayList<>(venues);
    }

}
