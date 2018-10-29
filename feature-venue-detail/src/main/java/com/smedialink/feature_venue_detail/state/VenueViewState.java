package com.smedialink.feature_venue_detail.state;

import home.oleg.placesnearme.core_presentation.base.ErrorEvent;
import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData;

public class VenueViewState {
    private final boolean isLoading;
    private final ErrorEvent errorEvent;
    private final VenueViewData venueViewData;

    private VenueViewState(boolean isLoading, ErrorEvent errorEvent, VenueViewData venueViewData) {
        this.isLoading = isLoading;
        this.errorEvent = errorEvent;
        this.venueViewData = venueViewData;
    }

    public static VenueViewState loading() {
        return new VenueViewState(true, null, null);
    }

    public static VenueViewState error(ErrorEvent errorEvent) {
        return new VenueViewState(false, errorEvent, null);
    }

    public static VenueViewState success(VenueViewData venueViewData) {
        return new VenueViewState(false, null, venueViewData);
    }

    public boolean isLoading() {
        return isLoading;
    }

    public ErrorEvent getErrorEvent() {
        return errorEvent;
    }

    public VenueViewData getVenueViewData() {
        return venueViewData;
    }
}
