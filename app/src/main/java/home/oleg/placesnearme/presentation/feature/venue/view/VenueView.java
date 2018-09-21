package home.oleg.placesnearme.presentation.feature.venue.view;

import home.oleg.placesnearme.presentation.base.ErrorView;
import home.oleg.placesnearme.presentation.base.LoadingView;
import home.oleg.placesnearme.presentation.base.View;
import home.oleg.placesnearme.presentation.viewdata.VenueViewData;
import io.reactivex.annotations.NonNull;

/**
 * Created by Oleg Sheliakin on 18.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public interface VenueView extends View {
    void showVenue(@NonNull VenueViewData venueViewData);
}
