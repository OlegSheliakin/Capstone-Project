package home.oleg.placesnearme.mapMVP;

import android.location.Location;

import java.util.List;

import home.oleg.placesnearme.retrofit_models.Item;

/**
 * Created by Oleg on 18.04.2016.
 */
public interface IMapView {
    void showMyLocation(Location location);

    void showVenues(List<Item> items);

    void showError();
}
