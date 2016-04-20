package home.oleg.placesnearme.mapMVP;

import android.location.Location;

import com.google.android.gms.maps.OnMapReadyCallback;

import java.util.List;

import home.oleg.placesnearme.models.Item;

/**
 * Created by Oleg on 18.04.2016.
 */
public interface IMapView extends OnMapReadyCallback {

    void showMyLocation(Location location);

    void showVenues(List<Item> items);

    void showVenueFromList(int position);

    void showProgress();

    void hideProgress();

    void showError();

    void setListAdapter(List<Item> items);

    void callIntent(int position);
}
