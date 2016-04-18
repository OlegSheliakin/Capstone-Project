package home.oleg.placesnearme;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import home.oleg.placesnearme.mapMVP.IMapPresenter;
import home.oleg.placesnearme.mapMVP.IMapView;
import home.oleg.placesnearme.mapMVP.impl.MapPresenterImpl;
import home.oleg.placesnearme.retrofit_models.Item;

import static home.oleg.placesnearme.BasicActivity.EXTRA_DATA_NAME;

public class MapActivity extends AppCompatActivity
        implements OnMapReadyCallback, AdapterView.OnItemClickListener, IMapView {

    private DrawerLayout drawer;
    private GoogleMap map;
    private IMapPresenter mapPresenter;
    private ProgressDialog progressDialog;

    private final String ATTRIBUTE_VENUE_NAME = "name";
    private final String ATTRIBUTE_VENUE_DISTANCE = "distance";
    private final String ATTRIBUTE_VENUE_ADDRESS = "address";
    private final String ATTRIBUTE_VENUE_PHONE = "phone";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.d("TAG", getIntent().getStringExtra(EXTRA_DATA_NAME));
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        progressDialog = new ProgressDialog(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);

        mapPresenter = new MapPresenterImpl(this /*Context*/);
        mapPresenter.onAttachView(this /* IMapView */);
    }

    @Override
    protected void onStart() {
        mapPresenter.onStart();
        super.onStart();
    }

    @Override
    protected void onResume() {
        mapPresenter.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mapPresenter.onPause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        mapPresenter.onStop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mapPresenter.onDetachView();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mapPresenter.onItemClick(position, drawer);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        map.setMyLocationEnabled(true);
    }

    @Override
    public void showMyLocation(Location location) {
        map.addMarker(new MarkerOptions()
                .position(new LatLng(
                        location.getLatitude(), location.getLongitude()))
                .title("My ass is here").icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)));

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(
                location.getLatitude(), location.getLongitude()), 15f);

        map.animateCamera(cameraUpdate);
    }

    @Override
    public void showVenues(List<Item> items) {
        for (Item v : items) {
            map.addMarker(new MarkerOptions()
                    .title(v.getVenue().getLocation().getAddress() + " " + v.getVenue().getName()).position
                            (new LatLng(v.getVenue().getLocation().getLat(),
                                    v.getVenue().getLocation().getLng())));
        }
    }

    @Override
    public void showProgress() {
        if(progressDialog != null){
            progressDialog.setTitle(R.string.progress_dialog_searching);
            progressDialog.show();
        }
    }

    @Override
    public void hideProgress() {
        if(progressDialog != null){
            progressDialog.dismiss();
        }
    }

    @Override
    public void showError() {
        Toast.makeText(this, R.string.connection_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setListAdapter(List<Item> items) {
        List<Map<String, String>> list = new ArrayList<>();
        for (Item item: items) {
            Map<String, String> map = new HashMap<>();
            map.put(ATTRIBUTE_VENUE_NAME, item.getVenue().getName());
            map.put(ATTRIBUTE_VENUE_ADDRESS, item.getVenue().getLocation().getAddress());
            map.put(ATTRIBUTE_VENUE_DISTANCE, String.valueOf(item.getVenue().getLocation().getDistance()));
            map.put(ATTRIBUTE_VENUE_PHONE, item.getVenue().getContact().getFormattedPhone());
            list.add(map);
        }
        int [] to = new int[]{R.id.tvName, R.id.tvDistance, R.id.tvAddress, R.id.tvPhone};
        String [] from = new String[]{ATTRIBUTE_VENUE_NAME, ATTRIBUTE_VENUE_DISTANCE, ATTRIBUTE_VENUE_ADDRESS, ATTRIBUTE_VENUE_PHONE};
        VenuesListAdapter venuesListAdapter = new VenuesListAdapter(this, list, R.layout.venue_list_item, from, to);

        ListView listView = (ListView) findViewById(R.id.navidationListView);
        if (listView != null) {
            listView.setAdapter(venuesListAdapter);
        }
    }
}
