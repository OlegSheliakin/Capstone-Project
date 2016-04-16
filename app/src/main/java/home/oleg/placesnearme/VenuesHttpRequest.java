package home.oleg.placesnearme;

import android.location.Location;

/**
 * Created by Oleg on 16.04.2016.
 */
public class VenuesHttpRequest {

    Parameters parameters;

    public VenuesHttpRequest(Parameters parameters) {
        this.parameters = parameters;
    }

    @Override
    public String toString() {
        return "https://api.foursquare.com/v2/venues/explore"
                + "?ll="
                + parameters.getLocation().getLatitude()
                + ","
                + parameters.getLocation().getLongitude()
                + "&radius="
                + parameters.getRadius()
                + "&section="
                + parameters.getSection()
                + "&alt="
                + parameters.getLocation().getAltitude()
                + "&llAcc="
                + parameters.getLocation().getAccuracy()
                + "&client_id="
                + Constants.CLIENT_ID
                + "&client_secret="
                + Constants.CLIENT_SECRET
                + "&v="
                + Constants.FOURSQAURE_API_VERSION;
    }
}
