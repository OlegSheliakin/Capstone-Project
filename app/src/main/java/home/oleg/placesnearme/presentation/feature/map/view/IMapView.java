package home.oleg.placesnearme.presentation.feature.map.view;

import android.location.Location;

import com.google.android.gms.maps.OnMapReadyCallback;

import java.util.List;

import home.oleg.placenearme.models.Venue;

/**
 * Created by Oleg on 18.04.2016.
 */
public interface IMapView extends OnMapReadyCallback {

    void showMyLocation(Location location);

    void showVenues(List<Venue> items);

    void showVenueFromList(int position);

    void showProgress();

    void hideProgress();

    void showError();

    void setListAdapter(List<Venue> items);

    void callIntent(int position);
}
