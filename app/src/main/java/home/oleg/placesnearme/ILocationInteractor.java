package home.oleg.placesnearme;

import android.location.Location;

/**
 * Created by Oleg on 18.04.2016.
 */
public interface ILocationInteractor {
    void connect ();
    void disconnect();
    void stopLocationUpdates();
    void startLocationUpdates();
    void setFlagLocationUpdates(boolean flag);
}
