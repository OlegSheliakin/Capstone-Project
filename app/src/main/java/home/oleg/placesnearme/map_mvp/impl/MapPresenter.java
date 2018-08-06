package home.oleg.placesnearme.map_mvp.impl;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import home.oleg.placenearme.domain.interactors.MapInteractor;
import home.oleg.placenearme.domain.models.Item;
import home.oleg.placenearme.domain.models.Venue;
import home.oleg.placenearme.domain.repositories.VenueRepository;
import home.oleg.placesnearme.map_mvp.IMapView;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Oleg on 18.04.2016.
 */
public class MapPresenter {

    private IMapView mapView;
    private VenueRepository venueRepository;

    @Inject
    public MapPresenter(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }

    public void onAttachView(@NonNull IMapView mapView) {
        Objects.requireNonNull(mapView, "mapView must not be null");

        this.mapView = mapView;
    }

    public boolean isViewAttached() {
        return mapView != null;
    }

    public void onDetachView() {
        mapView = null;
    }

    public void startSearchingVenues(MapInteractor.Parameters parameters) {
        mapView.showProgress();
        venueRepository.getPlaces(parameters)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Venue>>() {
                    @Override
                    public void accept(List<Venue> venues) throws Exception {
                        mapView.hideProgress();
                        mapView.showVenues(venues);
                        mapView.setListAdapter(venues);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
    }

    public void onFailed() {
        mapView.hideProgress();
        mapView.showError();
    }
}
