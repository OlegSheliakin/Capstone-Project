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

//This activity extends MapViewImpl, which extends AppCompatActivity.
// It is made specially to hide MapView implementation from GoogleAPIClient implementation.
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
        updateValuesFromBundle(savedInstanceState);
        mapPresenter = new MapPresenterImpl(this);
        mapPresenter.onAttachView(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (googleApiClient.isConnected() && requestingLocationUpdates) {
            stopLocationUpdates();
        }
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mapPresenter.onDetachView();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.basic, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        item.setChecked(true);
        showProgress();
        switch (id){
            case R.id.distance100:
                startSearchingVenues(100);
                return true;
            case R.id.distance250:
                startSearchingVenues(250);
                return true;
            case R.id.distance500:
                startSearchingVenues(500);
                return true;
            case R.id.distance750:
                startSearchingVenues(750);
                return true;
            case R.id.distance1000:
                startSearchingVenues(1000);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

        if (requestingLocationUpdates) {
            startLocationUpdates();
        }

        if (requestingSearchingVenues) {
            requestingSearchingVenues = false;
            startSearchingVenues(100);// searches venues around location in the radius = 100 meters;
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        googleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        mapPresenter.onGoogleApiLocationChanged(location);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        mapPresenter.onFailed();
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
        requestingLocationUpdates = false;
        LocationServices.FusedLocationApi.requestLocationUpdates(
                googleApiClient, locationRequest, this);
    }

    private void stopLocationUpdates() {
        requestingLocationUpdates = true;
        LocationServices.FusedLocationApi.removeLocationUpdates(
                googleApiClient, this);
    }

    private void startSearchingVenues(int radius) {
        String section = getIntent().getStringExtra(BasicActivity.EXTRA_DATA_SECTION);
        Parameters parameters = new Parameters();
        parameters.setLocation(currentLocation)//sets the current location
                .setRadius(radius)//sets the radius of searching
                .setSection(section)//sets the section(type) of venues
                .setOpenNow(1);//sets only open venues
        mapPresenter.startSearchingVenues(parameters);
    }
}
