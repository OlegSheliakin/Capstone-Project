package com.smedialink.feature_venue_detail.venue.view;

import home.oleg.placesnearme.core_presentation.base.ErrorView;
import home.oleg.placesnearme.core_presentation.base.LoadingView;
import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData;

/**
 * Created by Oleg Sheliakin on 18.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public interface VenueView extends LoadingView, ErrorView {
    void show(VenueViewData venue);
}
