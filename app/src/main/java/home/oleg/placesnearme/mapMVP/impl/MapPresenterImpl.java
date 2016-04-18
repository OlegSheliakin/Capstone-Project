package home.oleg.placesnearme.mapMVP.impl;

import android.content.Context;
import android.location.Location;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import home.oleg.placesnearme.Constants;
import home.oleg.placesnearme.ILocationInteractor;
import home.oleg.placesnearme.LocationInteractorImpl;
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
    private ILocationInteractor locationInteractor;

    public MapPresenterImpl(Context context) {
        mapInteractor = new MapInteractorImpl(this);
        locationInteractor = new LocationInteractorImpl(context, this);
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
    public void onStart() {
        mapView.showProgress();
        locationInteractor.connect();
    }

    @Override
    public void onResume() {
        locationInteractor.startLocationUpdates();
    }

    @Override
    public void onPause() {
        locationInteractor.stopLocationUpdates();
    }

    @Override
    public void onStop() {
        locationInteractor.disconnect();
    }

    @Override
    public void onDetachView() {
        mapView = null;
    }

    @Override
    public void onItemClick(int position, DrawerLayout drawer) {
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public void onGoogleApiClientConnected(Location location) {
        if (location == null) {
            return;
        }
        this.location = location;
        mapView.showMyLocation(this.location);
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            this.location = location;
            mapView.showMyLocation(location);
        }
    }

    @Override
    public void onFinished(List<Item> items) {
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
