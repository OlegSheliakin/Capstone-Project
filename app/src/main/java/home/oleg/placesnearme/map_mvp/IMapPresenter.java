package home.oleg.placesnearme.map_mvp;

import android.location.Location;

import java.util.List;

import home.oleg.placesnearme.Parameters;
import home.oleg.placesnearme.models.Item;

/**
 * Created by Oleg on 18.04.2016.
 */
public interface IMapPresenter {

    void onAttachView (IMapView mapView);

    void onDetachView();

    boolean isViewAttached ();

    void onGoogleApiClientSetMyLocation(Location location);

    void onGoogleApiLocationChanged(Location location);

    void startSearchingVenues(Parameters parameters);

    void onFinished(List<Item> items);

    void onFailed();
}