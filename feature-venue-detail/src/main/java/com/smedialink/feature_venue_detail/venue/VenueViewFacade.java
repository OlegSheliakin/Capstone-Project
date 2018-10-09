package com.smedialink.feature_venue_detail.venue;

import android.arch.lifecycle.LifecycleOwner;
import android.view.View;

import com.smedialink.feature_venue_detail.R;
import com.smedialink.feature_venue_detail.venue.view.VenueDetailsView;
import com.smedialink.feature_venue_detail.venue.viewmodel.VenueViewModel;

import javax.inject.Inject;

import home.oleg.placesnearme.core_presentation.view_actions.ViewActionObserver;
import home.oleg.placesnearme.core_presentation.viewdata.VenueMapViewData;

/**
 * Created by Oleg Sheliakin on 09.10.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public class VenueViewFacade {

    private final LifecycleOwner lifecycleOwner;
    private final VenueViewModel venueViewModel;

    @Inject
    public VenueViewFacade(LifecycleOwner lifecycleOwner,
                           VenueViewModel venueViewModel) {
        this.lifecycleOwner = lifecycleOwner;
        this.venueViewModel = venueViewModel;
    }

    public void onCreateView(View view) {
        VenueDetailsView venueDetailsView = view.findViewById(R.id.venueView);
        venueViewModel.getObserver()
                .observe(lifecycleOwner, ViewActionObserver.create(venueDetailsView));
    }

    public void setVenue(VenueMapViewData venueMapViewData) {
        venueViewModel.setVenue(venueMapViewData);
    }

}
