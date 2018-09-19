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

    private VenueViewModel.VenueViewDataSelector some;

    public interface VenueViewDataSelector {
        Observable<VenueViewData> listenSelected();
    }

    public void attachSelector(VenueViewModel.VenueViewDataSelector selector) {
        addToDisposables(
                selector.listenSelected()
                        .subscribe(
                                venueViewData -> setAction(ShowVenueAction.create(venueViewData)),
                                Throwable::printStackTrace
                        )
        );
    }

}
