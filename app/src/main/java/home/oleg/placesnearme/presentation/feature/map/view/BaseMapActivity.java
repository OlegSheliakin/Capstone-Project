package home.oleg.placesnearme.presentation.feature.map.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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

import home.oleg.placenearme.domain.models.Venue;
import home.oleg.placesnearme.R;
import home.oleg.placesnearme.presentation.VenueRecyclerViewAdapter;

public class BaseMapActivity extends AppCompatActivity implements IMapView {

    public final static String ATTRIBUTE_VENUE_NAME = "name";
    public final static String ATTRIBUTE_VENUE_DISTANCE = "distance";
    public final static String ATTRIBUTE_VENUE_ADDRESS = "address";
    public final static String ATTRIBUTE_VENUE_PHONE = "phone";
    public final static String ATTRIBUTE_VENUE_PHOTO = "photo";

    private GoogleMap map;
    private List<Venue> items;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void showMyLocation(Location location) {
        if (location != null) {
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(
                    location.getLatitude(), location.getLongitude()), 15f);
            map.animateCamera(cameraUpdate);
        }
    }

    @Override
    public void showVenues(List<Venue> items) {
        this.items = items;
        map.clear();//delete all marks if they exist

        if (items.isEmpty()) {
            Toast.makeText(this, R.string.nothing_around_you, Toast.LENGTH_SHORT).show();
        }

        StringBuilder title = new StringBuilder();
        for (Venue v : items) {
            if (v.getName() != null) {
                title.append(v.getName());//appends name if it exists
            }
            if (v.getLocation().getAddress() != null) {
                title.append(", ").append(v.getLocation().getAddress());//appends address if it exists
            }

            map.addMarker(new MarkerOptions()
                    .title(title.toString()).position
                            (new LatLng(v.getLocation().getLat(),
                                    v.getLocation().getLng())));
            title.delete(0, title.length());//clear string builder
        }
    }

    @Override
    public void showVenueFromList(int position) {
        double lat = items.get(position).getLocation().getLat();
        double lng = items.get(position).getLocation().getLng();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(
                lat, lng), 18f);
        map.animateCamera(cameraUpdate);
    }

    @Override
    public void showProgress() {
    }

    @Override
    public void hideProgress() {
    }

    @Override
    public void showError() {
        Toast.makeText(this, R.string.connection_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setListAdapter(List<Venue> items) {
        List<Map<String, String>> data = new ArrayList<>();
        for (Venue item : items) {
            Map<String, String> map = new HashMap<>();
            map.put(ATTRIBUTE_VENUE_NAME, item.getName());
            map.put(ATTRIBUTE_VENUE_ADDRESS, item.getLocation().getAddress());
            map.put(ATTRIBUTE_VENUE_DISTANCE, String.valueOf(item.getLocation().getDistance()) + getString(R.string.distance));
            map.put(ATTRIBUTE_VENUE_PHONE, item.getContact().getFormattedPhone());
            map.put(ATTRIBUTE_VENUE_PHOTO, item.getFeaturedPhotos().getItems().get(0).getPhotoURL());
            data.add(map);
        }
    }

    @Override
    public void callIntent(int position) {
        String phoneNumber = items.get(position).getContact().getFormattedPhone();
        Uri uri = Uri.parse("tel:" + phoneNumber);
        Intent intent = new Intent(Intent.ACTION_DIAL, uri);
        startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        map = googleMap;
        map.setMyLocationEnabled(true);
    }

}
