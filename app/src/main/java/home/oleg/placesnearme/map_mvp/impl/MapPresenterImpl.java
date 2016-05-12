package home.oleg.placesnearme.map_mvp.impl;

import java.util.List;
import java.util.Map;

import home.oleg.placesnearme.Parameters;
import home.oleg.placesnearme.R;
import home.oleg.placesnearme.map_mvp.IMapInteractor;
import home.oleg.placesnearme.map_mvp.IMapPresenter;
import home.oleg.placesnearme.map_mvp.IMapView;
import home.oleg.placesnearme.models.Item;

/**
 * Created by Oleg on 18.04.2016.
 */
public class MapPresenterImpl implements IMapPresenter {

    private IMapView mapView;
    private IMapInteractor mapInteractor;

    public MapPresenterImpl() {
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
    public void onFinished(List<Item> items) {
        if (mapView != null) {
            mapView.hideProgress();
            mapView.showVenues(items);
            mapView.setListAdapter(items);
        }
    }

    @Override
    public void startSearchingVenues(Parameters parameters) {
        if (mapView != null) {
            mapView.showProgress();
        }
            Map<String, String> queryMap = parameters.toQueryMap();
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
