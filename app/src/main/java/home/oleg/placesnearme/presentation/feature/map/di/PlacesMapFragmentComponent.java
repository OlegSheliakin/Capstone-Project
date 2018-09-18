package home.oleg.placesnearme.presentation.feature.map.di;

import dagger.BindsInstance;
import dagger.Subcomponent;
import home.oleg.placesnearme.presentation.feature.map.view.VenuesMapFragment;

@Subcomponent(modules = VenuesMapFragmentModule.class)
public interface PlacesMapFragmentComponent {

    void inject(VenuesMapFragment target);

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        PlacesMapFragmentComponent.Builder bind(VenuesMapFragment mapFragment);
        PlacesMapFragmentComponent build();
    }
}
