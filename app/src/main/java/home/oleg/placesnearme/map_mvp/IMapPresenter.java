package home.oleg.placesnearme.map_mvp;

import java.util.List;

import home.oleg.placenearme.domain.interactors.MapInteractor;
import home.oleg.placenearme.domain.models.Item;

/**
 * Created by Oleg on 18.04.2016.
 */
public interface IMapPresenter {

    void onAttachView(IMapView mapView);

    void onDetachView();

    boolean isViewAttached();

    void startSearchingVenues(MapInteractor.Parameters parameters);

    void onFinished(List<Item> items);

    void onFailed();
}
