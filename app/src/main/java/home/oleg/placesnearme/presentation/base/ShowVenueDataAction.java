package home.oleg.placesnearme.presentation.base;

import android.support.annotation.NonNull;

import com.smedialink.common.Optional;
import com.smedialink.common.function.Action;

import java.util.ArrayList;
import java.util.List;

import home.oleg.placesnearme.presentation.feature.map.view.MapView;
import home.oleg.placesnearme.presentation.viewdata.VenueViewData;

/**
 * Created by Oleg Sheliakin on 21.08.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public final class ShowVenueDataAction implements Action<MapView> {

    private final List<VenueViewData> data;

    private ShowVenueDataAction(List<VenueViewData> data) {
        this.data = new ArrayList<>(data);
    }

    public static ShowVenueDataAction create(List<VenueViewData> data) {
        return new ShowVenueDataAction(data);
    }

    @Override
    public void perform(@NonNull MapView mapView) {
        Optional.of(data).ifPresent(mapView::showVenues);
    }
}
