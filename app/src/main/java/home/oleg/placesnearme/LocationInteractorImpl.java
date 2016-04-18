package home.oleg.placesnearme;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import home.oleg.placesnearme.mapMVP.IMapPresenter;

/**
 * Created by Oleg on 18.04.2016.
 */
public class LocationInteractorImpl implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener, ILocationInteractor {

    private Context context;
    private GoogleApiClient googleApiClient;
    private Location location;
    private IMapPresenter mapPresenter;
    private boolean requestingLocationUpdates;

    public LocationInteractorImpl(Context context, IMapPresenter mapPresenter) {
        this.context = context;
        this.mapPresenter = mapPresenter;
        googleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        checkPermission();

        if (requestingLocationUpdates) {
            startLocationUpdates();
        }

        location = getLocation();
        Parameters parameters = new Parameters();
        parameters.setLocation(location)
                .setRadius(1000)
                .setSection("food")
                .setOpenNow(1)
                .setVenuesPhoto(1);
        mapPresenter.onGoogleApiClientConnected(location);
        mapPresenter.startSearchingVenues(parameters);

    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        mapPresenter.onFailed();
    }

    @Override
    public void onLocationChanged(Location location) {
        mapPresenter.onLocationChanged(location);
    }

    @Override
    public void connect() {
        googleApiClient.connect();
    }

    @Override
    public void disconnect() {
        googleApiClient.disconnect();
    }

    @Override
    public void startLocationUpdates() {
        checkPermission();
        if (googleApiClient.isConnected() && !requestingLocationUpdates) {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, createLocationRequest(), this);
            setFlagLocationUpdates(true);
        }
    }

    @Override
    public void setFlagLocationUpdates(boolean flag) {
        requestingLocationUpdates = flag;
    }

    @Override
    public void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
    }

    public Location getLocation() {
        checkPermission();
        return LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
    }

    private void checkPermission() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
    }

    private LocationRequest createLocationRequest() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        return locationRequest;
    }
}

