package home.oleg.placesnearme.presentation.feature.map.view;

import java.util.ArrayList;
import java.util.List;

import home.oleg.placesnearme.presentation.base.ViewAction;
import home.oleg.placesnearme.presentation.viewobjects.VenueViewObject;

/**
 * Created by Oleg Sheliakin on 21.08.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public class ShowDataAction implements ViewAction<MapView> {

    private final List<VenueViewObject> data;

    public ShowDataAction(List<VenueViewObject> data) {
        this.data = new ArrayList<>(data);
    }

    @Override
    public void accept(MapView mapView) {
        mapView.showVenues(data);
    }
}
