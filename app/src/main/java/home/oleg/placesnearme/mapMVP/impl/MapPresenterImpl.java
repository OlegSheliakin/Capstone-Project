package home.oleg.placesnearme.mapMVP.impl;

import android.content.Context;
import android.location.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import home.oleg.placesnearme.Constants;
import home.oleg.placesnearme.Parameters;
import home.oleg.placesnearme.mapMVP.IMapInteractor;
import home.oleg.placesnearme.mapMVP.IMapPresenter;
import home.oleg.placesnearme.mapMVP.IMapView;
import home.oleg.placesnearme.retrofit_models.Item;

/**
 * Created by Oleg on 18.04.2016.
 */
public class MapPresenterImpl implements IMapPresenter {

    private IMapView mapView;
    private IMapInteractor mapInteractor;
    private Location location;
    private List<Item> items = new ArrayList<>();

    public MapPresenterImpl(Context context) {
        mapInteractor = new MapInteractorImpl(this);
    }

    @Override
    public void onAttachView(IMapView mapView) {
        if (mapView != null) {
            this.mapView = mapView;
        }
    }
    @Override
    public boolean isViewAttached() {
        return mapView != null;
    }

    @Override
    public void onDetachView() {
        mapView = null;
    }

    @Override
    public void onGoogleApiClientSetMyLocation(Location location) {
        if (location == null) {
            return;
        }
        this.location = location;
        mapView.showMyLocation(location);
    }

    @Override
    public void onGoogleApiLocationChanged(Location location) {
        if (location == null) {
            return;
        }
        this.location = location;
    }

    @Override
    public void onFinished(List<Item> items) {
        this.items = items;
        mapView.hideProgress();
        mapView.showVenues(items);
        mapView.setListAdapter(items);
    }

    @Override
    public void startSearchingVenues(Parameters parameters) {
        if (location == null) {
            return;
        }
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put(Constants.LL, parameters.getLocationLL());
        queryMap.put(Constants.SECTION, parameters.getSection());
        queryMap.put(Constants.RADIUS, parameters.getRadius());
        queryMap.put(Constants.CLIENT_ID, parameters.getClientId());
        queryMap.put(Constants.CLIENT_SECRET, parameters.getClientSecret());
        queryMap.put(Constants.VERSION, parameters.getVersion());
        mapInteractor.sendRequest(queryMap);
    }

    @Override
    public void onFailed() {
        if (mapView != null) {
            mapView.hideProgress();
            mapView.showError();
        }
    }
}
