package home.oleg.placesnearme.mapMVP.impl;

import android.location.Location;

import java.util.List;

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

    public MapPresenterImpl(){
        mapInteractor = new MapInteractorImpl(this);
    }

    @Override
    public void onAttachView(IMapView mapView) {
        if (mapView != null) {
            this.mapView = mapView;
        }
    }

    @Override
    public void onDetachView() {
            mapView = null;
    }

    @Override
    public boolean isViewAttached() {
        return mapView != null;
    }

    @Override
    public void onFinished(List<Item> items) {
        mapView.showVenues(items);
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onGoogleApiClientConnected(Location location) {
        if (location != null){
            this.location = location;
            mapView.showMyLocation(location);
        }
    }

    @Override
    public void startSearchingVenues(Parameters parameters) {
        if (location != null) {
            mapInteractor.findItems(parameters);
        }else {
            failed();
        }
    }

    @Override
    public void failed() {
        if (mapView != null){
            mapView.showError();
        }
    }
}
