package home.oleg.placesnearme.core_presentation;

import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData;

public interface ShowHideBottomBarListener {
    void showBottomBar();

    void hideBottomBar();

    void showVenueDetail(VenueViewData venueViewData);
}
