package home.oleg.placesnearme.presentation.feature.map.view;

import android.support.annotation.NonNull;

import java.util.List;

import home.oleg.placesnearme.presentation.viewdata.VenueViewData;

/**
 * Created by Oleg Sheliakin on 17.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public interface VenueView {

    void showVenues(@NonNull List<VenueViewData> items);

}
