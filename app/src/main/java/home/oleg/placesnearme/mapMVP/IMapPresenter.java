package home.oleg.placesnearme.mapMVP;

import android.location.Location;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

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

    void onItemClick(int position);

    void onGoogleApiClientConnected(Location location);

    void onLocationChanged(Location location);

    void startSearchingVenues(Parameters parameters);

    void onFinished(List<Item> items);

    void onFailed();
}
