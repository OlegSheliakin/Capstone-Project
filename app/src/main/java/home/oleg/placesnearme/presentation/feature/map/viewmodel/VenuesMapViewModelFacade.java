package home.oleg.placesnearme.presentation.feature.map.viewmodel;

import android.arch.lifecycle.LifecycleOwner;

import com.smedialink.common.Optional;

import java.util.Map;

import javax.inject.Inject;

import home.oleg.placesnearme.presentation.feature.map.view.VenuesMapView;
import home.oleg.placesnearme.presentation.feature.venue.viewmodel.VenueViewModel;
import home.oleg.placesnearme.presentation.view_action.ViewActionObserver;
import home.oleg.placesnearme.presentation.viewdata.VenueViewData;

/**
 * Created by Oleg Sheliakin on 20.09.2018.
 * Contact me by email - olegsheliakin@gmail.com
 */
public class VenuesMapViewModelFacade {

    private final LifecycleOwner lifecycleOwner;

    private final VenuesViewModel venuesViewModel;
    private final VenueViewModel venueViewModel;
    private final UserLocationViewModel userLocationViewModel;

    @Inject
    public VenuesMapViewModelFacade(LifecycleOwner lifecycleOwner,
                                    VenuesViewModel venuesViewModel,
                                    VenueViewModel venueViewModel,
                                    UserLocationViewModel userLocationViewModel) {
        this.lifecycleOwner = lifecycleOwner;
        this.venuesViewModel = venuesViewModel;
        this.venueViewModel = venueViewModel;
        this.userLocationViewModel = userLocationViewModel;
    }

    public void attachView(VenuesMapView venuesMapView) {
        venuesViewModel.getObserver().observe(lifecycleOwner, ViewActionObserver.create(venuesMapView));
        venueViewModel.getObserver().observe(lifecycleOwner, ViewActionObserver.create(venuesMapView));
        userLocationViewModel.getObserver().observe(lifecycleOwner, ViewActionObserver.create(venuesMapView));
    }

    public void refreshRecomendedVenues() {
        venuesViewModel.getRecommendedVenues();
    }

    public void refreshUserLocation() {
        userLocationViewModel.getUserLocation();
    }

    public void setVenues(Map<String, VenueViewData> venues) {
        venuesViewModel.setVenues(venues);
    }

    public void selectVenue(String id) {
        VenueViewData venueViewData = Optional
                .of(venuesViewModel.getVenue(id))
                .getOrElseThrow(new IllegalStateException("wrong id"));

        venueViewModel.setVenue(venueViewData);
    }

    public void geocode(double latitude, double longitude) {
        venueViewModel.geocode(latitude, longitude);
    }
}
