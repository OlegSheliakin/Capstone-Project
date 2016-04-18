package home.oleg.placesnearme;

import android.location.Location;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;

/**
 * Created by Oleg on 18.04.2016.
 */
public interface ILocationInteractor extends LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{
    void connect ();
    void disconnect();
    void stopLocationUpdates();
    void startLocationUpdates();
    void setFlagLocationUpdates(boolean flag);
}
