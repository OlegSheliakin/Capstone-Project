package home.oleg.placesnearme.presentation.feature.venue.di;

import dagger.BindsInstance;
import dagger.Subcomponent;
import home.oleg.placesnearme.presentation.feature.map.view.VenuesMapFragment;
import home.oleg.placesnearme.presentation.feature.venue.view.VenueDetailsFragment;

@Subcomponent(modules = VenueDetailsFragmentModule.class)
public interface VenueDetailsFragmentComponent {

    void inject(VenueDetailsFragment target);

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        VenueDetailsFragmentComponent.Builder bind(VenueDetailsFragment mapFragment);
        VenueDetailsFragmentComponent build();
    }
}
