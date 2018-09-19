package home.oleg.placesnearme.presentation.feature.map.di;

import dagger.BindsInstance;
import dagger.Subcomponent;
import home.oleg.placesnearme.presentation.feature.map.view.VenuesMapFragment;
import home.oleg.placesnearme.presentation.feature.venue.di.VenueDetailsFragmentComponent;

@Subcomponent(modules = VenuesMapFragmentModule.class)
public interface PlacesMapFragmentComponent {

    VenueDetailsFragmentComponent.Builder venueDetailsFragmentComponentBuilder();

    void inject(VenuesMapFragment target);

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        PlacesMapFragmentComponent.Builder bind(VenuesMapFragment mapFragment);
        PlacesMapFragmentComponent build();
    }
}
