package home.oleg.placesnearme.map_mvp.impl;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import home.oleg.placesnearme.NavigationListAdapter;
import home.oleg.placesnearme.R;
import home.oleg.placesnearme.map_mvp.IMapView;
import home.oleg.placesnearme.models.Item;

public class MapViewImpl extends AppCompatActivity
        implements IMapView {

    private GoogleMap map;
    private DrawerLayout drawerLayout;
    private ProgressDialog progressDialog;
    protected List<Item> items;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        progressDialog = new ProgressDialog(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);

        showProgress();
    }

    @Override
    public void showMyLocation(Location location) {
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(
                location.getLatitude(), location.getLongitude()), 15f);
        map.animateCamera(cameraUpdate);
    }

    @Override
    public void showVenues(List<Item> items) {
        this.items = items;
        map.clear();//delete all marks if they exist

        if (items.isEmpty()){
            Toast.makeText(this, R.string.nothing_arround_you, Toast.LENGTH_SHORT).show();
        }

        StringBuilder title = new StringBuilder();
        for (Item v : items) {
            if ( v.getVenue().getName() != null){
                title.append(v.getVenue().getName());//appends name if it exist
            }
            if (v.getVenue().getLocation().getAddress() != null){
                title.append(", ").append(v.getVenue().getLocation().getAddress());//appends address if it exist
            }

            map.addMarker(new MarkerOptions()
                    .title(title.toString()).position
                            (new LatLng(v.getVenue().getLocation().getLat(),
                                    v.getVenue().getLocation().getLng())));
            title.delete(0, title.length());//clear stringbuilder
        }
    }

    @Override
    public void showVenueFromList(int position) {
        double lat = items.get(position).getVenue().getLocation().getLat();
        double lng = items.get(position).getVenue().getLocation().getLng();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(
                lat, lng), 18f);
        map.animateCamera(cameraUpdate);
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public void showProgress() {
        if (progressDialog != null) {
            progressDialog.setTitle(R.string.progress_dialog_searching);
            progressDialog.setMessage(getString(R.string.progress_bar_message));
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }

    @Override
    public void hideProgress() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void showError() {
        Toast.makeText(this, R.string.connection_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setListAdapter(List<Item> items) {

        final String ATTRIBUTE_VENUE_NAME = "name";
        final String ATTRIBUTE_VENUE_DISTANCE = "distance";
        final String ATTRIBUTE_VENUE_ADDRESS = "address";
        final String ATTRIBUTE_VENUE_PHONE = "phone";

        ListView listView = (ListView) findViewById(R.id.navidationListView);
        List<Map<String, String>> list = new ArrayList<>();
        for (Item item : items) {
            Map<String, String> map = new HashMap<>();
            map.put(ATTRIBUTE_VENUE_NAME, item.getVenue().getName());
            map.put(ATTRIBUTE_VENUE_ADDRESS, item.getVenue().getLocation().getAddress());
            map.put(ATTRIBUTE_VENUE_DISTANCE, String.valueOf(item.getVenue().getLocation().getDistance())+getString(R.string.distance));
            map.put(ATTRIBUTE_VENUE_PHONE, item.getVenue().getContact().getFormattedPhone());
            list.add(map);
        }
        int[] to = new int[]{R.id.tvName, R.id.tvDistance, R.id.tvAddress, R.id.tvPhone};
        String[] from = new String[]{ATTRIBUTE_VENUE_NAME, ATTRIBUTE_VENUE_DISTANCE, ATTRIBUTE_VENUE_ADDRESS, ATTRIBUTE_VENUE_PHONE};
        NavigationListAdapter venuesListAdapter = new NavigationListAdapter(this, list, R.layout.venue_list_item, from, to, this);

        if (listView != null) {
            listView.setAdapter(venuesListAdapter);
        }
    }

    @Override
    public void callIntent(int position) {
        String phoneNumber = items.get(position).getVenue().getContact().getFormattedPhone();
        Uri uri = Uri.parse("tel:" + phoneNumber);
        Intent intent = new Intent(Intent.ACTION_DIAL, uri);
        startActivity(intent);
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
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
