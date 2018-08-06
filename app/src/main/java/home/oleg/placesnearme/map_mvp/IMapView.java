package home.oleg.placesnearme.map_mvp;

import android.location.Location;

import com.google.android.gms.maps.OnMapReadyCallback;

import java.util.List;

import home.oleg.placenearme.domain.models.Item;
import home.oleg.placenearme.domain.models.Venue;

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
