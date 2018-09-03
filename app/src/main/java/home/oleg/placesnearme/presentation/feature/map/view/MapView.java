package home.oleg.placesnearme.presentation.feature.map.view;

import android.support.annotation.NonNull;

import java.util.List;

import home.oleg.placesnearme.presentation.base.ErrorView;
import home.oleg.placesnearme.presentation.base.LoadingView;
import home.oleg.placesnearme.presentation.viewdata.VenueViewData;

/**
 * Created by Oleg on 18.04.2016.
 */
public interface MapView extends LoadingView, ErrorView {

    void showVenues(@NonNull List<VenueViewData> items);

}
