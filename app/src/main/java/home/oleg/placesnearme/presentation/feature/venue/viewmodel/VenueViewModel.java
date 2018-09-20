package home.oleg.placesnearme.presentation.feature.venue.viewmodel;

import home.oleg.placesnearme.presentation.base.BaseViewModel;
import home.oleg.placesnearme.presentation.feature.venue.view.VenueView;
import home.oleg.placesnearme.presentation.view_action.ShowVenueAction;
import home.oleg.placesnearme.presentation.viewdata.VenueViewData;
import io.reactivex.Observable;

/**
 * Created by Oleg Sheliakin on 18.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public class VenueViewModel extends BaseViewModel<VenueView> {

    private VenueViewData venue;

    public void setVenue(VenueViewData venue) {
        this.venue = venue;

        setAction(ShowVenueAction.create(venue));
    }

    public void geocode(double latitude, double longitude) {

    }

}
