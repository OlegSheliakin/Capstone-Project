package home.oleg.placesnearme.feature_map.view;

import java.util.List;

import home.oleg.placesnearme.core_presentation.base.ErrorView;
import home.oleg.placesnearme.core_presentation.base.LoadingView;
import home.oleg.placesnearme.core_presentation.viewdata.VenueMapViewData;
import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData;

/**
 * Created by Oleg Sheliakin on 17.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public interface VenuesView extends ErrorView, LoadingView {
    void show(List<VenueMapViewData> venues);
}
