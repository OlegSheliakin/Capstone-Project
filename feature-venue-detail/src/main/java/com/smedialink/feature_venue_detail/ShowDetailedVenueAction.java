package com.smedialink.feature_venue_detail;

import android.support.annotation.NonNull;

import com.smedialink.common.Optional;
import com.smedialink.common.function.Action;
import com.smedialink.feature_venue_detail.venue.view.VenueView;

import home.oleg.placesnearme.core_presentation.viewdata.VenueViewData;

/**
 * Created by Oleg Sheliakin on 21.08.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public final class ShowDetailedVenueAction<VIEW extends VenueView> implements Action<VIEW> {

    private final VenueViewData data;

    private ShowDetailedVenueAction(VenueViewData data) {
        this.data = data;
    }

    public static <VIEW extends VenueView> ShowDetailedVenueAction<VIEW> create(VenueViewData data) {
        return new ShowDetailedVenueAction<>(data);
    }

    @Override
    public void perform(@NonNull VIEW view) {
        Optional.of(data).ifPresent(view::show);
    }
}
