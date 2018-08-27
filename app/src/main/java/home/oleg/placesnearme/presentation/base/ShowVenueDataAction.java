package home.oleg.placesnearme.presentation.base;

import java.util.ArrayList;
import java.util.List;

import home.oleg.placesnearme.presentation.feature.map.view.MapView;
import home.oleg.placesnearme.presentation.viewdata.VenueViewData;

/**
 * Created by Oleg Sheliakin on 21.08.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public class ShowVenueDataAction implements ViewAction<MapView> {

    private final List<VenueViewData> data;

    private ShowVenueDataAction(List<VenueViewData> data) {
        this.data = new ArrayList<>(data);
    }

    public static ShowVenueDataAction create(List<VenueViewData> data) {
        return new ShowVenueDataAction(data);
    }

    @Override
    public void accept(MapView mapView) {
        mapView.showVenues(data);
    }
}
