package home.oleg.placesnearme.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import home.oleg.placesnearme.Parameters;
import home.oleg.placesnearme.R;
import home.oleg.placesnearme.map_mvp.IMapPresenter;
import home.oleg.placesnearme.map_mvp.impl.MapPresenterImpl;
import home.oleg.placesnearme.map_mvp.impl.MapViewImpl;

/**
 * Created by Oleg on 19.04.2016.
 */

//This activity extends MapViewImpl, which extends AppCompatActivity.
// It is made specially to hide MapView implementation from GoogleAPIClient implementation.
public class MapActivity extends MapViewImpl implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, LocationListener {

    private final static String REQUESTING_LOCATION_UPDATES_KEY = "requesting-location-updates-key";
    private final static String REQUESTING_SEARCHING_VENUES = "requesting-searching-venues-key";
    private final static String LOCATION_KEY = "location-key";
    private final static int DEFAULT_RADIUS_METERS = 100;
    private final static LocationRequest LOCATION_REQUEST;

    static {
        LOCATION_REQUEST = new LocationRequest();
        LOCATION_REQUEST.setInterval(10000);
        LOCATION_REQUEST.setFastestInterval(5000);
        LOCATION_REQUEST.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private boolean requestingLocationUpdates = true;
    private boolean requestingSearchingVenues = true;
    private GoogleApiClient googleApiClient;
    private Location currentLocation;
    private IMapPresenter mapPresenter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildGoogleApiClient();
        updateValuesFromBundle(savedInstanceState);
        mapPresenter = new MapPresenterImpl();
        mapPresenter.onAttachView(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        int radius;

        item.setChecked(true);

        switch (id) {
            case R.id.distance100:
                radius = 100;
                break;
            case R.id.distance250:
                radius = 250;
                break;
            case R.id.distance500:
                radius = 500;
                break;
            case R.id.distance750:
                radius = 750;
                break;
            case R.id.distance1000:
                radius = 1000;
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        if (currentLocation != null) {
            startSearchingVenues(radius);
        } else {
            showLocationError();
        }

        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean(REQUESTING_LOCATION_UPDATES_KEY, requestingLocationUpdates);
        savedInstanceState.putBoolean(REQUESTING_SEARCHING_VENUES, requestingSearchingVenues);
        if (currentLocation != null) {
            savedInstanceState.putParcelable(LOCATION_KEY, currentLocation);
        }
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        if (requestingLocationUpdates) {
            startLocationUpdates();
        }

        currentLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if (currentLocation == null) {
            showLocationError();
            return;
        }

        showMyLocation(currentLocation);

        if (requestingSearchingVenues) {
            requestingSearchingVenues = false;
            startSearchingVenues(DEFAULT_RADIUS_METERS);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        googleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLocation = location;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        showLocationError();
    }

    private void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
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
                showMyLocation(currentLocation);
            }
        }
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        requestingLocationUpdates = false;
        LocationServices.FusedLocationApi.requestLocationUpdates(
                googleApiClient, LOCATION_REQUEST, this);
    }

    private void stopLocationUpdates() {
        requestingLocationUpdates = true;
        LocationServices.FusedLocationApi.removeLocationUpdates(
                googleApiClient, this);
    }

    private void startSearchingVenues(int radius) {
        String section = getIntent().getStringExtra(BasicActivity.EXTRA_DATA_SECTION);
        Parameters parameters = new Parameters();
        parameters.setLocation(currentLocation)
                .setRadius(radius)
                .setSection(section)
                .setOpenNow(0);
        mapPresenter.startSearchingVenues(parameters);
    }

    private void showLocationError() {
        Toast.makeText(this, getString(R.string.error_no_location), Toast.LENGTH_LONG).show();
    }

}
