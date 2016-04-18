package home.oleg.placesnearme.mapMVP;

import android.location.Location;

import java.util.List;

import home.oleg.placesnearme.Parameters;
import home.oleg.placesnearme.retrofit_models.Item;

/**
 * Created by Oleg on 18.04.2016.
 */
public interface IMapPresenter {
    void onAttachView (IMapView mapView);

    void onDetachView();

    boolean isViewAttached ();

    void onFinished(List<Item> items);

    void onResume();

    void onGoogleApiClientConnected(Location location);

    void startSearchingVenues(Parameters parameters);

    void failed();
}
