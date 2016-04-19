package home.oleg.placesnearme.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import home.oleg.placesnearme.Parameters;
import home.oleg.placesnearme.R;
import home.oleg.placesnearme.mapMVP.impl.MapPresenterImpl;
import home.oleg.placesnearme.mapMVP.impl.MapViewImpl;

/**
 * Created by Oleg on 19.04.2016.
 */
public class MapActivity extends MapViewImpl implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, LocationListener {

    private final static String REQUESTING_LOCATION_UPDATES_KEY = "requesting-location-updates-key";
    private final static String REQUESTING_SEARCHING_VENUES = "requesting-searching-venues-key";
    private final static String LOCATION_KEY = "location-key";

    private boolean requestingLocationUpdates = true;
    private boolean requestingSearchingVenues = true;

    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private Location currentLocation;

    private MapPresenterImpl mapPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildGoogleApiClient();
        mapPresenter = new MapPresenterImpl(this);
        mapPresenter.onAttachView(this);
    }

    private void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        createLocationRequest();
    }

    private void createLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
        Log.d("TAG", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("TAG", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("TAG", "onPaues");
        if (googleApiClient.isConnected()) {
            stopLocationUpdates();
        }
    }

    @Override
    protected void onStop() {
        Log.d("TAG", "onStop");
        googleApiClient.disconnect();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d("TAG", "onDestroy");
        mapPresenter.onDetachView();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.basic, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            requestingSearchingVenues = false;
            startSearchingVenues();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean(REQUESTING_LOCATION_UPDATES_KEY, requestingLocationUpdates);
        savedInstanceState.putBoolean(REQUESTING_SEARCHING_VENUES, requestingSearchingVenues);
        savedInstanceState.putParcelable(LOCATION_KEY, currentLocation);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (currentLocation == null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            currentLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
            mapPresenter.onGoogleApiClientSetMyLocation(currentLocation);
        }

        Log.d("TAG", "onConnected");
        if (requestingLocationUpdates) {
            startLocationUpdates();
        }

        if (requestingSearchingVenues) {
            requestingSearchingVenues = false;
            startSearchingVenues();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        googleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        mapPresenter.onGoogleApiClientSetMyLocation(location);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        mapPresenter.onFailed();
    }

    private void updateValuesFromBundle(Bundle savedInstanceState) {
        if (savedInstanceState != null) {

            if (savedInstanceState.keySet().contains(REQUESTING_LOCATION_UPDATES_KEY)) {
                requestingLocationUpdates = savedInstanceState.getBoolean(
                        REQUESTING_LOCATION_UPDATES_KEY);
            }
            if (savedInstanceState.keySet().contains(REQUESTING_SEARCHING_VENUES)) {
                requestingSearchingVenues = savedInstanceState.getBoolean(
                        REQUESTING_SEARCHING_VENUES);
            }
            if (savedInstanceState.keySet().contains(LOCATION_KEY)) {
                currentLocation = savedInstanceState.getParcelable(LOCATION_KEY);
                mapPresenter.onGoogleApiClientSetMyLocation(currentLocation);
            }
        }
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Log.d("TAG", "StartLocationUpdates");
        requestingLocationUpdates = false;
        LocationServices.FusedLocationApi.requestLocationUpdates(
                googleApiClient, locationRequest, this);
    }

    private void stopLocationUpdates() {
        requestingLocationUpdates = true;
        LocationServices.FusedLocationApi.removeLocationUpdates(
                googleApiClient, this);
        Log.d("TAG", "StopLocationUpdates");
    }

    private void startSearchingVenues() {
        String section = getIntent().getStringExtra(BasicActivity.EXTRA_DATA_SECTION);
        Parameters parameters = new Parameters();
        parameters.setLocation(currentLocation)
                .setRadius(1000)
                .setSection(section)
                .setOpenNow(1);
        mapPresenter.startSearchingVenues(parameters);
    }
}
